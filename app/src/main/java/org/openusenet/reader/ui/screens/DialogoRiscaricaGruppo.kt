/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.4.7 (§A17) — IL DIALOGO «RISCARICA IL GRUPPO», IN UN POSTO SOLO.
 *
 * PERCHE' ESISTE QUESTO FILE. Il dialogo era scritto due volte, identico a
 * parte un dettaglio: `ThreadListScreen` riportava l'errore con `it.message`,
 * `ThreePaneHome` con `it.message ?: ""`. Due copie che *erano gia' divergenti*
 * su come si comportano quando il messaggio d'errore e' nullo — nessuno l'aveva
 * deciso, e' successo.
 *
 * La segnalazione di **Jeff** (5 ottobre) chiedeva di rendere il comando
 * raggiungibile anche dalla schermata dei gruppi, dove lui era andato a
 * cercarlo. Scriverlo li' sarebbe stata **la terza copia**: la regola del
 * quaderno dice che prima della N-esima copia si estrae, e qui N valeva tre.
 *
 * ⚠️ IL DIALOGO NON E' DECORAZIONE. Quello che conferma **cancella dati senza
 * possibilita' di annullare** — stato di lettura, contrassegni e corpi gia'
 * scaricati. E' esattamente il genere di comando che non deve comportarsi in
 * due modi diversi a seconda della schermata da cui lo si apre.
 *
 * ================================================================
 * ⚠️⚠️ IL DIFETTO DELLA BUILD 135, E PERCHE' LO SCOPE E' UN PARAMETRO
 * ================================================================
 *
 * Nella 135 questa funzione chiamava `rememberCoroutineScope()` **al proprio
 * interno**. Sembrava naturale: il lavoro parte da qui, lo scope sta qui.
 *
 * Ma questo composable vive solo finche' il dialogo e' aperto. `onChiudi()` lo
 * toglie dalla composizione, e **uscire dalla composizione cancella lo scope
 * ottenuto con `rememberCoroutineScope`** — cioe' proprio la coroutine appena
 * avviata. Risultato misurato sul telefono di Marco, gruppo
 * `it.tlc.cellulari.android`, log del 5 ottobre:
 *
 *     lista gruppo 4: ricevute 415 righe     <- prima
 *     lista gruppo 4: ricevute 0 righe       <- deleteByGroup e' passato
 *     ... sessione GROUP -> QUIT, nessun XOVER ...   <- syncGroup interrotto
 *     lista gruppo 4: ricevute 0 righe       <- gruppo svuotato e basta
 *
 * `reloadGroupFromScratch` fa tre cose in fila: cancella, azzera il progresso,
 * risincronizza. **Cancellare la coroutine a meta' lascia il gruppo svuotato**,
 * e nessun aggiornamento successivo lo ripopola.
 *
 * Prima dell'estrazione lo scope apparteneva alla SCHERMATA, che resta composta
 * mentre il dialogo se ne va: il lavoro sopravviveva per costruzione. Spostando
 * il dialogo mi sono portato dietro anche la sorgente dello scope, che invece
 * doveva restare dov'era.
 *
 * > **Quando si sposta un pezzo di interfaccia, si sposta anche il suo ciclo di
 * > vita. Un lavoro avviato da qualcosa che sta per sparire muore con lui.**
 *
 * *E' il principio 7 in forma nuova: la correzione di §A17 non ha spostato un
 * difetto esistente, ne ha creato uno — ma per la stessa ragione, cioe' aver
 * guardato il sintomo (due copie divergenti) senza guardare il contesto che le
 * teneva in piedi.*
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.openusenet.reader.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.openusenet.reader.R
import org.openusenet.reader.data.NewsRepository

/**
 * Chiede conferma e, se confermato, butta la copia locale del gruppo e la
 * riprende dal server.
 *
 * @param groupId il gruppo da riscaricare. **Nullable** perche' la vista a
 *        pannelli lo ricava da `selectedGroupId`, che puo' non esserci: in quel
 *        caso la conferma si limita a chiudere, come faceva il `return@TextButton`
 *        della copia che stava li'.
 * @param onSyncing segnala inizio e fine del lavoro, per chi mostra una rotella.
 *        Ha un valore predefinito perche' la schermata dei gruppi non ne ha una
 *        da muovere per questa operazione.
 * @param onChiudi chiude il dialogo. Viene chiamato **prima** di avviare il
 *        lavoro, non dopo: il precedente del dialogo di conservazione (beta20,
 *        Paul) e' che un dialogo ancora composto mentre parte la coroutine di
 *        cancellazione faceva cadere l'app su Android 10.
 * @param scope ⚠️ **LO SCOPE ARRIVA DALLA SCHERMATA, E NON E' UN DETTAGLIO.**
 *        Vedi il blocco qui sotto: e' il difetto della build 135.
 */
@Composable
fun DialogoRiscaricaGruppo(
    groupId: Long?,
    repo: NewsRepository,
    snackbar: SnackbarHostState,
    scope: CoroutineScope,
    onChiudi: () -> Unit,
    onSyncing: (Boolean) -> Unit = {},
) {
    val ctx = LocalContext.current
    AlertDialog(
        onDismissRequest = onChiudi,
        title = { Text(stringResource(R.string.common_redownload_group_title)) },
        text = {
            Column {
                Text(
                    stringResource(R.string.common_redownload_group_body),
                    style = MaterialTheme.typography.bodySmall,
                )
                // 1.4.7 — L'AVVISO CHE MANCAVA, e il motivo e' un numero vero.
                //
                // Marco, 5 ottobre: un gruppo con **415** messaggi in locale,
                // dopo un riscarico ne aveva **298**. Non e' un difetto:
                // `syncGroup` riprende `AppSettings.maxHeadersSync` articoli
                // (300 sul suo telefono, e infatti l'XOVER chiedeva
                // 30619-30918). I 117 piu' vecchi se ne sono andati.
                //
                // Il corpo del dialogo avverte che si perdono stato di lettura,
                // contrassegni e corpi, ma **non** che si puo' finire con MENO
                // messaggi di prima — e la riga «usalo se i messaggi sono
                // spariti per via della conservazione automatica» suggerisce
                // l'opposto, cioe' che serva a recuperare.
                //
                // > **Un comando distruttivo descritto come un rimedio e' il
                // > peggiore dei due: chi lo tocca crede di star riparando.**
                //
                // Il numero e' quello REALE dell'utente, non una costante: su
                // chi tiene la finestra a 2000 l'avviso sarebbe allarmismo, su
                // chi la tiene a 100 sarebbe un eufemismo.
                Text(
                    stringResource(R.string.common_redownload_group_window, AppSettings.maxHeadersSync),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onChiudi()
                val gid = groupId ?: return@TextButton
                scope.launch {
                    onSyncing(true)
                    runCatching { repo.reloadGroupFromScratch(gid) }
                        .onSuccess { n ->
                            snackbar.showSnackbar(ctx.getString(R.string.msg_redownloaded, n))
                        }
                        // La variante piu' prudente delle due copie: un
                        // messaggio nullo diventa stringa vuota invece di
                        // stampare "null" nella snackbar.
                        .onFailure {
                            snackbar.showSnackbar(ctx.getString(R.string.msg_error, it.message ?: ""))
                        }
                    onSyncing(false)
                }
            }) { Text(stringResource(R.string.common_redownload)) }
        },
        dismissButton = {
            TextButton(onClick = onChiudi) { Text(stringResource(R.string.common_cancel)) }
        },
    )
}
