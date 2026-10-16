/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Elenco delle bozze: messaggi scritti e non ancora spediti.
 *
 * Una bozza nasce in due modi: perché l'hai salvata tu con l'apposito
 * pulsante, oppure automaticamente un attimo prima di un invio — in questo
 * secondo caso viene cancellata appena l'invio riesce, quindi la trovi qui
 * solo se qualcosa è andato storto. È la rete di sicurezza contro il server
 * irraggiungibile o la rete che cade a metà.
 *
 * 1.2.9 (richiesta di Marco, Piano C): ogni bozza porta già con sé il
 * gruppo di destinazione (Draft.groupId), quindi lo schermo può aprirsi in
 * due modi — da qui il parametro [groupId]:
 *   - groupId == null: aperta da un punto SENZA un gruppo di riferimento
 *     (oggi non più raggiungibile dal menu principale, ma la route Routes.DRAFTS
 *     resta per compatibilità/futuro); mostra sempre TUTTE le bozze.
 *   - groupId != null: aperta dal menu ⋮ di un gruppo (ThreadListScreen o
 *     ThreePaneHome) — parte filtrata su QUEL gruppo, con un interruttore
 *     per allargare a tutti i gruppi senza dover uscire dallo schermo.
 * Il filtro è fatto in memoria (le bozze sono in numero ridotto): niente
 * nuova query Room, il campo per filtrare c'era già.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ArrowBack
import org.openusenet.reader.ui.icone.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.openusenet.reader.R
import org.openusenet.reader.data.NewsRepository
import org.openusenet.reader.ui.Routes
import org.openusenet.reader.ui.indietroSicuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftsScreen(repo: NewsRepository, nav: NavHostController, groupId: Long? = null) {
    val tutteLeBozze by repo.drafts.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val noSubject = stringResource(R.string.drafts_no_subject)

    // Di default, aperta da un gruppo parte filtrata su quello; aperta senza
    // gruppo (groupId == null) non ha nulla su cui filtrare, quindi è come
    // avere l'interruttore già su "tutti i gruppi".
    var tuttiIGruppi by remember(groupId) { mutableStateOf(groupId == null) }
    var nomeGruppo by remember(groupId) { mutableStateOf("") }
    LaunchedEffect(groupId) {
        nomeGruppo = groupId?.let { repo.groupNameById(it) } ?: ""
    }
    val bozze = if (groupId != null && !tuttiIGruppi) {
        tutteLeBozze.filter { it.groupId == groupId }
    } else {
        tutteLeBozze
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (groupId != null && !tuttiIGruppi && nomeGruppo.isNotEmpty())
                            stringResource(R.string.drafts_title_group, nomeGruppo)
                        else
                            stringResource(R.string.drafts_title),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.indietroSicuro() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
            )
        },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
        // Il filtro ha senso solo quando c'è un gruppo di riferimento: aperta
        // dal menu generale (groupId == null) le bozze sono già tutte quelle
        // che esistono, l'interruttore non aggiungerebbe nulla.
        if (groupId != null) {
            Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                FilterChip(
                    selected = !tuttiIGruppi,
                    onClick = { tuttiIGruppi = false },
                    label = { Text(stringResource(R.string.drafts_filter_this_group)) },
                    modifier = Modifier.padding(end = 8.dp),
                )
                FilterChip(
                    selected = tuttiIGruppi,
                    onClick = { tuttiIGruppi = true },
                    label = { Text(stringResource(R.string.drafts_filter_all_groups)) },
                )
            }
        }
        if (bozze.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    stringResource(R.string.drafts_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            LazyColumn {
                items(bozze, key = { it.id }) { bozza ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            Modifier
                                .weight(1f)
                                .clickable { nav.navigate(Routes.composeDraft(bozza.id)) }
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                        ) {
                            Text(
                                bozza.subject.ifBlank { noSubject },
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                            )
                            Text(
                                bozza.body.lineSequence().firstOrNull { it.isNotBlank() }?.take(80).orEmpty(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                            )
                        }
                        IconButton(onClick = { scope.launch { repo.deleteDraft(bozza.id) } }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.drafts_delete),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
        }
    }
}
