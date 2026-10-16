/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.4.7 (§A18) — DOVE ERI NELL'ELENCO DEI GRUPPI.
 *
 * PERCHE' ESISTE. Segnalazione di **Jeff**, 5 ottobre: scorrendo l'elenco dei
 * gruppi per vedere quali hanno novita', entrando in uno e tornando indietro la
 * lista ripartiva dall'inizio — e con una sessantina di gruppi su due server
 * significa riscorrere tutto per leggerne uno sei righe piu' su.
 *
 * L'elenco MESSAGGI aveva una memoria del genere da beta14 ([ThreadScrollMemory]);
 * l'elenco GRUPPI non l'ha mai avuta. Non era una decisione: era una cucitura
 * fra due schermate, come le altre due che Jeff ha trovato nella stessa
 * settimana.
 *
 * ⚠️ PERCHE' NON SI RIUSA [ThreadScrollMemory], ED E' UNA SCELTA.
 * Quella ricorda ANCHE l'id del primo elemento, per distinguere «nessuna
 * novita', rimettiti dov'eri» da «sono arrivati messaggi nuovi in cima, vai a
 * vederli»: nell'elenco messaggi i nuovi arrivano in testa e riordinano la
 * lista. **Qui quel conflitto non esiste**: l'ordine dei gruppi lo decide
 * l'utente a mano (`groups_move_up` / `groups_move_down`) e non si riordina da
 * solo — un gruppo con messaggi nuovi resta dov'e', cambia solo il contatore.
 *
 * Unificare le due memorie porterebbe qui una complicazione che non serve. *Si
 * estrae cio' che e' uguale per caso, non cio' che e' diverso a ragione.*
 *
 * Come [ThreadScrollMemory], vive in memoria per la durata della sessione e non
 * su disco: e' comodita' di navigazione, non uno stato da conservare.
 */
package org.openusenet.reader.ui.screens

object GroupScrollMemory {

    /**
     * @property chiave la chiave dell'elemento in cima allo schermo — la stessa
     *   che la `LazyColumn` usa per quella riga (`srv-<etichetta>` per
     *   un'intestazione di server, `grp-<id>` per un gruppo).
     * @property offset scostamento in pixel dentro quella riga.
     *
     * **Si registra COSA c'era in cima, non a che riga si era.** E' la lezione
     * di [ThreadScrollMemory]: un indice diventa sbagliato appena l'elenco
     * cambia — e qui basta disiscriversi da un gruppo, o aggiungere un server,
     * perche' tutte le righe sotto scalino di posto.
     */
    data class Ancora(val chiave: String, val offset: Int)

    private var ancora: Ancora? = null

    /**
     * L'elenco dei gruppi e' **uno solo** in tutta l'applicazione, quindi qui
     * non serve la mappa per-gruppo che ha [ThreadScrollMemory].
     */
    fun get(): Ancora? = ancora

    fun put(chiave: String, offset: Int) {
        ancora = Ancora(chiave, offset)
    }

    fun clear() {
        ancora = null
    }
}
