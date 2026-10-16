/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Nome visibile dell'applicazione e valori derivati.
 *
 * Il nome e' cambiato due volte (OpenUsenet -> MyNewsReader -> NeReO) e ogni
 * volta era sparso in piu' file. Da qui in avanti sta in un punto solo.
 *
 * NOTA IMPORTANTE: il package e l'applicationId restano "org.openusenet.reader"
 * e NON vanno mai cambiati. Cambiarli farebbe installare l'APK come app
 * separata, con perdita di database, sottoscrizioni e impostazioni dei tester.
 */
package org.openusenet.reader

object Branding {
    /** Nome visibile in barra del titolo e nome applicazione. */
    const val APP_NAME: String = "NeReO"

    /** Scioglimento dell'acronimo: Ne(ws) Re(ader) O(pen-source). */
    const val APP_TAGLINE: String = "News Reader Open-source"

    /**
     * Valore predefinito dell'intestazione User-Agent dichiarata nei post,
     * e valore PROPOSTO nel campo User-Agent di un'identità nuova.
     *
     * ### 1.3.2 (Dave Royal): la forma richiesta dalla RFC 5536
     *
     * Fino alla 1.3.1 questo valore si espandeva in `NeReO 1.3.1 (Android)`,
     * con uno SPAZIO fra nome e versione. La RFC 5536 §3.2.13 definisce:
     *
     * ```
     * user-agent      =  "User-Agent:" SP 1*product [CFWS] CRLF
     * product         =  [CFWS] token [ [CFWS] "/" product-version ]
     * product-version =  [CFWS] token
     * ```
     *
     * La versione si attacca al prodotto con `"/"`. Con lo spazio, quella
     * intestazione non era *illegale* — `1*product` ammette più prodotti e
     * `1.3.1` è un token valido — ma diceva un'altra cosa: **due prodotti**,
     * uno chiamato "NeReO" e uno chiamato "1.3.1", invece di un prodotto alla
     * sua versione. Chi raccoglie statistiche sui newsreader leggeva un
     * software inesistente di nome "1.3.1". Il commento `(Android)` è CFWS ed
     * era ed è corretto.
     *
     * Forma corretta, dalla 1.3.2: `NeReO/1.3.2 (Android)`.
     *
     * ### Perché `%versione%` e non `%nereo%`
     *
     * Il segnaposto `%nereo%` si espande in "NeReO 1.3.2" (con lo spazio) ed è
     * usato anche nelle FIRME, dove quella forma è quella giusta. Cambiarne
     * l'espansione avrebbe corretto lo User-Agent riscrivendo però la firma di
     * chiunque lo usi — un effetto collaterale su testo scritto dall'utente.
     * Qui si usa quindi `%versione%`, che esiste già, con la barra scritta
     * esplicitamente nel modello.
     *
     * ### 1.2.13 (Merovingio, tramite Marco) — perché il campo nasce compilato
     *
     * Il problema segnalato: dalla 1.2.9 un campo vuoto significa davvero "non
     * dichiarare nulla" (era il senso della correzione W), ma su un'app appena
     * installata il campo NASCE vuoto — quindi l'utente medio, che non lo
     * compila, posta anonimo senza averlo scelto. L'anonimato deve restare una
     * scelta, non il risultato di una distrazione: ora il campo si presenta
     * già compilato e chi non lo vuole lo svuota, che è l'esatto contrario.
     *
     * Contiene il SEGNAPOSTO `%nereo%`, non il testo già espanso: viene
     * risolto al momento dell'invio (vedi [espandiVersione]), così lo
     * User-Agent dichiarato resta aggiornato da solo ad ogni release invece di
     * congelare per sempre la versione con cui l'identità è stata creata. È la
     * stessa convenzione già in uso nelle firme.
     *
     * 1.3.2: era `DEFAULT_USER_AGENT` la seconda costante che diceva la stessa
     * cosa ("NeReO (Android)", senza versione, usata solo dalla migrazione
     * delle vecchie preferenze). Due nomi per "lo User-Agent predefinito" sono
     * esattamente la forma di duplicazione che in questo progetto ha già
     * prodotto un difetto pubblico: ne resta uno solo.
     */
    const val USER_AGENT_PREDEFINITO: String = "$APP_NAME/%versione% (Android)"

    /**
     * Risolve i segnaposto di versione in un testo dell'utente:
     *   %versione% → "1.3"      (l'esempio segue la versione corrente)
     *   %nereo%    → "NeReO 1.3"
     *
     * Unica definizione condivisa: la usano sia la firma (ComposeLogic) sia lo
     * User-Agent (Repository.postArticle). Averne due copie significherebbe,
     * prima o poi, vederle divergere.
     */
    fun espandiVersione(testo: String): String = testo
        .replace("%versione%", ReleaseNotes.VERSION)
        .replace("%nereo%", "$APP_NAME ${ReleaseNotes.VERSION}")

    /**
     * User-Agent predefiniti usati dalle versioni precedenti. Servono alla
     * migrazione: se il tester ha salvato l'identita' quando l'app si chiamava
     * ancora in un altro modo — o prima della correzione RFC 5536 della 1.3.2 —
     * il vecchio valore e' rimasto dov'era e va aggiornato una volta sola.
     *
     * REGOLA, invariata dal 1.2.x: si sostituisce SOLO una corrispondenza
     * ESATTA con un valore che abbiamo spedito noi come predefinito. Un
     * User-Agent scritto dall'utente non compare in questa lista e non viene
     * mai toccato, nemmeno se somiglia a uno dei nostri.
     *
     * Nota: questa e' la ragione per cui non si usa un'espressione regolare
     * "tutto quello che assomiglia a NeReO qualcosa (Android)". Sarebbe piu'
     * comoda e riscriverebbe anche quello che l'utente ha scelto a mano.
     */
    val LEGACY_USER_AGENTS: List<String> = listOf(
        "MyNewsReader (Android)",
        "OpenUsenet (Android)",
        // predefiniti NeReO fino alla 1.3.1 compresa
        "$APP_NAME (Android)",     // ex DEFAULT_USER_AGENT (senza versione)
        "%nereo% (Android)",       // ex USER_AGENT_PREDEFINITO (1.2.13 → 1.3.1)
    )
}
