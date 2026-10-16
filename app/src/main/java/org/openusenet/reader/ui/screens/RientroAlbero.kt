/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.4.7 (§A15, build 1) — IL RIENTRO DELL'ALBERO, IN UN POSTO SOLO.
 *
 * PERCHE' ESISTE. Dave Royal, 2 ottobre: «In panel mode column 2 there are
 * vertical bars to the left of the indented subject line. I do not get these in
 * phone mode — including in the "Conversation tree". By design?»
 *
 * Guardando, la stessa formula era scritta **tre volte**, in tre file che non
 * condividono un nome:
 *
 *   ThreadListScreen.kt   (item.depth.coerceAtMost(8) * 12).dp     niente rotaie
 *   ArticleScreen.kt      (item.depth.coerceAtMost(8) * 12).dp     niente rotaie
 *   ThreePaneHome.kt      passo * depth.coerceAtMost(8)            CON le rotaie
 *
 * Nessuno script del progetto poteva vederlo: non e' un simbolo, e' una
 * formula. E le tre copie erano gia' divergenti su un ornamento che si vede a
 * occhio nudo — la specifica lo registra («nel pannello centrale … rotaie
 * verticali»), ma la ragione che adduce, «per leggere subito la gerarchia»,
 * vale identica sulle altre due viste. Era descritto, non deciso.
 *
 * ⚠️ QUESTA BUILD NON CAMBIA NIENTE DI QUELLO CHE SI VEDE, ED E' IL PUNTO.
 * Le rotaie restano **solo** nei pannelli, esattamente dove sono sempre state.
 * Si unificano prima le tre copie, a resa invariata; le rotaie si accendono
 * altrove nella build successiva. Cosi', se qualcosa si rompe, sappiamo se e'
 * stata la ristrutturazione o l'aspetto nuovo — principio 1 del quaderno, lo
 * strumento di misura precede di una build la modifica.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.openusenet.reader.AppSettings

/**
 * Oltre questa profondita' il rientro smette di crescere.
 *
 * Non e' un capriccio: a otto livelli il testo e' gia' schiacciato contro il
 * bordo destro, e su una colonna stretta continuare vorrebbe dire renderlo
 * illeggibile per rappresentare una gerarchia che nessuno riesce piu' a
 * seguire. Il valore era `8` in tutte e tre le copie — l'unica cosa su cui non
 * erano divergenti.
 */
const val PROFONDITA_MASSIMA_RIENTRO = 8

/** Larghezza di un livello di rientro. [scala] = 1 e' la densita' normale. */
fun passoRientro(scala: Float = 1f): Dp = (12f * scala).dp

/**
 * Il rientro di una riga alla profondita' [depth].
 *
 * Equivale alla formula che stava nei tre file: a `scala` 1 un livello vale
 * 12dp e otto livelli 96dp, come prima.
 */
fun rientroAlbero(depth: Int, scala: Float = 1f): Dp =
    passoRientro(scala) * depth.coerceAtMost(PROFONDITA_MASSIMA_RIENTRO).toFloat()

/**
 * Disegna le rotaie verticali: una linea per livello di profondita'.
 *
 * Estratta dal `drawBehind` di `ThreePaneThreadRow`, dove era l'unica copia.
 *
 * **Dalla build 2 di §A15 la chiamano TUTTE E TRE le viste** — pannelli, elenco
 * messaggi del telefono e albero della discussione. E' la risposta alla domanda
 * di Dave Royal («By design?»): le barre non erano una scelta, erano una copia
 * rimasta indietro.
 *
 * ⚠️ La geometria combacia perche' tutte e tre partono ora da `4.dp + indent`.
 * L'albero quei 4.dp non li aveva: aggiunti nella build 2, altrimenti l'ultima
 * rotaia sarebbe caduta sotto la prima lettera del titolo.
 *
 * Si chiama da dentro un `drawBehind`, che non e' `@Composable` e non puo'
 * leggere il tema: il [colore] arriva gia' risolto da chi disegna.
 *
 * ==========================================================================
 *  1.4.9 (§J1, Jeff) — L'INTERRUTTORE STA QUI, NON NEI TRE CHIAMANTI
 * ==========================================================================
 *
 * Jeff, 7 ottobre: «you chose "no vertical bars" and got vertical bars». Aveva
 * scelto lo stile citazioni «solo i >» e vedeva comunque righe verticali —
 * queste. Non era una regressione (`ArticleBody.kt` e' identico byte per byte
 * dalla 1.4.6) e non e' un difetto: sono due ornamenti diversi che si
 * somigliano. L'indizio che li distingue ce l'aveva davanti — le barre delle
 * citazioni prendono il COLORE DEL LIVELLO, le rotaie sono di un grigio unico —
 * e non lo ha notato.
 *
 * *Un indizio visibile a tutti non e' un indizio notato da qualcuno.*
 *
 * Quindi non si corregge: si rende opzionale. ACCESO di default, perche' le
 * rotaie le ha chieste Dave Royal e Jeff stesso le riconosce come quelle che
 * Thunderbird aveva prima.
 *
 * ⚠️ LA GUARDIA STA DENTRO LA FUNZIONE, e non nei tre `drawBehind` che la
 * chiamano. Tre guardie sono tre copie della stessa condizione, cioe' tre
 * occasioni di dimenticarne una — ed e' esattamente il guasto da cui §A15 e'
 * nato. Qui l'interruttore e' uno, e una vista nuova lo erediterebbe senza
 * doverselo ricordare.
 *
 * Perche' funziona leggendo lo stato da dentro un `drawBehind`: le letture di
 * stato fatte nella fase di DISEGNO sono osservate da Compose, che al cambio
 * invalida il disegno di quelle righe — non la composizione. Spegnere
 * l'interruttore ridisegna le liste senza ricomporle.
 */
fun DrawScope.disegnaRotaieAlbero(depth: Int, passo: Dp, colore: Color) {
    if (!AppSettings.rotaieAlbero) return
    val base = passo.toPx()
    val step = passo.toPx()
    for (l in 0 until depth.coerceAtMost(PROFONDITA_MASSIMA_RIENTRO)) {
        val x = base + l * step
        drawLine(colore, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1.dp.toPx())
    }
}
