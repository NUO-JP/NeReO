/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.3 (B — barre personalizzabili, PILOTA sulla barra bassa di ArticleScreen).
 * Editor: mostra/nascondi ogni azione (switch) e riordina con frecce su/giù.
 *
 * Perché frecce e non drag: un drag orizzontale sulla barra viva è fragile al
 * tocco e inutilizzabile col telecomando/mini-pad dei TV box (Roxy). Le frecce
 * su/giù in una lista verticale sono lo stesso pattern già validato per il
 * riordino gruppi in tablet mode (1.2.6): funziona identico con dito, penna o
 * D-pad. Vedi NeReO_feature_barre.md per la discussione completa (drag verticale
 * "più affidabile" restava comunque un'opzione futura, non scelta qui).
 *
 * Le azioni gestite sono quelle di AppSettings.articleBarActionIds. Icona ed
 * etichetta per id sono centralizzate qui (uniche fonte, per non disallinearsi
 * da ArticleScreen se in futuro cambia l'insieme delle azioni note).
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ArrowBack
// 1.3.6. SE NON COMPILA (Link/AlternateEmail): ripiegare su ContentCopy.
import org.openusenet.reader.ui.icone.KeyboardArrowDown
import org.openusenet.reader.ui.icone.KeyboardArrowUp
import org.openusenet.reader.ui.icone.RestartAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.openusenet.reader.AppSettings
import org.openusenet.reader.R
import org.openusenet.reader.ui.indietroSicuro

// 1.4.6 (§B4): le due tabelle che stavano qui sono diventate UNA, in
// AzioniBarra.kt, insieme all'id. Restano questi due accessi perche' la
// schermata disegna icone ed etichette, non perche' la mappa viva ancora
// qui: i quattro posti in cui registrare un'azione sono diventati due.
// I ripieghi sono gli stessi di prima, identici: un id ignoto non deve far
// cadere la schermata, deve farsi notare.
private fun barCatalogIcon(id: String): ImageVector =
    azioneBarra(id)?.icona ?: ICONA_RIPIEGO

@Composable
private fun barCatalogLabel(id: String): String =
    azioneBarra(id)?.let { stringResource(it.etichetta) } ?: id

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeBarsScreen(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.bars_editor_title)) },
                navigationIcon = {
                    IconButton(onClick = { nav.indietroSicuro() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
                actions = {
                    IconButton(onClick = { AppSettings.resetArticleBar(); AppSettings.resetPaneBar() }) {
                        Icon(Icons.Default.RestartAlt, contentDescription = stringResource(R.string.bars_editor_reset))
                    }
                },
            )
        },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Text(
                stringResource(R.string.bars_editor_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )
            val ordine = AppSettings.articleBarOrder
            val ordinePannelli = AppSettings.paneBarOrder
            LazyColumn(Modifier.fillMaxWidth()) {
                item {
                    Text(
                        stringResource(R.string.bars_editor_phone_section),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 8.dp),
                    )
                }
                items(ordine, key = { it }) { id ->
                    val idx = ordine.indexOf(id)
                    val nascosto = id in AppSettings.articleBarHidden
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                    ) {
                        Icon(
                            barCatalogIcon(id),
                            contentDescription = null,
                            tint = if (nascosto) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                   else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 12.dp),
                        )
                        Text(
                            barCatalogLabel(id),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (nascosto) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                    else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                        )
                        IconButton(
                            enabled = idx > 0,
                            onClick = { AppSettings.moveArticleBarAction(id, -1) },
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = stringResource(R.string.bars_editor_move_up_cd))
                        }
                        IconButton(
                            enabled = idx < ordine.size - 1,
                            onClick = { AppSettings.moveArticleBarAction(id, 1) },
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.bars_editor_move_down_cd))
                        }
                        Switch(
                            checked = !nascosto,
                            onCheckedChange = { AppSettings.toggleArticleBarActionHidden(id) },
                        )
                    }
                }

                // ----------------------------------------------------------
                // 1.3.1 (Dave Royal + Roxy): barra del riquadro di lettura nei
                // pannelli. Sezione separata e non un'estensione della prima:
                // le azioni sono altre, e soprattutto la semantica dello switch
                // e' DIVERSA — qui spento non vuol dire "sparita" ma "spostata
                // nel menu tre puntini", che c'e' sempre. Il testo della nota
                // lo dice, perche' uno switch che sembra uguale ma fa una cosa
                // diversa e' peggio di due switch diversi.
                // ----------------------------------------------------------
                item {
                    HorizontalDivider(Modifier.padding(vertical = 12.dp))
                    Text(
                        stringResource(R.string.bars_editor_pane_section),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
                    )
                    Text(
                        stringResource(R.string.bars_editor_pane_note),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                    )
                }
                items(ordinePannelli, key = { "pane_" + it }) { id ->
                    val idx = ordinePannelli.indexOf(id)
                    val nelMenu = id in AppSettings.paneBarInMenu
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                    ) {
                        Icon(
                            barCatalogIcon(id),
                            contentDescription = null,
                            tint = if (nelMenu) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                   else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 12.dp),
                        )
                        Column(Modifier.weight(1f)) {
                            Text(
                                barCatalogLabel(id),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (nelMenu) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                        else MaterialTheme.colorScheme.onSurface,
                            )
                            if (nelMenu) {
                                Text(
                                    stringResource(R.string.bars_editor_pane_in_menu),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        IconButton(
                            enabled = idx > 0,
                            onClick = { AppSettings.movePaneBarAction(id, -1) },
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = stringResource(R.string.bars_editor_move_up_cd))
                        }
                        IconButton(
                            enabled = idx < ordinePannelli.size - 1,
                            onClick = { AppSettings.movePaneBarAction(id, 1) },
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.bars_editor_move_down_cd))
                        }
                        Switch(
                            checked = !nelMenu,
                            onCheckedChange = { AppSettings.togglePaneBarInMenu(id) },
                        )
                    }
                }
            }
        }
    }
}
