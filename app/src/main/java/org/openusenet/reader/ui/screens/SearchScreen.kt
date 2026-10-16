/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Ricerca testuale in tutta la cache locale: cerca in oggetto e autore di
 * qualunque messaggio scaricato e, per i messaggi già aperti almeno una volta,
 * anche nel corpo. Toccando un risultato si apre il messaggio.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ArrowBack
import org.openusenet.reader.ui.icone.Close
import org.openusenet.reader.ui.icone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.flowOf
import org.openusenet.reader.R
import org.openusenet.reader.data.Article
import org.openusenet.reader.data.NewsRepository
import org.openusenet.reader.ui.Routes
import org.openusenet.reader.ui.indietroSicuro

/** Wishlist beta12 (Bingo3331): un gruppo di risultati quando si cerca "per thread". */
private data class RisultatoThread(
    /** 1.2.12: chiave univoca della riga (message-id di raggruppamento). */
    val chiaveGruppo: String,
    val displayArticleId: Long,
    val displaySubject: String,
    val matchCount: Int,
    val lastDateEpoch: Long,
    val radiceRisolta: Boolean,
)

/**
 * Raggruppa [risultati] per thread: chiave = prima Message-ID nella catena
 * References dell'articolo, o l'articolo stesso se non ha References (cioè
 * è lui l'origine). Approssimazione volutamente semplice — non il vero
 * threader JWZ dell'app (che richiederebbe scaricare l'intero albero di ogni
 * gruppo coinvolto solo per una ricerca) — ma sufficiente per raggruppare
 * risultati imparentati invece di mostrarli sparsi per data.
 */
private suspend fun raggruppaPerThread(repo: NewsRepository, risultati: List<Article>): List<RisultatoThread> {
    val primaReference = Regex("<[^>]+>")
    val gruppi = risultati.groupBy { a ->
        primaReference.find(a.references)?.value ?: a.messageId
    }
    return gruppi.map { (chiave, articoli) ->
        val radice = repo.articleByMessageId(chiave)
        val vecchioMatch = articoli.minByOrNull { it.dateEpoch }!!
        RisultatoThread(
            // 1.2.12 (audit di sicurezza): `chiave` (il message-id di
            // raggruppamento) è ciò che rende univoca la riga, NON l'id
            // dell'articolo mostrato. Due gruppi diversi possono infatti
            // risolvere allo stesso articolo da mostrare — succede se un
            // messaggio compare sia come radice risolta di un gruppo sia come
            // primo match di un altro — e una LazyColumn con due chiavi
            // uguali lancia un'eccezione: la ricerca "per discussione"
            // crashava su quel termine, e bastava pubblicare l'articolo
            // giusto per provocarlo.
            chiaveGruppo = chiave,
            displayArticleId = radice?.id ?: vecchioMatch.id,
            displaySubject = (radice ?: vecchioMatch).subject,
            matchCount = articoli.size,
            lastDateEpoch = articoli.maxOf { it.dateEpoch },
            radiceRisolta = radice != null,
        )
    }.sortedByDescending { it.lastDateEpoch }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    repo: NewsRepository,
    nav: NavHostController,
    groupId: Long? = null,
    groupName: String = "",
) {
    // Correzione 1.2.9 (segnalazione di Marco: "la lista di risultati si
    // resetta ogni volta che selezioni un risultato"). Causa: `remember`
    // semplice perde lo stato quando SearchScreen esce di composizione — cosa
    // che la navigazione verso un articolo fa regolarmente, essendo una nuova
    // destinazione sopra questa nello stack. Tornando indietro, la schermata
    // ripartiva da `query = ""`, quindi da risultati vuoti: da qui il
    // workaround di Marco (rifare la ricerca ogni volta per il risultato
    // successivo). `rememberSaveable` sopravvive a quell'uscita/rientro: la
    // query torna quella di prima, e la lista dei risultati (derivata dalla
    // query via Flow, poco sotto) si ripopola da sola, senza dover salvare
    // la lista stessa.
    var query by rememberSaveable { mutableStateOf("") }
    // wishlist beta12 (Bingo3331): ricerca "per messaggi" (esistente) o "per
    // thread" (nuova) — raggruppa i risultati imparentati invece di mostrarli
    // sparsi in ordine di data. Stesso trattamento di `query`: senza,
    // tornando da un risultato si perderebbe anche la scelta della scheda.
    var perThread by rememberSaveable { mutableStateOf(false) }
    // la ricerca parte da 2 caratteri; sotto soglia nessun risultato.
    // Con groupId presente resta confinata a quel gruppo.
    val results by remember(query, groupId) {
        val q = query.trim()
        when {
            q.length < 2 -> flowOf(emptyList())
            groupId != null -> repo.searchArticlesInGroup(groupId, q)
            else -> repo.searchArticles(q)
        }
    }.collectAsState(initial = emptyList())

    var threadResults by remember { mutableStateOf<List<RisultatoThread>>(emptyList()) }
    LaunchedEffect(results, perThread) {
        threadResults = if (perThread) raggruppaPerThread(repo, results) else emptyList()
    }

    val focus = remember { FocusRequester() }
    // CRASH corretto (beta12, segnalato da Marco e Allen: "spesso crasha,
    // solo raramente funziona" usando la ricerca — ed era l'unica chiamata a
    // requestFocus() in tutto il progetto, cercata apposta per conferma).
    // requestFocus() lancia IllegalStateException se il campo a cui è
    // collegato non è ancora attaccato alla composizione in quel preciso
    // istante — durante la transizione di navigazione verso questa
    // schermata è una corsa contro il tempo, non davvero casuale: a volte
    // il campo è già pronto (funziona), più spesso no (crash). Difendersi
    // con try/catch invece di sperare nel tempismo giusto.
    LaunchedEffect(Unit) {
        // qualche tentativo con piccola attesa: la prima volta il campo
        // spesso non è ancora attaccato, ma di solito lo è entro i primi
        // due-tre frame — meglio riprovare che rinunciare al focus
        // automatico al primo colpo perso
        repeat(5) {
            val riuscito = runCatching { focus.requestFocus() }.isSuccess
            if (riuscito) return@LaunchedEffect
            kotlinx.coroutines.delay(40)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (groupId != null) stringResource(R.string.srch_in_group, groupName) else stringResource(R.string.srch_title)) },
                navigationIcon = {
                    IconButton(onClick = { nav.indietroSicuro() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
            )
        },
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text(stringResource(R.string.srch_field)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.srch_clear_cd))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focus),
            )
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = !perThread,
                    onClick = { perThread = false },
                    label = { Text(stringResource(R.string.srch_tab_messages)) },
                )
                FilterChip(
                    selected = perThread,
                    onClick = { perThread = true },
                    label = { Text(stringResource(R.string.srch_tab_threads)) },
                )
            }
            Text(
                when {
                    query.trim().length < 2 -> if (groupId != null)
                        stringResource(R.string.srch_note_group)
                    else
                        stringResource(R.string.srch_note_all)
                    perThread && threadResults.isEmpty() -> stringResource(R.string.srch_none, query.trim())
                    perThread -> stringResource(R.string.srch_threads_n, threadResults.size)
                    results.isEmpty() -> stringResource(R.string.srch_none, query.trim())
                    results.size == 500 -> stringResource(R.string.srch_first_500)
                    else -> stringResource(R.string.srch_results_n, results.size)
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            if (perThread) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(threadResults, key = { it.chiaveGruppo }) { t ->
                        ListItem(
                            headlineContent = { Text(t.displaySubject, maxLines = 2) },
                            supportingContent = {
                                Text(
                                    stringResource(R.string.srch_matches, t.matchCount, formatThreadDate(t.lastDateEpoch)) +
                                        if (!t.radiceRisolta) stringResource(R.string.srch_root_missing) else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1,
                                )
                            },
                            modifier = Modifier.clickable { nav.navigate(Routes.article(t.displayArticleId)) },
                        )
                    }
                }
            } else {
            LazyColumn(Modifier.fillMaxSize()) {
                items(results, key = { it.id }) { a ->
                    val hlBg = Color(0x66FFEB3B) // giallo trasparente
                    ListItem(
                        headlineContent = {
                            Text(
                                highlightMatches(
                                    (if (a.replyToMe) "↩ " else "") + a.subject,
                                    query, hlBg,
                                ),
                                fontWeight = if (a.read) FontWeight.Normal else FontWeight.Bold,
                                maxLines = 2,
                            )
                        },
                        supportingContent = {
                            Text(
                                highlightMatches(
                                    "${a.fromField}  ·  ${formatThreadDate(a.dateEpoch)}",
                                    query, hlBg,
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                            )
                        },
                        modifier = Modifier.clickable {
                            // livello 2: porto il termine cercato fino all'articolo
                            nav.navigate(Routes.article(a.id) + "?q=" + java.net.URLEncoder.encode(query.trim(), "UTF-8"))
                        },
                    )
                }
            }
            }
        }
    }
}
