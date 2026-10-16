/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * M1 (release personale Marco) — propagazione di una regola alle RISPOSTE.
 *
 * PROBLEMA. Plonkare un troll con una regola su From lo fa sparire, ma lascia
 * in piedi tutte le risposte: il testo del troll ti arriva comunque, citato da
 * chi gli ha risposto. Si vorrebbe far sparire il ramo, SENZA però plonkare
 * anche chi ha risposto — che negli altri thread deve restare perfettamente
 * visibile.
 *
 * PRINCIPIO. L'albero non va ricostruito: ogni articolo se lo porta già dietro
 * serializzato nell'header References:, che contiene la catena degli antenati
 * dalla radice al padre diretto. Quindi
 *
 *     colpito(a)  ⟺  refs(a) ∩ P ≠ ∅
 *
 * con P = insieme dei message-id "avvelenati". È un'intersezione di insiemi,
 * niente Threader.thread() — che è O(n) per radice ed è già stato la causa
 * della regressione beta20 sui gruppi da ~47.000 articoli (rpoggi, it.test).
 *
 * IL TRABOCCHETTO. RFC 5536 consente ai client di POTARE gli elementi CENTRALI
 * della catena quando l'header cresce troppo, conservando il primo e gli
 * ultimi. Su un thread profondo il message-id del troll può quindi sparire dal
 * mezzo del References: di una risposta al 12°-15° livello, e l'intersezione
 * secca fallisce in silenzio — il ramo "risorge" a metà, che è esattamente il
 * sintomo peggiore perché sembra un bug casuale.
 *
 * RIMEDIO. Non una sola intersezione ma la CHIUSURA TRANSITIVA, percorrendo
 * gli articoli in ordine di data crescente: se il padre diretto è già colpito,
 * lo sono anch'io, anche se il mio References: non nomina più l'antenato
 * originale. Una passata, O(n·k) con k = lunghezza media della catena.
 *
 * Puro Kotlin/JVM, nessuna dipendenza da Room o da Android: testabile in
 * app/src/test come il resto del package filter.
 */
package org.openusenet.reader.filter

/** Articolo "visto" dal propagatore: solo ciò che serve a risalire l'albero. */
data class PropagationInput(
    val messageId: String,
    /** header References: — message-id separati da spazi. */
    val references: String,
    val dateEpoch: Long,
    /** true se l'articolo fa match su una regola avvelenante (è un "seme"). */
    val seedPoisoned: Boolean = false,
)

object FollowupPropagator {

    /** Stesso pattern usato da Threader: un solo modo di riconoscere un message-id. */
    private val MSG_ID = Regex("""<[^<>\s]+>""")

    /** Estrae i message-id dalla catena References:, nell'ordine dichiarato. */
    fun parseReferences(references: String): List<String> =
        MSG_ID.findAll(references).map { it.value }.toList()

    /**
     * Ritorna i messageId degli articoli colpiti PER PROPAGAZIONE.
     *
     * Esclusi dal risultato:
     *  - i semi stessi (li ha già gestiti FilterEngine con il suo score);
     *  - gli articoli già presenti in [alreadyPoisoned], che sono storia vecchia.
     *
     * @param articles   articoli da valutare. L'ordine in ingresso è
     *                   irrilevante: il metodo li riordina per data.
     * @param alreadyPoisoned message-id già noti come avvelenati — le "lapidi"
     *                   dei post del troll cancellati ai sync precedenti, più
     *                   gli articoli già marcati che sono ancora nel database.
     *                   Senza questo insieme la funzione sembra corretta al
     *                   primo sync e smette di funzionare dal secondo.
     * @param mode       NONE ⇒ nessun effetto; DIRECT ⇒ un solo livello;
     *                   SUBTREE ⇒ tutta la discendenza.
     */
    fun propagate(
        articles: List<PropagationInput>,
        alreadyPoisoned: Set<String>,
        mode: FollowupMode,
    ): Set<String> {
        if (mode == FollowupMode.NONE) return emptySet()
        if (articles.isEmpty()) return emptySet()

        // P: insieme avvelenato. Parte dalle lapidi e dai semi di questo lotto.
        val p = HashSet<String>(alreadyPoisoned.size + articles.size)
        p.addAll(alreadyPoisoned)
        for (a in articles) if (a.seedPoisoned) p.add(a.messageId)

        val colpiti = HashSet<String>()

        // Ordine di data crescente: garantisce che il padre sia già stato
        // processato quando si valuta il figlio, ed è ciò che rende la
        // propagazione transitiva anche con References potato.
        for (a in articles.sortedBy { it.dateEpoch }) {
            if (a.seedPoisoned) continue
            if (a.messageId in alreadyPoisoned) continue

            val refs = parseReferences(a.references)
            if (refs.isEmpty()) continue

            val colpito = when (mode) {
                // DIRECT guarda SOLO il padre diretto (ultimo reference).
                FollowupMode.DIRECT -> refs.last() in p
                FollowupMode.SUBTREE -> refs.any { it in p }
                FollowupMode.NONE -> false
            }
            if (!colpito) continue

            colpiti.add(a.messageId)

            // ATTENZIONE: solo in SUBTREE l'articolo colpito diventa a sua volta
            // avvelenato. In DIRECT no — altrimenti la propagazione si
            // propagherebbe di nuovo al livello successivo e DIRECT degenererebbe
            // in SUBTREE, rendendo indistinguibili le due modalità.
            if (mode == FollowupMode.SUBTREE) p.add(a.messageId)
        }
        return colpiti
    }

    /**
     * Variante comoda per il caso reale: più regole avvelenanti insieme, ognuna
     * con la propria modalità e il proprio punteggio. Ritorna, per ogni
     * messageId colpito, il punteggio PIÙ BASSO fra quelli delle regole che lo
     * hanno colpito — coerente con la logica a soglie del motore, dove vince
     * sempre il vincolo più stringente.
     *
     * [semiPerRegola] associa ogni regola all'insieme dei message-id che la
     * fanno scattare in questo lotto; [lapidiPerRegola] fa lo stesso per le
     * lapidi già in archivio.
     */
    fun propagateMulti(
        articles: List<PropagationInput>,
        regole: List<FilterRule>,
        semiPerRegola: Map<Long, Set<String>>,
        lapidiPerRegola: Map<Long, Set<String>>,
    ): Map<String, Int> {
        val esito = HashMap<String, Int>()
        for (regola in regole) {
            if (!regola.propaga) continue
            val semi = semiPerRegola[regola.id].orEmpty()
            val lapidi = lapidiPerRegola[regola.id].orEmpty()
            if (semi.isEmpty() && lapidi.isEmpty()) continue

            val input = articles.map { a ->
                if (a.messageId in semi) a.copy(seedPoisoned = true) else a.copy(seedPoisoned = false)
            }
            val colpiti = propagate(input, lapidi, regola.followup)
            val punteggio = regola.followupScoreEffettivo
            for (id in colpiti) {
                val precedente = esito[id]
                if (precedente == null || punteggio < precedente) esito[id] = punteggio
            }
        }
        return esito
    }
}
