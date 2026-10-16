/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Memoria della posizione di lettura nella lista discussioni, per gruppo.
 *
 * COSA RICORDA E PERCHÉ (riscritto nella beta10)
 * ----------------------------------------------
 * La prima versione salvava l'INDICE della riga in cima allo schermo. Non
 * funzionava, per due motivi che si sommavano:
 *
 *  1. Rientrando nel gruppo, i messaggi arrivano dal database in modo
 *     asincrono: per un istante la lista è VUOTA. Una lista vuota non può
 *     essere scorsa, quindi la posizione corrente vale forzatamente 0 — e il
 *     salvataggio automatico, già in ascolto, scriveva quello 0 sopra la
 *     posizione buona. La memoria si cancellava da sola un attimo prima di
 *     essere usata.
 *
 *  2. Anche senza quel difetto, l'indice è un riferimento fragile: basta una
 *     discussione che si apre, un filtro attivato o messaggi nuovi arrivati
 *     con la sincronizzazione, e la riga numero 40 non è più lo stesso
 *     messaggio di prima. Si sarebbe tornati "nel punto giusto" sbagliato.
 *
 * Ora si ricorda l'ID DEL MESSAGGIO che stava in cima, più l'offset in pixel
 * per non far saltare la riga di qualche unità. Al rientro si cerca dove sia
 * finito quel messaggio nella lista attuale e ci si riposiziona. Se il
 * messaggio non c'è più (cancellato, filtrato, scaduto per conservazione) si
 * riparte dall'inizio: meglio un punto noto che uno arbitrario.
 *
 * Resta in memoria per la durata della sessione, non su disco: è comodità di
 * navigazione, e una posizione vecchia di giorni sarebbe più d'intralcio che
 * d'aiuto.
 *
 * BEST-OF-BOTH (beta14, deciso da Marco dopo feedback tester)
 * ------------------------------------------------------------
 * Un tester notava: entrando in un gruppo con articoli NUOVI, la lista
 * restava ancorata alla vecchia posizione e i nuovi (che arrivano in cima)
 * non si vedevano. Conflitto fra "riprendi da dov'eri" e "mostrami le
 * novità". Soluzione: oltre all'ancora, ricordo l'id del PRIMO articolo
 * della lista al momento dell'uscita. Al rientro:
 *  - se il primo articolo è cambiato (ne è arrivato uno nuovo sopra) →
 *    ci sono novità → vai in cima a mostrarle;
 *  - se il primo è lo stesso → nessuna novità → ripristina la posizione.
 * Nessun campo nuovo nel DB: l'informazione si ricava dalla lista stessa.
 */
package org.openusenet.reader.ui.screens

object ThreadScrollMemory {

    /**
     * @property articleId primo messaggio visibile (dove riposizionarsi);
     * @property offset scostamento in pixel;
     * @property topArticleId id del PRIMO articolo della lista all'uscita,
     *   per capire al rientro se sono arrivati articoli nuovi in cima.
     */
    data class Ancora(val articleId: Long, val offset: Int, val topArticleId: Long)

    private val ancore = HashMap<Long, Ancora>()

    fun get(groupId: Long): Ancora? = ancore[groupId]

    fun put(groupId: Long, articleId: Long, offset: Int, topArticleId: Long) {
        ancore[groupId] = Ancora(articleId, offset, topArticleId)
    }

    fun clear(groupId: Long) { ancore.remove(groupId) }
}
