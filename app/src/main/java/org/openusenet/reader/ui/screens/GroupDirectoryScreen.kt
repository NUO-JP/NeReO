/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Directory dei gruppi di un server. Il download (LIST ACTIVE) è ESPLICITO:
 * su feed commerciali completi l'elenco è enorme e va scaricato per ramo
 * (wildmat, es. "it.*") per non annegare in milioni di gruppi binari.
 * Una volta in cache, la ricerca è istantanea e offline.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ArrowBack
import org.openusenet.reader.ui.icone.AddCircleOutline
import org.openusenet.reader.ui.icone.CheckCircle
import org.openusenet.reader.ui.icone.Download
import org.openusenet.reader.ui.icone.ExpandLess
import org.openusenet.reader.ui.icone.ExpandMore
import org.openusenet.reader.ui.icone.Refresh
import org.openusenet.reader.ui.icone.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.openusenet.reader.R
import org.openusenet.reader.data.NewsRepository
import org.openusenet.reader.ui.indietroSicuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDirectoryScreen(repo: NewsRepository, nav: NavHostController) {
    val servers by repo.servers.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val snackbar = remember { SnackbarHostState() }

    var serverId by remember { mutableStateOf<Long?>(null) }
    var serverMenu by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var wildmat by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<String>>(emptyList()) }
    var subscribed by remember { mutableStateOf<Set<String>>(emptySet()) }
    var total by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0) }
    var error by remember { mutableStateOf<String?>(null) }

    // primo server come default
    LaunchedEffect(servers) {
        if (serverId == null && servers.isNotEmpty()) serverId = servers.first().id
    }

    // cambio server: carico sottoscrizioni e conteggio cache (NIENTE auto-download)
    LaunchedEffect(serverId) {
        val id = serverId ?: return@LaunchedEffect
        error = null
        subscribed = repo.subscribedNames(id)
        total = repo.availableGroupCount(id)
    }

    // ricerca live
    LaunchedEffect(query, total, serverId) {
        val id = serverId ?: return@LaunchedEffect
        if (total > 0) results = repo.searchAvailableGroups(id, query)
    }

    fun download() {
        val id = serverId ?: return
        scope.launch {
            loading = true
            error = null
            progress = 0
            runCatching { repo.refreshAvailableGroups(id, wildmat.takeIf { it.isNotBlank() }) { n -> progress = n } }
                .onSuccess { n ->
                    total = n
                    results = repo.searchAvailableGroups(id, query)
                    snackbar.showSnackbar(ctx.getString(R.string.gd_updated, n))
                }
                .onFailure { error = it.message ?: it.javaClass.simpleName }
            loading = false
        }
    }

    val currentServer = servers.firstOrNull { it.id == serverId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gd_title)) },
                navigationIcon = {
                    IconButton(onClick = { nav.indietroSicuro() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
                actions = {
                    IconButton(enabled = !loading && serverId != null, onClick = { download() }) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.gd_reload_cd))
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbar) },
    ) { padding ->
        Column(Modifier.padding(padding)) {

            // selettore server
            // 1.4.4 (Dave Royal): il Box E' L'ANCORA. Senza, DropdownMenu si
            // aggancia al nodo GENITORE (Popup usa parentLayoutCoordinates),
            // qui la Column intera. Oggi il pulsante e' il suo primo figlio e
            // lo scarto non si vede, ma basta aggiungere qualcosa sopra perche'
            // il menu si stacchi dal pulsante.
            Box {
                TextButton(onClick = { serverMenu = true }) {
                    Text(stringResource(R.string.gd_server, currentServer?.name ?: "—"))
                }
                DropdownMenu(expanded = serverMenu, onDismissRequest = { serverMenu = false }) {
                    servers.forEach { s ->
                        DropdownMenuItem(
                            text = { Text(s.name) },
                            onClick = { serverId = s.id; serverMenu = false },
                        )
                    }
                }
            }

            // beta19 (spunto di Big Umberto): il download dell'elenco gruppi ora
            // prende TUTTO per default. Il filtro per gerarchia (wildmat) era
            // pensato per quando banda e spazio erano scarsi: oggi 30.000 gruppi
            // sono meno di 1 MB di testo. Restava però un problema pratico: ogni
            // download SOSTITUISCE la cache, quindi chi aveva scaricato "it.*" e
            // voleva aggiungere "mozilla.*" doveva riscrivere entrambe — il bug
            // che Big Umberto segnalava come "non vedo le gerarchie abilitate".
            // Scaricando tutto, il problema sparisce: si filtra nella RICERCA.
            // Il wildmat resta sotto "Opzioni avanzate" per i casi veri in cui
            // serve (server enormi con binaries, connessioni a consumo).
            FilledTonalButton(
                enabled = !loading && serverId != null,
                onClick = { download() },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text(
                    if (total > 0) stringResource(R.string.gd_reload_full)
                    else stringResource(R.string.gd_download)
                )
            }

            var opzioniAvanzate by remember { mutableStateOf(false) }
            TextButton(
                onClick = { opzioniAvanzate = !opzioniAvanzate },
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Icon(
                    if (opzioniAvanzate) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                )
                Text(stringResource(R.string.gd_advanced))
            }
            if (opzioniAvanzate) {
                OutlinedTextField(
                    value = wildmat,
                    onValueChange = { wildmat = it },
                    label = { Text(stringResource(R.string.gd_wildmat)) },
                    singleLine = true,
                    // wishlist beta12 (SiMcarD): senza questo, alcune tastiere
                    // inserivano uno spazio automatico dopo ogni punto, pensando
                    // fosse fine frase — qui il punto è sintassi, non punteggiatura
                    keyboardOptions = KeyboardOptions(autoCorrectEnabled = false),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                Text(
                    stringResource(R.string.gd_wildmat_note),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }

            // ricerca nella cache
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text(stringResource(R.string.gd_search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(autoCorrectEnabled = false),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            error?.let { msg ->
                Text(
                    stringResource(R.string.gd_error, msg),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }

            if (loading) {
                LinearProgressIndicator(Modifier.fillMaxWidth().padding(top = 8.dp))
                Text(
                    stringResource(R.string.gd_downloading) +
                        (if (progress > 0) stringResource(R.string.gd_received, progress) else ""),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp),
                )
            } else if (error == null) {
                Text(
                    stringResource(R.string.gd_cache_info, total, results.size) +
                        (if (results.size == 500) stringResource(R.string.gd_first_500) else ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }

            LazyColumn {
                items(results, key = { it }) { name ->
                    val isSub = name in subscribed
                    ListItem(
                        headlineContent = { Text(name, style = MaterialTheme.typography.bodyMedium) },
                        trailingContent = {
                            IconButton(onClick = {
                                val id = serverId ?: return@IconButton
                                scope.launch {
                                    if (isSub) {
                                        repo.unsubscribeByName(id, name)
                                        subscribed = subscribed - name
                                    } else {
                                        repo.subscribe(id, name)
                                        subscribed = subscribed + name
                                    }
                                }
                            }) {
                                if (isSub) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = stringResource(R.string.gd_subscribed_cd),
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                } else {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = stringResource(R.string.gd_subscribe_cd))
                                }
                            }
                        },
                    )
                }
                if (!loading && total > 0 && results.isEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.gd_no_match, query),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(24.dp),
                        )
                    }
                }
            }
        }
    }
}

