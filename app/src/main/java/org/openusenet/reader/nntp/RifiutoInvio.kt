/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.nntp

/**
 * 1.5.5 (voce 15 del quaderno) — RICONOSCERE IL 441 E SPIEGARLO.
 *
 * Il 7 ottobre Ammammata prepara un post corto e all'invio riceve
 * «441 Article must contain real content». NeReO aveva fatto l'unica cosa
 * giusta: riportare la risposta del SERVER parola per parola. Ma e' testo
 * grezzo, in inglese, e chi scrive un messaggio corto in italiano non ha modo
 * di capire che deve solo scrivere di piu' (con un testo piu' lungo, il suo
 * messaggio e' passato).
 *
 * Il testo del server RESTA, intero: questa funzione decide soltanto se
 * aggiungergli una riga di spiegazione. Due casi:
 *  - CONTENUTO_INSUFFICIENTE: 441 + «real content». E' il caso verificato
 *    con Ammammata; il rimedio e' scrivere di piu'.
 *  - RIFIUTATO: qualunque altro 441. RFC 3977 §6.3.1 lo definisce «posting
 *    failed» e lascia il motivo al testo libero del server: non lo
 *    indoviniamo, diciamo solo che il motivo e' quello.
 *
 * ⚠️ Non si sa se quel rifiuto venga dal server, dal gruppo o da una soglia
 * generale (la seconda prova con Ammammata non e' stata fatta): la
 * spiegazione dice «il server», che e' vero in tutti e tre i casi.
 *
 * Kotlin puro, nessun import: si prova da solo con kotlinc.
 */
enum class RifiutoInvio { CONTENUTO_INSUFFICIENTE, RIFIUTATO }

private val CODICE_441 = Regex("""(?:^|\s)441(?:\s|$)""")

/**
 * [messaggio] e' il testo dell'errore d'invio così come arriva all'interfaccia
 * (da `NntpException`: «<codice> <testo del server>»). `null` = nessuna riga
 * da aggiungere.
 */
fun riconosciRifiutoInvio(messaggio: String?): RifiutoInvio? {
    val m = messaggio?.trim().orEmpty()
    if (!CODICE_441.containsMatchIn(m)) return null
    return if (m.contains("real content", ignoreCase = true))
        RifiutoInvio.CONTENUTO_INSUFFICIENTE
    else
        RifiutoInvio.RIFIUTATO
}
