/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui.screens

/**
 * 1.5.5 (voce 14 del quaderno) — DIRE DOVE UN RISULTATO HA FATTO MATCH.
 *
 * La ricerca guarda OGGETTO, AUTORE e, per i messaggi gia' aperti, CORPO
 * (ArticleDao.searchArticles). Nell'elenco dei risultati il giallo compariva
 * solo su oggetto e autore: un messaggio trovato per una parola del CORPO
 * restava senza nessun segno, e sembrava un risultato sbagliato. E' quello che
 * ha confuso BIG Umberto col «1.1.4» (8 ottobre): il risultato era giusto, la
 * parola stava nel testo.
 *
 * Qui si decide soltanto COSA aggiungere alla riga: se la parola si vede gia'
 * (oggetto o autore) non serve altro; se sta solo nel corpo, un breve
 * ESTRATTO intorno alla prima occorrenza, da mostrare col giallo come il
 * resto.
 *
 * Confronto: maiuscole/minuscole ignorate con `lowercase()`, lo stesso di
 * `highlightMatches`, cosi' il giallo e questa decisione non possono
 * divergere. (SQLite ignora le maiuscole solo sulle lettere ASCII: dove la
 * query del database e' piu' severa, al massimo qui si vede un giallo in piu',
 * mai uno in meno.) La query arriva GREZZA: nel database `_` e `%` sono gia'
 * neutralizzati da `testoPerLike`, qui sono caratteri come gli altri.
 *
 * Kotlin puro, nessun import: si prova da solo con kotlinc.
 */
data class Corrispondenza(
    val inOggetto: Boolean,
    val inAutore: Boolean,
    val inCorpo: Boolean,
    /** Estratto del corpo da mostrare, o `null` se non serve (o non c'e'). */
    val estratto: String?,
)

/** Caratteri di contesto prima e dopo la parola trovata. */
private const val ESTRATTO_PRIMA = 30
private const val ESTRATTO_DOPO = 50

fun doveCorrisponde(
    oggetto: String,
    autore: String,
    corpo: String?,
    query: String,
): Corrispondenza {
    val q = query.trim().lowercase()
    if (q.isEmpty()) return Corrispondenza(false, false, false, null)
    val inOggetto = oggetto.lowercase().contains(q)
    val inAutore = autore.lowercase().contains(q)
    val posCorpo = corpo?.lowercase()?.indexOf(q) ?: -1
    val inCorpo = posCorpo >= 0
    val estratto = if (inCorpo && !inOggetto && !inAutore)
        estrattoIntorno(corpo!!, posCorpo, q.length)
    else null
    return Corrispondenza(inOggetto, inAutore, inCorpo, estratto)
}

/**
 * Una finestra di testo intorno a [pos], su una riga sola: gli a-capo e le
 * spaziature multiple diventano uno spazio, «…» dove il testo e' stato
 * tagliato. Non spezza mai una coppia surrogata (emoji e simili).
 *
 * ⚠️ [pos] e' un indice nella versione `lowercase()` del corpo. Per le lingue
 * di NeReO la lunghezza non cambia; dove cambiasse (rarissimi caratteri come
 * «İ»), l'indice viene comunque riportato dentro i limiti del testo.
 */
fun estrattoIntorno(corpo: String, pos: Int, lunghezza: Int): String {
    var inizio = (pos - ESTRATTO_PRIMA).coerceIn(0, corpo.length)
    var fine = (pos + lunghezza + ESTRATTO_DOPO).coerceIn(inizio, corpo.length)
    if (inizio in 1 until corpo.length && corpo[inizio].isLowSurrogate()) inizio--
    if (fine < corpo.length && fine > 0 && corpo[fine - 1].isHighSurrogate()) fine++
    val pezzo = corpo.substring(inizio, fine).replace(Regex("""\s+"""), " ").trim()
    val prima = if (inizio > 0) "…" else ""
    val dopo = if (fine < corpo.length) "…" else ""
    return prima + pezzo + dopo
}
