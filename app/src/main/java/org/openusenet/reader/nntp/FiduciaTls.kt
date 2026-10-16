/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * La FIDUCIA TLS di NeReO (1.4). Traduzione in Kotlin del prototipo provato su
 * OpenJDK 21 il 13 settembre 2026 — vedi NeReO_1.4_prototipo_scadenza.md: sei
 * casi a livello di API piu' tre handshake TLS veri contro un server con
 * certificato scaduto, tutti passati.
 *
 * COSA RISOLVE. Paganini presentava una catena REGOLARE e COMPLETA
 * (foglia -> Let's Encrypt R13 -> ISRG Root X1) con la sola foglia SCADUTA il
 * 24 aprile 2026. Non e' autofirmato, non e' una CA privata, non e' una catena
 * incompleta: e' una scadenza non rinnovata. Per collegarsi non serve
 * disattivare la validazione — serve rilassare UNA condizione.
 *
 * IL PEZZO CHE VALE PIU' DI TUTTI: LA CLASSIFICAZIONE.
 * Per sapere se il fallimento e' "solo le date" NON si annusa il testo
 * dell'eccezione — che cambia da una ROM all'altra ed e' la ragione per cui la
 * classificazione della 1.3.3 ha dovuto imparare sei firme testuali diverse.
 * Peggio: su DUE dispositivi indipendenti Android riporta "Trust anchor for
 * certification path not found" anche per un certificato soltanto SCADUTO — ci
 * depista da solo. Qui invece si RI-VALIDA con l'orologio indietro: se cosi'
 * passa, l'unico problema erano le date; se non passa, la catena e' rotta
 * davvero. E' una PROVA, non un indizio.
 *
 * COSA RESTA DA CONFERMARE SU ANDROID: il provider di sicurezza deriva da
 * BouncyCastle, non da SunJSSE. Il ripiego e' gia' dichiarato nella specifica:
 * se la ri-validazione a orologio indietro non funzionasse, il caso "scaduto"
 * ricade sul pin TOFU — funziona comunque, col difetto noto di una
 * riapprovazione a ogni rinnovo.
 */
package org.openusenet.reader.nntp

import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.CertPathValidator
import java.security.cert.CertPathValidatorException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.PKIXParameters
import java.security.cert.TrustAnchor
import java.security.cert.X509Certificate
import java.util.Date
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/** Esito della valutazione di una catena presentata da un server. */
enum class EsitoFiducia {
    /** Valida secondo le regole normali: niente da chiedere all'utente. */
    OK,

    /** Catena PKIX buona, firme buone, ancora fidata — SOLO le date non tornano. */
    SOLO_SCADUTO,

    /** La catena non arriva a un'ancora fidata: autofirmata, CA privata, anelli mancanti. */
    CATENA,
}

/**
 * L'identita' TLS OSSERVATA su una connessione: quanto basta per mostrarla
 * all'utente, per appuntarla, e per dirgli fra quanto scade.
 *
 * E' un dato, non una decisione: viene raccolto SEMPRE, anche quando la
 * connessione fallisce. Osservare non e' fidarsi.
 */
data class IdentitaTls(
    /** SHA-256 dello SubjectPublicKeyInfo, Base64. E' QUESTO che si appunta. */
    val spkiSha256: String,
    /** SHA-256 del certificato intero: solo per diagnosi e visualizzazione. */
    val certificatoSha256: String,
    val subject: String,
    val issuer: String,
    /** Millisecondi epoch. */
    val notBefore: Long,
    val notAfter: Long,
    val esito: EsitoFiducia,
) {
    /**
     * Giorni che mancano alla scadenza del certificato foglia (negativi se e'
     * gia' scaduto), calcolati su [adesso].
     *
     * Serve all'avviso "il certificato scade fra N giorni": su un server a
     * rinnovo MANUALE — cioe' Paganini — previene la caduta invece di
     * attutirla. Con il TrustManager della 1.4 la catena la vediamo a ogni
     * connessione, quindi questo numero e' gratis.
     */
    fun giorniAllaScadenza(adesso: Long = System.currentTimeMillis()): Long =
        (notAfter - adesso) / 86_400_000L
}

/**
 * Fiducia TLS di NeReO. Non conserva stato: legge il trust store di sistema a
 * ogni costruzione, come fa oggi `SSLSocketFactory.getDefault()`.
 */
object FiduciaTls {

    /**
     * Il TrustManager di sistema, quello vero: trust store di Android,
     * algoritmo PKIX, nessuna modifica. E' lui a decidere nel percorso normale.
     */
    fun trustManagerDiSistema(): X509TrustManager {
        val f = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        f.init(null as KeyStore?) // null = trust store di sistema
        return f.trustManagers.filterIsInstance<X509TrustManager>().firstOrNull()
            ?: throw IllegalStateException("nessun X509TrustManager di sistema")
    }

    /** Le ancore di fiducia del sistema, per la ri-validazione a orologio indietro. */
    private fun ancoreDiSistema(): Set<TrustAnchor> =
        trustManagerDiSistema().acceptedIssuers.map { TrustAnchor(it, null) }.toSet()

    /**
     * Un istante in cui TUTTA la catena presentata dal server era valida.
     *
     * Si guarda SOLO la catena che arriva dalla rete: l'ancora viene dal trust
     * store, non dal server, e — verificato nel prototipo — PKIX non controlla
     * la validita' dell'ancora stessa (RFC 5280: la trust anchor non si valida).
     * Nel prototipo la radice aveva `notBefore` di OGGI e la validazione a
     * ritroso e' passata lo stesso.
     *
     * null quando la finestra e' vuota — cioe' due certificati della catena non
     * sono mai stati validi insieme. In quel caso NON e' un problema di sole
     * date, ed e' giusto che il percorso non si applichi.
     */
    fun istanteInCuiLaCatenaEraValida(catena: Array<X509Certificate>): Date? {
        if (catena.isEmpty()) return null
        val maxNotBefore = catena.maxOf { it.notBefore.time }
        val minNotAfter = catena.minOf { it.notAfter.time }
        if (maxNotBefore > minNotAfter) return null
        // un secondo prima della scadenza piu' vicina: dentro la finestra di tutti
        return Date(minNotAfter - 1000L)
    }

    /**
     * Valida la catena IGNORANDO SOLO LE DATE. Tutto il resto resta in piedi:
     * firme, catena fino a un'ancora fidata, vincoli di base.
     *
     * `isRevocationEnabled = false` e' esplicito e non e' un allentamento: con
     * l'orologio indietro un controllo di revoca non avrebbe senso (le liste
     * sono di oggi), e il TrustManager di sistema su Android non fa comunque
     * OCSP di sua iniziativa. Meglio dichiararlo che ereditarlo.
     *
     * @throws CertPathValidatorException se la catena non regge comunque.
     */
    fun validaIgnorandoLeDate(catena: Array<X509Certificate>) {
        val quando = istanteInCuiLaCatenaEraValida(catena)
            ?: throw CertPathValidatorException("finestra di validita' vuota")
        val path = CertificateFactory.getInstance("X.509").generateCertPath(catena.toList())
        val par = PKIXParameters(ancoreDiSistema()).apply {
            isRevocationEnabled = false
            date = quando
        }
        CertPathValidator.getInstance("PKIX").validate(path, par)
    }

    /**
     * Che cosa c'e' che non va in questa catena.
     *
     * Non guarda il testo di nessuna eccezione: prova, e se fallisce riprova
     * con l'orologio indietro. Il risultato della seconda prova E' la
     * classificazione.
     */
    fun classifica(catena: Array<X509Certificate>, authType: String): EsitoFiducia = try {
        trustManagerDiSistema().checkServerTrusted(catena, authType)
        EsitoFiducia.OK
    } catch (_: CertificateException) {
        try {
            validaIgnorandoLeDate(catena)
            EsitoFiducia.SOLO_SCADUTO
        } catch (_: Exception) {
            EsitoFiducia.CATENA
        }
    }

    /**
     * Il pin: SHA-256 dello SubjectPublicKeyInfo del certificato FOGLIA, in
     * Base64 — la stessa definizione che Android usa per la Network Security
     * Configuration.
     *
     * MAI di un intermedio: appuntare un intermedio significherebbe fidarsi di
     * tutto cio' che quella CA firmera' in futuro, cioe' quasi il contrario di
     * quel che il TOFU serve a fare.
     *
     * `PublicKey.getEncoded()` restituisce gia' il DER dello SPKI. Verificato
     * nel prototipo: il valore coincide con
     *   openssl x509 -pubkey -noout | openssl pkey -pubin -outform der \
     *     | openssl dgst -sha256 -binary | base64
     */
    fun pinSpki(foglia: X509Certificate): String =
        android.util.Base64.encodeToString(
            MessageDigest.getInstance("SHA-256").digest(foglia.publicKey.encoded),
            android.util.Base64.NO_WRAP,
        )

    /** SHA-256 del certificato intero (DER), in Base64. Solo diagnosi. */
    fun improntaCertificato(foglia: X509Certificate): String =
        android.util.Base64.encodeToString(
            MessageDigest.getInstance("SHA-256").digest(foglia.encoded),
            android.util.Base64.NO_WRAP,
        )

    /** Fotografa la foglia di [catena] in un [IdentitaTls]. */
    fun osserva(catena: Array<X509Certificate>, esito: EsitoFiducia): IdentitaTls {
        val foglia = catena.first()
        return IdentitaTls(
            spkiSha256 = pinSpki(foglia),
            certificatoSha256 = improntaCertificato(foglia),
            subject = foglia.subjectX500Principal.name,
            issuer = foglia.issuerX500Principal.name,
            notBefore = foglia.notBefore.time,
            notAfter = foglia.notAfter.time,
            esito = esito,
        )
    }
}

/**
 * Il TrustManager che NeReO installa al posto di quello predefinito.
 *
 * Fa due cose, e nessuna delle due e' "accettare tutto":
 *  1. OSSERVA e registra la catena presentata — serve per lo stato PENDING, per
 *     mostrare all'utente subject/issuer/impronte e per sapere quando scade il
 *     certificato. Registrare non e' fidarsi: la connessione fallisce lo stesso
 *     se non deve passare;
 *  2. DELEGA la decisione al TrustManager di sistema, e la ribalta solo nei due
 *     casi deliberati: un pin approvato che coincide, oppure una catena buona
 *     con le sole date scadute su un server dove l'utente ha acceso
 *     l'eccezione.
 *
 * NON usare un TrustManager "accetta tutto" per catturare il certificato e
 * decidere dopo: spegnerebbe la verifica proprio nel momento in cui serve
 * raccogliere il dato in sicurezza.
 *
 * L'osservazione finisce in [ultimaIdentita] invece che in una callback: chi
 * costruisce il client la legge DOPO l'handshake, riuscito o fallito che sia, e
 * la scrive nel database da un contesto sospendibile. Una callback qui dentro
 * girerebbe nel mezzo dell'handshake, dove non si puo' sospendere.
 */
class TrustManagerNeReO(
    /** SPKI approvato per questo endpoint, oppure null se non ce n'e' uno. */
    private val pinApprovato: String?,
    /** L'utente ha acceso l'eccezione "certificato scaduto" per questa riga server. */
    private val eccezioneScadenza: Boolean,
) : X509TrustManager {

    /** L'ultima identita' osservata, anche se la connessione e' poi fallita. */
    @Volatile
    var ultimaIdentita: IdentitaTls? = null
        private set

    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        val esito = FiduciaTls.classifica(chain, authType)
        ultimaIdentita = runCatching { FiduciaTls.osserva(chain, esito) }.getOrNull()

        // 1. Pin approvato: e' LUI l'identita' di riferimento per questo
        //    percorso (identity binding, formulazione di HAL). Non "ignora il
        //    nome host": SOSTITUISCE la prova di identita', e solo qui.
        val pin = pinApprovato
        if (pin != null) {
            val visto = FiduciaTls.pinSpki(chain.first())
            if (visto == pin) return
            // Il pin non si aggiorna MAI da solo: si blocca e si torna nelle
            // impostazioni del server. L'attrito e' la funzione, non un difetto.
            throw CertificateException(
                "The server key does not match the one you approved for this server. " +
                    "Expected: $pin - received: $visto. " +
                    "Open Settings > Servers > TLS identity to review it.",
            )
        }

        // 2. Percorso normale: decide il TrustManager di sistema.
        if (esito == EsitoFiducia.OK) return

        // 3. Eccezione deliberata, e SOLO per le date.
        if (esito == EsitoFiducia.SOLO_SCADUTO && eccezioneScadenza) return

        throw CertificateException("certificate chain not trusted: $esito")
    }

    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit

    override fun getAcceptedIssuers(): Array<X509Certificate> =
        FiduciaTls.trustManagerDiSistema().acceptedIssuers
}
