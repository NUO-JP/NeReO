/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.4.6 (§A14) — L'INTESTAZIONE E LA BARRA NON SI CONTENDONO PIU' LA RIGA.
 *
 * IL DIFETTO, trovato da Marco il 30 settembre 2026 sulla configurazione che
 * nessun collaudatore usa: la vista a PANNELLI su un TELEFONO in orizzontale.
 * Roxy ha un box TV largo, i tablet sono larghi — quel caso stava fuori da
 * tutte le prove.
 *
 * In una `Row` di Compose i figli SENZA peso vengono misurati per primi e si
 * prendono quello che chiedono; chi ha `weight(1f)` riceve cio' che avanza. La
 * barra di lettura non ha peso e chiede nove icone da 48dp — oltre 430dp. Su un
 * riquadro largo 260 al testo avanzava ZERO, e un `Text` largo zero non sparisce:
 * manda a capo UN CARATTERE PER RIGA. L'oggetto del messaggio diventava una
 * colonna di lettere.
 *
 * ⚠️ IL PUNTO NON E' CHE LA BARRA SIA LUNGA: e' che a pagare era sempre il
 * TESTO, cioe' l'unica cosa che l'utente deve poter leggere. Un pulsante in
 * meno si nota e si rimedia; un oggetto illeggibile fa sembrare rotta l'app.
 *
 * LA REGOLA, e vale oltre questo caso: quando due elementi si dividono una riga
 * e uno solo puo' cedere, non puo' essere quello che porta l'informazione.
 *
 * COSA FA. Sotto una certa larghezza mette la barra SOTTO l'intestazione invece
 * di affiancarla: niente viene schiacciato e niente viene tagliato. Sopra quella
 * larghezza il comportamento e' identico a prima, riga sola.
 *
 * ⚠️ §A14-bis — LA CORREZIONE AVEVA SPOSTATO IL DIFETTO, NON TOLTO.
 * Marco, 30 settembre, sulle schermate della 124: in verticale la barra del
 * riquadro mostrava TRE icone invece di quattro. Mancava il ⋮.
 *
 * Il ramo stretto dava alla barra una riga tutta sua, e li' ci si era fermati —
 * ma «una riga sua» non vuol dire «ci sta». Tre icone piu' il menu fanno 192dp
 * in un riquadro che in verticale ne misura circa 170: l'ultimo figlio disegnato
 * finiva fuori dal bordo. E l'ultimo figlio disegnato e' proprio il pulsante del
 * MENU, cioe' l'unica via per arrivare alle azioni che dalla barra sono state
 * tolte. Non si perdeva un comando: si perdeva l'accesso a tutti gli altri.
 *
 * Da qui la `FlowRow` nel ramo stretto: se le icone non entrano su una riga,
 * passano alla seconda. Niente viene mai nascosto e non c'e' nessun gesto da
 * indovinare — uno scorrimento orizzontale avrebbe rimesso in scena lo stesso
 * difetto, con in piu' un rimedio che l'utente deve immaginarsi.
 *
 * Il ramo largo NON ha bisogno di niente: li' la soglia garantisce per
 * costruzione che la barra ci stia (`maxWidth >= spazioBarra + MINIMO_LEGGIBILE`).
 *
 * ⚠️⚠️ E QUI IL PUNTO CHE CONTA DAVVERO. Questo difetto ERA GIA' STATO RISOLTO.
 * `ArticleScreen.kt:646` usa `FlowRow` dal punto #3 di Bingo3331, e il commento
 * che lo accompagna descrive il sintomo parola per parola: «la barra a icone va
 * a capo su due righe quando non c'entrano tutte, INVECE DI FAR SPARIRE L'ULTIMA
 * ICONA OLTRE IL BORDO».
 *
 * La barra dei pannelli e' nata dopo, accanto a quella soluzione, e non l'ha
 * presa. Non per una scelta: perche' erano due barre diverse in due file diversi,
 * e nessuno le ha mai messe una accanto all'altra. E' la **categoria 3 del
 * censimento §B4** — stessa funzione, due copie, una sola corretta — con la
 * differenza che stavolta abbiamo la data e il nome di chi l'aveva gia' trovata.
 *
 * Non e' un difetto di distrazione: e' un difetto di STRUTTURA. Finche' le due
 * barre restano due implementazioni, ogni correzione fatta su una e' una
 * correzione che l'altra dovra' riscoprire sul campo, con un collaudatore che la
 * subisce. Questa l'ha riscoperta Marco il 30 settembre 2026.
 *
 * LA LEZIONE, che vale piu' del caso: una correzione che risolve il sintomo DOVE
 * LO SI E' VISTO puo' limitarsi a spingerlo dove non lo si sta guardando. La
 * prima volta il difetto era sul testo perche' li' guardavamo; la seconda era
 * sulla barra, a un dito di distanza.
 *
 * PERCHE' STA IN UN FILE SUO. Lo stesso schema era in DUE punti —
 * `PostContinuo` (autore e data) e `ArticlePaneSingolo` (l'oggetto) — e il
 * principio 2 del quaderno dice di estrarre prima di scrivere la N-esima copia
 * di una logica. Correggerlo due volte avrebbe voluto dire due occasioni di
 * correggerlo in modo diverso, che e' esattamente come sono nati §A11 e §A13.2.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Larghezza di un pulsante con icona: e' il bersaglio tattile minimo di Material. */
private val LARGHEZZA_ICONA: Dp = 48.dp

/**
 * Sotto questa larghezza il testo non e' piu' leggibile e tanto vale mandare la
 * barra a capo. Non e' un numero magico: e' poco piu' di una parola lunga, cioe'
 * il minimo perche' una riga sia una riga e non una colonna di lettere.
 */
private val MINIMO_LEGGIBILE: Dp = 120.dp

/**
 * Dispone [intestazione] e [barra] su una riga sola quando ci stanno, su due
 * quando non ci stanno.
 *
 * @param azioniInBarra quante icone la barra sta per disegnare. Serve a stimare
 *   quanto spazio chiedera': la soglia si ricava dal bisogno reale invece di
 *   essere una costante scelta a occhio, cosi' resta giusta anche se l'utente
 *   sposta azioni fra barra e menu.
 * @param intestazione riceve il `Modifier` da applicare: con il peso quando c'e'
 *   la riga sola, a tutta larghezza quando si va a capo. Cosi' il chiamante
 *   tiene il SUO `Text` e non deve sapere in quale dei due casi si trova.
 */
@Composable
fun IntestazioneConBarra(
    azioniInBarra: Int,
    intestazione: @Composable (Modifier) -> Unit,
    barra: @Composable () -> Unit,
) {
    // +1 per il pulsante del menu, che c'e' sempre
    val spazioBarra = LARGHEZZA_ICONA * (azioniInBarra + 1)
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        if (maxWidth < spazioBarra + MINIMO_LEGGIBILE) {
            Column(Modifier.fillMaxWidth()) {
                intestazione(Modifier.fillMaxWidth())
                // §A14-bis: FlowRow e non Row. Vedi la nota in testa al file:
                // qui la barra e' sola sulla sua riga, ma «sola» non vuol dire
                // «ci sta». Se le icone non entrano, vanno a capo invece di
                // essere tagliate — e cio' che verrebbe tagliato e' l'ULTIMO
                // figlio disegnato, cioe' il pulsante del menu.
                FlowRow(Modifier.fillMaxWidth()) { barra() }
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                intestazione(Modifier.weight(1f))
                barra()
            }
        }
    }
}
