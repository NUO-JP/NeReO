/*
 * Filtri: azioni etichettate ↔ score (beta18, Piano B — richiesta Marco).
 *
 * Il motore dei filtri (FilterEngine) resta a PUNTEGGIO cumulativo con soglie:
 * questa è solo una traduzione davanti, per la UI. L'utente sceglie un'AZIONE
 * con nome umano ("Ignora", "Evidenzia"...) e qui la convertiamo nel delta di
 * score corrispondente. Chi vuole la cumulabilità (sommare più contributi)
 * passa alla modalità avanzata e mette il numero a mano.
 *
 * Le soglie di riferimento (da FilterEngine / FiltersScreen):
 *   -10000 / -9999  → elimina (kill totale)
 *   -100            → nascondi
 *   +100            → evidenzia
 *   +9999           → segui il thread (watch forte)
 */
package org.openusenet.reader.ui.screens

/**
 * Azioni "umane" che l'utente può scegliere senza ragionare in numeri. Ognuna
 * mappa su un delta di score noto. AVANZATA non ha un valore fisso: segnala che
 * l'utente vuole inserire il numero a mano (cumulabilità).
 */
enum class FilterAction(val label: String, val score: Int?) {
    IGNORA("Ignora (nascondi)", -100),
    ELIMINA("Elimina del tutto", -9999),
    EVIDENZIA("Evidenzia", 100),
    SEGUI("Segui il thread", 9999),
    AVANZATA("Avanzato (punteggio a mano)", null),
}

/**
 * Dato uno score, ritorna l'azione corrispondente se coincide esattamente con
 * una nota, altrimenti AVANZATA (valore custom, tipicamente frutto di
 * cumulabilità o di un delta intermedio come +50). Così una regola creata in
 * modalità avanzata si riapre in avanzata, e una creata con un'azione si riapre
 * sull'azione giusta.
 */
fun actionForScore(score: Int): FilterAction =
    FilterAction.entries.firstOrNull { it.score == score } ?: FilterAction.AVANZATA
