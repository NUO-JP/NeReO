/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * L'ICONA "AGGIORNA" DELLA BARRA, IN UN POSTO SOLO.
 *
 * PERCHE' ESISTE QUESTO FILE (1.4.4, §A7).
 * Dave Royal, 24 settembre, dopo un equivoco che vale la pena ricordare —
 * Marco aveva capito che volesse togliere l'icona:
 *
 *   «I'm not suggesting getting rid of the refresh icon. I'm suggesting that,
 *    if you have only one server, it does not ask you which server(s) you wish
 *    to refresh.»
 *
 * Aveva ragione. Il menu si apriva SEMPRE, senza mai guardare quanti server ci
 * fossero: con un server solo offriva due voci che fanno la stessa cosa —
 * "Aggiorna tutto" e "Scegli quali server aggiornare", dove scegliere significa
 * spuntare l'unico che si possiede. Non e' una scelta, e' una formalita'.
 *
 * E vivevano in DUE COPIE: `GroupsScreen` (pagine) e `ThreePaneHome`
 * (pannelli), identiche salvo il nome della variabile di stato e la lambda di
 * sincronizzazione. Correggerne una sola sarebbe stato il settimo caso della
 * serie "presente in una copia su due" in cinque giorni. Marco ha scelto il
 * piano B: la condizione **piu'** l'estrazione, cosi' la prossima modifica a
 * questo menu non puo' atterrare su un lato solo.
 *
 * E' anche un altro mattone di §B4 (la lista unica di azioni per le due viste)
 * che non andra' rifatto, dopo i tre dialoghi del ritiro e il pulsante Incolla.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.Dns
import org.openusenet.reader.ui.icone.Sync
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.openusenet.reader.R

/**
 * L'icona di aggiornamento della barra, con il suo menu.
 *
 * @param numeroServer quanti server sono configurati. **Con uno solo il menu
 *        non si apre affatto**: l'icona sincronizza e basta, che e' l'unica
 *        cosa che quel menu avrebbe potuto fare.
 * @param onAggiornaTutto sincronizza tutti i gruppi sottoscritti.
 * @param onScegliServer apre il dialogo "scegli quali server".
 */
@Composable
fun AzioneAggiorna(
    abilitata: Boolean,
    numeroServer: Int,
    onAggiornaTutto: () -> Unit,
    onScegliServer: () -> Unit,
    tint: Color = LocalContentColor.current,
) {
    var menuAperto by remember { mutableStateOf(false) }

    // 1.4.4 (Dave Royal): il Box E' L'ANCORA. Senza, DropdownMenu si
    // aggancia al nodo GENITORE (Popup usa parentLayoutCoordinates),
    // e in una Row a tutta larghezza il menu parte dal bordo dello
    // schermo invece che da sotto l'icona.
    Box {
        IconButton(
            enabled = abilitata,
            onClick = {
                // Con un server solo la scelta non esiste: si salta il menu invece
                // di farlo aprire su un'alternativa fittizia.
                if (numeroServer > 1) menuAperto = true else onAggiornaTutto()
            },
        ) {
            Icon(
                Icons.Default.Sync,
                contentDescription = stringResource(R.string.groups_refresh_all),
                tint = tint,
            )
        }
        DropdownMenu(expanded = menuAperto, onDismissRequest = { menuAperto = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.sel_refresh_all)) },
                leadingIcon = { Icon(Icons.Default.Sync, contentDescription = null) },
                onClick = {
                    menuAperto = false
                    onAggiornaTutto()
                },
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.sel_refresh_choose_servers)) },
                leadingIcon = { Icon(Icons.Default.Dns, contentDescription = null) },
                onClick = {
                    menuAperto = false
                    onScegliServer()
                },
            )
        }
    }
}
