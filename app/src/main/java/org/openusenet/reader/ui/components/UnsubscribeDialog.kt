/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Dialogo di conferma per annullare la sottoscrizione a un gruppo.
 *
 * Sta in un file suo perche' serve identico in due punti (elenco gruppi del
 * telefono e colonna sinistra del layout a pannelli): duplicarlo avrebbe
 * portato prima o poi a due testi diversi per la stessa azione.
 */
package org.openusenet.reader.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.openusenet.reader.R

/**
 * @param nomeGruppo nome del gruppo, mostrato all'utente
 * @param messaggiInLocale quanti messaggi risultano scaricati (0 = non noto)
 * @param onConferma riceve true se l'utente vuole eliminare anche i messaggi
 */
@Composable
fun UnsubscribeDialog(
    nomeGruppo: String,
    messaggiInLocale: Int,
    onDismiss: () -> Unit,
    onConferma: (eliminaMessaggi: Boolean) -> Unit,
) {
    // Preselezionato: chi si disiscrive di solito vuole liberare lo spazio.
    // Resta comunque una scelta esplicita, non un effetto collaterale.
    var elimina by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.unsubscribe_title)) },
        text = {
            Column {
                Text(nomeGruppo, style = MaterialTheme.typography.titleSmall)
                Text(
                    stringResource(R.string.unsub_body),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 12.dp),
                ) {
                    Checkbox(checked = elimina, onCheckedChange = { elimina = it })
                    Column(Modifier.padding(start = 4.dp)) {
                        Text(
                            if (messaggiInLocale > 0)
                                stringResource(R.string.unsub_del_n, messaggiInLocale)
                            else
                                stringResource(R.string.unsub_del),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            if (elimina)
                                stringResource(R.string.unsub_delete_note)
                            else
                                stringResource(R.string.unsub_keep_note),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConferma(elimina) }) { Text(stringResource(R.string.unsubscribe_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.common_cancel)) }
        },
    )
}
