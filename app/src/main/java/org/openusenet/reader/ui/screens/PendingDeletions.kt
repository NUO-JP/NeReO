/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Messaggi marcati per la cancellazione locale, in attesa di uscire dal gruppo.
 *
 * MODELLO (beta10)
 * ----------------
 * "Elimina" non cancella subito: contrassegna. Il messaggio resta in lista con
 * l'intestazione barrata e si può ripristinare toccandolo di nuovo, per tutto
 * il tempo che si resta nel gruppo. Uscendo, i contrassegnati vengono
 * cancellati davvero dalla copia locale.
 *
 * È il modello della posta IMAP: cancellare marca, l'eliminazione vera avviene
 * chiudendo la cartella. Rispetto alla versione precedente (cancellazione
 * immediata con qualche secondo di ANNULLA) dà tutto il tempo di ripensarci e
 * permette di cambiare idea su alcuni sì e altri no.
 *
 * Perché in memoria e non nel database: il contrassegno deve vivere solo
 * finché si resta nel gruppo, quindi non serve renderlo persistente — e così
 * si evita una migrazione dello schema, che è l'operazione più delicata da
 * fare su installazioni già in mano ai tester.
 *
 * Conseguenza da conoscere: se l'app viene chiusa dal sistema mentre si è
 * dentro un gruppo, i contrassegni si perdono e i messaggi restano. Meglio
 * questo del contrario — non si perde nulla che non fosse già destinato a
 * essere cancellato.
 */
package org.openusenet.reader.ui.screens

object PendingDeletions {
    private val perGruppo = HashMap<Long, MutableSet<Long>>()

    fun of(groupId: Long): Set<Long> = perGruppo[groupId] ?: emptySet()

    fun isMarked(groupId: Long, articleId: Long): Boolean =
        perGruppo[groupId]?.contains(articleId) == true

    fun mark(groupId: Long, ids: Collection<Long>) {
        perGruppo.getOrPut(groupId) { LinkedHashSet() }.addAll(ids)
    }

    fun unmark(groupId: Long, ids: Collection<Long>) {
        perGruppo[groupId]?.removeAll(ids.toSet())
    }

    /** Svuota e restituisce i contrassegnati: da chiamare uscendo dal gruppo. */
    fun drain(groupId: Long): List<Long> {
        val ids = perGruppo.remove(groupId)?.toList() ?: emptyList()
        return ids
    }
}
