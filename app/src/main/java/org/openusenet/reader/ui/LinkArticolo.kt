/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Message-ID e link permanente: UN SOLO punto in tutta l'app.
 *
 * PERCHE' ESISTE (1.3.6, secondo giro).
 * Richiesta di **ObiWan**, 14 settembre 2026: poter generare un URL che punti a
 * un dato messaggio e copiarlo negli appunti. Il servizio che ha indicato e'
 * https://usenet.id/ — dato un Message-ID, recupera e mostra l'articolo
 * corrispondente. La forma dell'URL e' il Message-ID SENZA parentesi angolari
 * attaccato al dominio:
 *
 *     https://usenet.id/20260914102709.00000c5b@mvps.org
 *
 * (verificato aprendo proprio quell'indirizzo: restituisce l'articolo di
 * ObiWan «Re: [NeReO] - 1.3.5 - Bugfix release» in it.tlc.cellulari.android).
 *
 * NEREO NON CONTATTA USENET.ID. Qui si compone una stringa, e la stringa
 * finisce negli appunti. Nessuna connessione, nessun permesso nuovo, nessun
 * dato che esce dal telefono: e' un generatore di link, non il client di un
 * servizio terzo. Se un giorno usenet.id chiudesse, l'unica riga da cambiare e'
 * [PREFISSO_USENET_ID] — per questo e' una costante e non e' sparsa nel codice.
 *
 * IL LIMITE, DA DIRE E NON DA NASCONDERE. usenet.id legge dai propri feed NNTP
 * (il sito ringrazia Eternal September e BlueWorld Hosting). Un articolo in una
 * gerarchia che loro non portano, o troppo vecchio, puo' non risolversi. Il link
 * e' una comodita', non una garanzia di permanenza.
 *
 * PERCHE' KOTLIN PURO E NIENTE android.net.Uri. `Uri.encode` farebbe lo stesso
 * lavoro, ma non esiste in una JVM: le prove di `app/src/test` girano senza
 * Android e non potrebbero verificarne una riga. Una funzione che costruisce un
 * URL a partire da dati altrui e' esattamente il genere di cosa che va provata,
 * quindi la codifica e' scritta qui, in venti righe, e collaudata in CoreTests.
 */
package org.openusenet.reader.ui

/**
 * Il servizio a cui puntano i link generati. Cambiare QUI, e solo qui, se un
 * giorno servisse un altro gateway.
 */
const val PREFISSO_USENET_ID = "https://usenet.id/"

/** Caratteri che NON vanno percent-codificati, oltre a lettere e cifre. */
private const val SICURI = "-._~@"

private const val ESADECIMALI = "0123456789ABCDEF"

/**
 * Percent-codifica [s] per inserirlo in un segmento di percorso di un URL.
 *
 * NON e' pedanteria: un Message-ID e' scelto da chi ha scritto l'articolo, e la
 * RFC 5536 gli lascia un alfabeto ampio che comprende `/`, `?`, `#`, `%`, `&` e
 * `+`. Attaccarlo al dominio cosi' com'e' produrrebbe, per quegli articoli, un
 * URL che punta altrove o non punta a niente.
 *
 * `@` resta letterale: e' consentito in un segmento di percorso (RFC 3986,
 * `pchar`), compare in ogni Message-ID, e codificarlo renderebbe i link
 * illeggibili senza guadagnare nulla.
 */
fun percentCodifica(s: String): String = buildString(s.length + 8) {
    for (b in s.toByteArray(Charsets.UTF_8)) {
        val v = b.toInt() and 0xFF
        val c = v.toChar()
        if (c in 'A'..'Z' || c in 'a'..'z' || c in '0'..'9' || c in SICURI) {
            append(c)
        } else {
            append('%').append(ESADECIMALI[v shr 4]).append(ESADECIMALI[v and 0x0F])
        }
    }
}

/**
 * Il Message-ID senza le parentesi angolari, pronto per un URL.
 *
 * In archivio i Message-ID stanno nella forma RFC — `<abc@example.org>` — perche'
 * e' cosi' che arrivano da XOVER. Le parentesi si tolgono solo se ci sono
 * ENTRAMBE: una sola parentesi e' un dato malformato, e mangiarla in silenzio
 * nasconderebbe il problema invece di mostrarlo.
 *
 * Gli spazi interni non dovrebbero esistere (RFC 5536 non li ammette) ma si
 * tolgono lo stesso: un Message-ID spezzato da un folding mal ricomposto
 * produrrebbe un URL rotto, e qui l'unica cosa che possiamo fare e' non
 * peggiorarlo.
 */
fun messageIdNudo(raw: String?): String =
    raw?.trim().orEmpty()
        .removeSurrounding("<", ">")
        .filterNot { it.isWhitespace() }

/**
 * Il Message-ID nella forma canonica `<abc@example.org>`, oppure stringa vuota.
 *
 * E' quella che si copia negli appunti: le parentesi angolari FANNO PARTE del
 * Message-ID quando lo si scrive in un `References`, lo si cita in un messaggio
 * o lo si incolla in una segnalazione. Il link e' l'unico posto dove vanno
 * tolte, ed e' un dettaglio di quel formato — non del Message-ID.
 *
 * Si ricostruisce dal [messageIdNudo] invece di restituire il dato grezzo, cosi'
 * un archivio che per qualunque ragione lo avesse salvato senza parentesi
 * produce comunque una forma valida.
 */
fun messageIdCanonico(raw: String?): String {
    val nudo = messageIdNudo(raw)
    return if (nudo.isEmpty()) "" else "<$nudo>"
}

/**
 * Il link permanente all'articolo, oppure **stringa vuota** se non c'e' un
 * Message-ID utilizzabile.
 *
 * La stringa vuota e' deliberata: il chiamante disattiva l'azione invece di
 * copiare negli appunti un `https://usenet.id/` che non porta da nessuna parte.
 */
fun linkUsenetId(raw: String?): String {
    val nudo = messageIdNudo(raw)
    return if (nudo.isEmpty()) "" else PREFISSO_USENET_ID + percentCodifica(nudo)
}
