/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui

/**
 * 1.5.5 (G1, G3 del brainstorming del 17 ottobre, scelte di Marco) — COME SI
 * PRESENTA UN AUTORE: iniziali, colore, data.
 *
 * G1. Quando l'autore non ha una Face, al suo posto un cerchio con le
 * INIZIALI su un colore FISSO per quell'autore (come Gmail): lo stesso autore
 * ha sempre lo stesso colore, in ogni gruppo e a ogni apertura, e lo si
 * riconosce a colpo d'occhio. Il colore si sceglie dall'INDIRIZZO, non dal
 * nome: chi cambia nickname tenendo l'indirizzo resta dello stesso colore.
 *
 * G3. La data dell'intestazione dice «oggi», «ieri» o il giorno della
 * settimana quando il messaggio e' recente, e la data piena altrimenti.
 * Calendario e non ore trascorse: «ieri 23:50» e' piu' chiaro di «10 minuti
 * fa» detto alle 00:00, e non servono plurali («1 ora» / «2 ore»).
 *
 * Kotlin puro, nessun import: si prova da solo con kotlinc.
 */

/** Toglie virgolette e spazi ai bordi: `"Mario Rossi"` -> `Mario Rossi`. */
private fun ripulisci(s: String): String = s.trim().trim('"', '\'').trim()

/**
 * Le iniziali da mostrare: due per un nome di piu' parole («Mario Rossi» ->
 * «MR»), una per una parola sola («ObiWan» -> «O»), «?» se non c'e' niente.
 * [nome] e' quello mostrato (authorDisplayName); i due casi strani di Usenet
 * si riconoscono qui: il vecchio formato `indirizzo (Nome)` e il nome assente,
 * dove resta solo l'indirizzo (si usa la parte prima della chiocciola).
 */
fun inizialiAutore(nome: String): String {
    var n = ripulisci(nome)
    val aperta = n.indexOf('(')
    val chiusa = n.lastIndexOf(')')
    if (aperta >= 0 && chiusa > aperta + 1) n = ripulisci(n.substring(aperta + 1, chiusa))
    if ('@' in n && ' ' !in n) n = n.substringBefore('@')
    // parole fatte di lettere o cifre; tutto il resto separa (punti, trattini, _)
    val parole = n.split(Regex("""[^\p{L}\p{N}]+""")).filter { it.isNotEmpty() }
    if (parole.isEmpty()) return "?"
    fun primo(p: String): String {
        val cp = p.codePointAt(0)
        return String(Character.toChars(cp)).uppercase()
    }
    return if (parole.size == 1) primo(parole[0]) else primo(parole[0]) + primo(parole.last())
}

/**
 * La chiave che decide il colore: l'indirizzo (minuscolo) se c'e', altrimenti
 * il campo intero. `Nome <a@b>`, `a@b (Nome)`, `a@b`.
 */
fun chiaveColoreAutore(fromField: String): String {
    val f = fromField.trim()
    val tra = f.substringAfter('<', "").substringBefore('>', "").trim()
    if ('@' in tra) return tra.lowercase()
    val parola = f.split(' ', '\t').firstOrNull { '@' in it }
    if (parola != null) return parola.trim('<', '>', '(', ')', '"').lowercase()
    return f.lowercase()
}

/**
 * Indice nella tavolozza [n] per [chiave]. FNV-1a a 32 bit: stabile fra
 * versioni dell'app e di Android (non dipende da `hashCode`, che per le String
 * e' fissato dal linguaggio ma e' meglio non legarci un colore che l'utente
 * impara a riconoscere).
 */
fun indiceColoreAutore(chiave: String, n: Int): Int {
    if (n <= 0) return 0
    var h = 0x811C9DC5.toInt()
    for (b in chiave.encodeToByteArray()) {
        h = h xor (b.toInt() and 0xFF)
        h *= 0x01000193
    }
    return ((h.toLong() and 0xFFFFFFFFL) % n).toInt()
}

/**
 * Dodici colori medio-scuri: le iniziali in BIANCO hanno contrasto almeno
 * 4:1 su ognuno (misurato con la formula WCAG, minimo 4,10 sul verde oliva),
 * sia sul tema chiaro sia su quello scuro. Nessuno e' il viola esatto
 * dell'app, per non confondersi coi pulsanti. ARGB.
 */
val COLORI_AUTORE: List<Long> = listOf(
    0xFF1565C0, // blu
    0xFF2E7D32, // verde
    0xFFC62828, // rosso mattone
    0xFF8E24AA, // porpora
    0xFF00796B, // verde acqua
    0xFFD84315, // arancio scuro
    0xFF3949AB, // indaco
    0xFF6D4C41, // marrone
    0xFFAD1457, // magenta
    0xFF00838F, // ciano scuro
    0xFF558B2F, // verde oliva
    0xFF5E35B1, // viola scuro
)

/** Come si scrive la data di un messaggio nell'intestazione. */
enum class TipoData { OGGI, IERI, SETTIMANA, DATA }

/**
 * [giornoMessaggio] e [giornoOggi] sono giorni di calendario (epoch day) nel
 * fuso del telefono. Un messaggio «dal futuro» (orologio del mittente avanti)
 * ha la data piena: «oggi» sarebbe falso.
 */
fun tipoData(giornoMessaggio: Long, giornoOggi: Long): TipoData = when (giornoOggi - giornoMessaggio) {
    0L -> TipoData.OGGI
    1L -> TipoData.IERI
    in 2L..6L -> TipoData.SETTIMANA
    else -> TipoData.DATA
}
