/*
 * Navigazione indietro sicura (beta21 — bug segnalato da Marco).
 *
 * SINTOMO: aprendo un gruppo e toccando 2-3 volte RAPIDAMENTE la freccia
 * "indietro" in alto a sinistra, l'app resta con lo schermo vuoto (nero, bianco
 * o del colore di sfondo scelto in Aspetto). L'app non e' bloccata: e' viva ma
 * non ha piu' niente da disegnare, e l'unico modo per uscirne e' chiuderla.
 *
 * CAUSA: ogni pulsante chiamava direttamente `nav.popBackStack()`. Con tap
 * ravvicinati i clic si accumulano prima che la ricomposizione aggiorni lo
 * stato della navigazione: il primo pop toglie la schermata corrente, quelli
 * successivi svuotano il back stack ANCHE OLTRE LA RADICE. A quel punto il
 * NavHost non ha piu' alcuna destinazione da mostrare e disegna il vuoto.
 *
 * E' probabilmente anche la spiegazione della "schermata nera" segnalata da
 * alcuni tester come crash sporadico: stesso sintomo, stessa dinamica.
 *
 * RIMEDIO: togliere una schermata solo se ce n'e' davvero una precedente a cui
 * tornare. Il controllo su `previousBackStackEntry` fa esattamente questo: se
 * siamo gia' sulla radice, il tocco non fa nulla invece di svuotare tutto.
 */
package org.openusenet.reader.ui

import androidx.navigation.NavHostController

/**
 * Torna alla schermata precedente SOLO se esiste. Sostituisce le chiamate
 * dirette a `nav.popBackStack()` nei pulsanti "indietro": e' idempotente
 * rispetto ai tap ripetuti, quindi non puo' lasciare l'app senza schermate.
 */
fun NavHostController.indietroSicuro() {
    if (previousBackStackEntry != null) {
        popBackStack()
    }
}
