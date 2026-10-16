/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * IL RITIRO DAL SERVER (cancel), IN UN POSTO SOLO.
 *
 * PERCHE' ESISTE QUESTO FILE (1.4.4, 24 settembre 2026).
 * Roxy, che usa un box TV e quindi il layout a pannelli in orizzontale:
 *
 *   «Negli screenshot si vedeva come nella versione "normale" e' presente sia
 *    il "cancel message" del menu contestuale, ed e' presente anche l'icona
 *    "Incolla". Ma nella versione a pannelli landscape continuano a non essere
 *    presenti.»
 *
 * Aveva ragione, e la formulazione esatta e' piu' semplice di "non funziona":
 * nei pannelli quelle due cose NON C'ERANO. NeReO ha due menu contestuali di
 * riga — quello di ThreadListScreen (pagine) e quello di ThreePaneHome
 * (pannelli) — e "Ritira" viveva solo nel primo insieme ai suoi tre dialoghi.
 *
 * Nella 1.4.2 (build 110) era entrato "Sostituisci" e il ritiro no, con una
 * ragione scritta nel codice: Sostituisci e' una navigazione, una riga; il
 * ritiro porta con se' tre dialoghi, e ricopiarli sarebbe stata la seconda
 * copia, cioe' il difetto stesso. Questo file e' quella ragione onorata: i tre
 * dialoghi sono ESTRATTI, non ricopiati, e le due viste ne chiamano uno solo.
 *
 * COSA RESTA AL CHIAMANTE: la sola riga di stato `bersaglio` — quale articolo
 * si sta per ritirare — perche' e' il menu contestuale a deciderla. L'attesa e
 * l'esito per server vivono qui dentro: sono meccanica del ritiro, non della
 * schermata, e tenerle fuori vorrebbe dire due copie di quelle.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.openusenet.reader.R
import org.openusenet.reader.data.Article
import org.openusenet.reader.data.EsitoRitiro
import org.openusenet.reader.data.EsitoRitiroServer
import org.openusenet.reader.data.NewsRepository

/**
 * I tre dialoghi del ritiro: conferma, attesa, esito per server.
 *
 * Si chiama SEMPRE, senza avvolgerla in un `if`: dopo la conferma il bersaglio
 * viene azzerato dal chiamante, ma l'attesa e l'esito devono continuare a
 * vivere — se la chiamata sparisse dalla composizione con il bersaglio, la
 * coroutine perderebbe il suo scope a meta' strada e l'utente resterebbe senza
 * risposta proprio mentre il messaggio di controllo viaggia sulla rete.
 *
 * @param bersaglio l'articolo per cui si chiede conferma; null = nessun dialogo
 *                  di conferma aperto.
 * @param onFine    azzera [bersaglio] nel chiamante (conferma o annulla).
 */
@Composable
fun DialoghiRitiro(
    repo: NewsRepository,
    bersaglio: Article?,
    onFine: () -> Unit,
    snackbar: SnackbarHostState,
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    // 1.2.16 (Piano B): esito del ritiro server per server, e attesa dichiarata
    // mentre li interroghiamo (una connessione ciascuno).
    var esiti by remember { mutableStateOf<List<EsitoRitiroServer>?>(null) }
    var inCorso by remember { mutableStateOf(false) }

    // 1.2.11: conferma prima di inviare un messaggio di controllo "cancel".
    // Il testo dice esplicitamente che il server può ignorarlo e che la copia
    // locale resta: meglio un'aspettativa corretta che una sorpresa dopo.
    bersaglio?.let { target ->
        AlertDialog(
            onDismissRequest = onFine,
            title = { Text(stringResource(R.string.tl_cancel_title)) },
            text = {
                Text(
                    stringResource(R.string.tl_cancel_body),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onFine()
                    scope.launch {
                        // 1.2.15 (voce C): il messaggio non dice più soltanto
                        // "richiesta inviata". Dopo il POST il Repository
                        // interroga il server con STAT e riferisce cosa ha
                        // trovato davvero — su Astraweb, che ignora i messaggi
                        // di controllo, l'utente lo scopre subito invece di
                        // credere di aver ritirato un articolo ancora leggibile.
                        //
                        // 1.2.16 (Piano B): l'esito non è più uno solo ma uno
                        // PER SERVER, quindi non ci sta in una barretta di
                        // avviso: serve un dialogo. Nel frattempo l'attesa va
                        // dichiarata — una connessione per server richiede
                        // qualche secondo, e un pulsante che non fa niente per
                        // cinque secondi sembra rotto.
                        inCorso = true
                        runCatching { repo.cancelArticle(target.id) }
                            .onSuccess { esitiRitiro -> esiti = esitiRitiro }
                            .onFailure { snackbar.showSnackbar(ctx.getString(R.string.msg_error, it.message)) }
                        inCorso = false
                    }
                }) { Text(stringResource(R.string.tl_cancel_confirm)) }
            },
            dismissButton = { TextButton(onClick = onFine) { Text(stringResource(R.string.common_cancel)) } },
        )
    }

    // 1.2.16 (Piano B): attesa mentre si interrogano i server.
    if (inCorso) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.tl_cancel_result_title)) },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(stringResource(R.string.tl_cancel_checking))
                }
            },
            confirmButton = { },
        )
    }

    // 1.2.16 (Piano B): esito server per server. Il server su cui il messaggio
    // di controllo è stato davvero inviato è marcato, perché è l'unico dove il
    // ritiro poteva agire: senza quella distinzione, tre righe "ancora
    // presente" sembrerebbero tre fallimenti invece di una cosa sola andata
    // come doveva e due server semplicemente interrogati.
    esiti?.let { elenco ->
        AlertDialog(
            onDismissRequest = { esiti = null },
            title = { Text(stringResource(R.string.tl_cancel_result_title)) },
            text = {
                Column {
                    elenco.forEach { e ->
                        val stato = stringResource(
                            when (e.esito) {
                                EsitoRitiro.RITIRATO -> R.string.tl_cancel_state_removed
                                EsitoRitiro.ANCORA_PRESENTE -> R.string.tl_cancel_state_present
                                EsitoRitiro.NON_VERIFICABILE -> R.string.tl_cancel_state_unknown
                            },
                        )
                        Text(
                            stringResource(
                                if (e.iniezione) R.string.tl_cancel_line_sent else R.string.tl_cancel_line_other,
                                e.server,
                                stato,
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                    Text(
                        stringResource(R.string.tl_cancel_result_note),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { esiti = null }) { Text(stringResource(R.string.common_ok)) }
            },
        )
    }
}
