/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Catalogo di server Usenet noti (solo testo, gerarchie di discussione).
 *
 * Serve a compilare in fretta host/porta/SSL di un nuovo server: l'utente
 * sceglie dall'elenco e digita solo utente e password dove servono. È lo
 * stesso comodo dei client VoIP che preimpostano i provider noti.
 *
 * NB: sono coordinate di CONNESSIONE, non credenziali. Ognuno usa le proprie
 * dove il server le richiede; per i due aperti non serve nulla.
 */
package org.openusenet.reader.data

data class ServerPreset(
    val label: String,
    val host: String,
    val port: Int,
    val ssl: Boolean,
    /**
     * 1.3.6: STARTTLS sulla porta in chiaro (RFC 4642) — alternativo a [ssl],
     * mai insieme. Serviva a OVH e non c'era: un preset poteva dire "563
     * cifrato" o "119 in chiaro", ma non "119 con upgrade a cifrato", che e'
     * esattamente la configurazione giusta per quel server.
     */
    val startTls: Boolean = false,
    /** true = accesso libero senza registrazione. */
    val aperto: Boolean,
    /** breve nota mostrata sotto il nome. */
    val nota: String,
)

object ServerPresets {
    /*
     * 1.3.5 — I TRE SERVER CON ACCOUNT NASCONO SU 563 CIFRATO.
     *
     * Prima erano tutti e cinque "119, ssl = false". Per i due aperti va bene:
     * non c'e' password da proteggere. Per i tre che RICHIEDONO un account era
     * un errore garantito al primo tentativo, a chiunque, sempre: su 119 in
     * chiaro la guardia della 1.2.6 si rifiuta di inviare la password e l'utente
     * riceve un 483. L'ha trovato un utente inglese su Solani ed eternal-september
     * ("What am I doing wrong?" — niente: era il percorso normale a essere
     * sbagliato).
     *
     * Non e' un caso limite ed e' proprio la strada maestra: chi sceglie dall'
     * elenco lo fa perche' NON vuole decidere porta e cifratura da se'.
     * Dargli i valori che non funzionano e' il contrario di un aiuto.
     *
     * Perche' 563/SSL e non "119 + STARTTLS": lo STARTTLS dipende da cio' che il
     * server annuncia in CAPABILITIES e oggi, se fallisce, ricade in chiaro —
     * questione aperta e da decidere con Bingo3331 (RFC 4642, caso B). La porta
     * 563 e' cifrata dal primo byte e non ha ripieghi. Una decisione alla volta.
     *
     * Nessun rischio per chi ha gia' un server configurato: questi valori si
     * applicano SOLO alla creazione di un server nuovo dall'elenco, non
     * toccano nulla di esistente.
     */
    val all: List<ServerPreset> = listOf(
        ServerPreset(
            "Eternal September", "news.eternal-september.org", 563, true,
            aperto = false, nota = "Registrazione gratuita sul sito",
        ),
        ServerPreset(
            "Solani", "news.solani.org", 563, true,
            aperto = false, nota = "Registrazione gratuita sul sito",
        ),
        /*
         * 1.3.6 — OVH TORNA SU 119, MA CON STARTTLS. Correzione di un errore
         * mio della 1.3.5.
         *
         * Nella 1.3.5 avevo messo OVH su 563/SSL insieme agli altri due server
         * "con account", trattandoli come un gruppo omogeneo. Ma il nostro
         * stesso documento della 1.2.17 (NeReO_ovh_starttls_in_chiaro.md),
         * nato dal log di Marco, diceva che OVH NON offre TLS su nessuna
         * porta: l'ho contraddetto senza accorgermene, e chi sceglieva OVH
         * dall'elenco si ritrovava una configurazione impossibile — cioe' lo
         * stesso difetto che la 1.3.5 doveva correggere, dall'altro lato.
         *
         * Il 14 settembre 2026 l'amministratore ha abilitato STARTTLS sulla
         * 119 (563 resta non implementata). MISURATO da Marco sul suo
         * telefono, non riferito: "OVH su 119 con STARTTLS si collega
         * davvero". E' la stessa regola di Paganini — la misura prima della
         * parola, compresa la nostra.
         */
        ServerPreset(
            "OVH", "news.usenet.ovh", 119, false, startTls = true,
            aperto = false, nota = "Registrazione gratuita sul sito",
        ),
        ServerPreset(
            "tcpreset", "news.tcpreset.net", 119, false,
            aperto = true, nota = "Nessuna registrazione",
        ),
        ServerPreset(
            "Paganini (BOFH)", "paganini.bofh.team", 119, false,
            aperto = true, nota = "Nessuna registrazione",
        ),
    )
}
