/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Threading degli articoli basato su References/In-Reply-To,
 * versione semplificata del classico algoritmo di Jamie Zawinski
 * (https://www.jwz.org/doc/threading.html).
 */
package org.openusenet.reader.thread

data class ThreadableArticle(
    val messageId: String,
    val references: String, // header References: message-id separati da spazi
    val subject: String,
    val dateEpoch: Long,
)

class ThreadNode(val article: ThreadableArticle?) {
    val children = ArrayList<ThreadNode>()
    var parent: ThreadNode? = null

    /**
     * Data più recente nel sottoalbero: usata per ordinare i thread.
     *
     * 1.2.12: resa ITERATIVA. Era ricorsiva come [flatten] e condivideva quindi
     * lo stesso rischio di StackOverflowError su un albero molto profondo, ma a
     * differenza di flatten qui non c'è nessun ordine o rientro da preservare —
     * è solo il massimo su un sottoalbero, quindi la riscrittura è a rischio
     * zero e toglie di mezzo una delle due ricorsioni.
     */
    fun latestDate(): Long {
        var max = article?.dateEpoch ?: 0L
        val daVisitare = ArrayDeque<ThreadNode>()
        daVisitare.addLast(this)
        while (daVisitare.isNotEmpty()) {
            val n = daVisitare.removeLast()
            n.article?.dateEpoch?.let { if (it > max) max = it }
            for (c in n.children) daVisitare.addLast(c)
        }
        return max
    }

    fun flatten(depth: Int = 0, out: MutableList<Pair<Int, ThreadableArticle>> = ArrayList()): List<Pair<Int, ThreadableArticle>> {
        article?.let { out.add(depth to it) }
        // 1.2.12 (audit di sicurezza): guardia di profondità. Questa funzione è
        // ricorsiva, quindi la profondità dell'albero è profondità di STACK: un
        // albero abbastanza profondo produce uno StackOverflowError, che è un
        // `Error` e non viene intercettato da nessun `catch (Exception)`
        // dell'app — crash secco. Un albero così profondo non esiste in una
        // discussione reale (poche decine di livelli); si ottiene solo di
        // proposito, ed è per questo che il tetto sta qui e non altrove.
        // Il limite gemello, a monte, è MAX_REFERENCES in Threader.thread.
        if (depth >= MAX_PROFONDITA) return out
        for (c in children.sortedBy { it.article?.dateEpoch ?: 0 }) {
            c.flatten(if (article != null) depth + 1 else depth, out)
        }
        return out
    }

    private companion object {
        /**
         * Oltre questo rientro la discussione smette di essere leggibile molto
         * prima di diventare pericolosa: tagliare qui non toglie nulla a un uso
         * reale. Vedi il commento in [flatten].
         */
        const val MAX_PROFONDITA = 300
    }
}

object Threader {

    private val MSG_ID = Regex("""<[^<>\s]+>""")

    /**
     * 1.2.12 (audit di sicurezza): quanti message-id si leggono al massimo
     * dall'header References di UN articolo.
     *
     * Senza questo tetto, un singolo articolo con un References molto lungo
     * (decine di migliaia di message-id, ~400 KB — accettati da molti server)
     * genera altrettanti container vuoti incatenati: un albero profondo
     * altrettanto, cioè uno StackOverflowError in [ThreadNode.flatten] per
     * chiunque apra quel gruppo, ad ogni apertura, finché non lo svuota.
     * Verificato eseguendo la logica di threading fuori da Android: sopra i
     * ~5.000 anelli l'errore si presenta a comando.
     *
     * 100 è molto più di qualunque discussione reale (RFC 5537 §3.4.4 suggerisce
     * ai client di TAGLIARE References lunghi, tenendo il primo e gli ultimi):
     * si tengono i primi e soprattutto gli ULTIMI, perché il padre diretto —
     * quello che decide dove appendere il messaggio — è l'ultimo della lista.
     */
    private const val MAX_REFERENCES = 100

    /**
     * Gli identificatori dichiarati nell'header References, **già tagliati**
     * come da [MAX_REFERENCES]: il primo anello piu' la coda.
     *
     * PERCHE' E' PUBBLICA (1.4.6, §A12). Serviva anche fuori da qui — al sync,
     * per far ereditare «seguito» alle risposte che arrivano dentro una
     * discussione gia' seguita. Ricopiare `Regex("<[^>]+>")` in un quinto posto
     * sarebbe stato facile e sbagliato per due motivi:
     *
     *  1. quella regex, usata altrove nell'app, e' **piu' larga** di questa:
     *     `<[^>]+>` accetta spazi e `<` annidati dentro l'identificatore;
     *  2. e soprattutto **non taglia**. Il tetto di 100 nasce dall'audit di
     *     sicurezza del 1.2.12: un solo articolo con un References di decine di
     *     migliaia di id (~400 KB, e molti server li accettano) e' un vettore
     *     reale. Una copia senza tetto lo riaprirebbe in un punto nuovo.
     *
     * *Un limite di sicurezza vale quanto il posto peggiore in cui e' scritto.*
     */
    fun idNelleReferences(references: String): List<String> {
        val tutti = MSG_ID.findAll(references).map { it.value }.toList()
        // 1.2.12: primo anello (la radice, che tiene insieme il thread) piu' la
        // coda (il padre diretto e i suoi antenati vicini).
        return if (tutti.size <= MAX_REFERENCES) tutti
               else listOf(tutti.first()) + tutti.takeLast(MAX_REFERENCES - 1)
    }

    /**
     * Costruisce la foresta di thread. Ritorna le radici ordinate per
     * attività più recente (il thread "vivo" sta in cima).
     */
    fun thread(articles: List<ThreadableArticle>, orphansAsRoots: Boolean = true): List<ThreadNode> {
        val byId = HashMap<String, ThreadNode>(articles.size * 2)

        // Passo 1: un nodo per ogni articolo scaricato
        for (a in articles) byId.getOrPut(a.messageId) { ThreadNode(a) }

        // Passo 2: ricostruisci la catena di parentela di OGNI messaggio dalle
        // sue References, creando CONTAINER VUOTI per gli anelli mancanti.
        //
        // È il pezzo che risolve i "thread troncati": se il messaggio radice (o
        // un anello intermedio) non è tra gli scaricati, senza container vuoti
        // ogni risposta diventerebbe una radice a sé e la discussione apparirebbe
        // spezzata in tanti tronconi che iniziano con "Re:". Il contenitore
        // fantasma (article = null) tiene insieme i figli di un padre assente;
        // flatten() lo salta, quindi non produce righe vuote a schermo — a
        // schermo la discussione resta una sola, solo senza il post iniziale.
        for (a in articles) {
            val node = byId[a.messageId] ?: continue
            // 1.4.6: il taglio del 1.2.12 sta in idNelleReferences, perche'
            // serve identico anche all'eredita' del «seguito» al sync.
            val refs = idNelleReferences(a.references)
            if (refs.isEmpty()) continue

            // Concatena i reference tra loro nell'ordine dichiarato (radice → …
            // → padre diretto), materializzando i mancanti come container vuoti.
            var prev: ThreadNode? = null
            for (ref in refs) {
                val cont = byId.getOrPut(ref) { ThreadNode(null) }
                if (prev != null && cont.parent == null && prev !== cont &&
                    !isDescendant(prev, cont) && !isDescendant(cont, prev)
                ) {
                    cont.parent = prev
                    prev.children.add(cont)
                }
                prev = cont
            }

            // Il padre diretto è l'ultimo reference; agganciaci il messaggio,
            // spostandolo se era già altrove (es. sotto un container).
            val parent = refs.lastOrNull()?.let { byId[it] }
            if (parent != null && parent !== node && !isDescendant(node, parent)) {
                node.parent?.children?.remove(node)
                node.parent = parent
                parent.children.add(node)
            }
        }

        // Passo 3: radici = nodi senza parent. Scarta i container vuoti che non
        // hanno figli (non aggiungono nulla) e, se un container vuoto è rimasto
        // radice con un solo figlio, promuovi il figlio per non mostrare un
        // livello di rientro inutile.
        var roots = byId.values.filter { it.parent == null && !(it.article == null && it.children.isEmpty()) }
        roots = roots.map { promoteLoneChild(it) }
        val kept = if (orphansAsRoots) roots
                   else roots.filter { it.article?.references?.isBlank() != false || it.children.isNotEmpty() }
        return kept.sortedByDescending { it.latestDate() }
    }

    /**
     * Se la radice è un container vuoto con un unico figlio, restituisce quel
     * figlio come nuova radice: evita di indentare tutta la discussione sotto un
     * livello fantasma quando il padre mancante aveva una sola risposta nota.
     */
    private fun promoteLoneChild(root: ThreadNode): ThreadNode {
        if (root.article == null && root.children.size == 1) {
            val figlio = root.children.first()
            figlio.parent = null
            return figlio
        }
        return root
    }

    /** true se [candidate] è già discendente di [node] (evita cicli con References corrotti). */
    private fun isDescendant(node: ThreadNode, candidate: ThreadNode): Boolean {
        var p: ThreadNode? = candidate.parent
        while (p != null) {
            if (p === node) return true
            p = p.parent
        }
        return false
    }

    /** Normalizza il subject per raggruppare "Re: Re: R: foo" → "foo". */
    fun normalizeSubject(s: String): String =
        s.replace(Regex("""^(\s*(re|r|aw|sv|fwd?)\s*(\[\d+\])?\s*:\s*)+""", RegexOption.IGNORE_CASE), "").trim()
}
