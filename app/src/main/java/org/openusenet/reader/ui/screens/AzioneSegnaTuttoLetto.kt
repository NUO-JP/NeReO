/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * «SEGNA TUTTO LETTO», IN UN POSTO SOLO — AZIONE E ANNULLA.
 *
 * PERCHE' ESISTE QUESTO FILE (1.4.5, §A11).
 * Dave Royal, 28 settembre:
 *
 *   «I'd still like "Mark all as read" moved back to the menu bar from the
 *    three-dot menu. After that change "Mark all as read" requires two taps
 *    versus one previously. (Panel mode.)»
 *
 * E' LA STESSA PERSONA CHE AVEVA CHIESTO LA MODIFICA, e non e' una
 * contraddizione: le due segnalazioni parlano di due cose diverse. La prima
 * riguardava la POSIZIONE — un'azione rara e con conseguenze appoggiata a una
 * frequente e innocua (il filtro non-letti), quindi premuta per sbaglio. La
 * seconda riguarda il COSTO IN TOCCHI. Siamo stati noi a confonderle in una.
 *
 * IL DIFETTO VERO ERA CHE LO STESSO PROBLEMA HA AVUTO DUE CURE DIVERSE.
 * Nella 1.4.1 (build 108):
 *   - sul TELEFONO l'icona e' stata SPOSTATA, scambiata con «Aggiorna»: un
 *     tocco, e lontana dal filtro;
 *   - nei PANNELLI e' stata TOLTA, perche' li' la voce c'era gia' anche nel
 *     menu ed era un doppione: due tocchi.
 * Nessuna delle due e' sbagliata presa da sola. Ma la cura del telefono
 * risolveva l'adiacenza SENZA costare un tocco, ed e' quella che nei pannelli
 * non e' mai stata provata.
 *
 * *Due copie che divergono, prima o poi si notano. Due copie curate in modo
 * DIVERSO sembrano entrambe corrette guardando il codice, e si scoprono solo
 * quando qualcuno conta i tocchi.* Per questo l'azione ora vive qui: la
 * prossima volta che si sposta, si sposta una volta sola.
 *
 * COSA PORTA CON SE', e non e' ovvio: lo snackbar con ANNULLA. «Segna tutto
 * letto» cancella uno stato che l'utente non puo' ricostruire a mano — quali
 * messaggi non aveva ancora letto — quindi l'annulla non e' una cortesia, e'
 * la ragione per cui l'azione si puo' tenere in barra. Era ricopiato parola per
 * parola in due punti; ricopiarlo in un terzo sarebbe stato il difetto di cui
 * parla questa release.
 */
package org.openusenet.reader.ui.screens

import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.openusenet.reader.R
import org.openusenet.reader.data.NewsRepository

/**
 * Segna letti tutti i messaggi di [groupId] e offre l'annulla.
 *
 * Non e' composable apposta: la chiamano sia un `IconButton` sia una voce di
 * menu, e nessuna delle due deve ricopiare il giro dello snackbar. Le risorse
 * arrivano gia' risolte dal [ctx] del chiamante.
 */
fun segnaTuttoLetto(
    repo: NewsRepository,
    groupId: Long,
    scope: CoroutineScope,
    ctx: android.content.Context,
    snackbar: SnackbarHostState,
) {
    scope.launch {
        val eranoDaLeggere = repo.markAllRead(groupId)
        if (eranoDaLeggere.isEmpty()) return@launch
        val esito = snackbar.showSnackbar(
            message = ctx.getString(R.string.common_marked_read, eranoDaLeggere.size),
            actionLabel = ctx.getString(R.string.common_undo_upper),
            duration = SnackbarDuration.Long,
        )
        if (esito == SnackbarResult.ActionPerformed) {
            repo.setReadBulk(eranoDaLeggere, false, groupId)
        }
    }
}

/**
 * L'icona di barra «segna tutto letto».
 *
 * **Dove va messa, e non e' indifferente:** MAI accanto al filtro dei non
 * letti. E' l'unica cosa che la prima segnalazione di Dave chiedeva, e vale in
 * tutti e due i layout. Nella barra sta dopo «Aggiorna» (telefono) e dopo
 * «Nuovo thread» (pannelli), cioe' subito prima dei tre puntini.
 */
@Composable
fun AzioneSegnaTuttoLetto(
    repo: NewsRepository,
    groupId: Long?,
    scope: CoroutineScope,
    snackbar: SnackbarHostState,
    tint: Color = LocalContentColor.current,
) {
    val ctx = LocalContext.current
    IconButton(
        enabled = groupId != null,
        onClick = { groupId?.let { segnaTuttoLetto(repo, it, scope, ctx, snackbar) } },
    ) {
        Icon(
            Icons.Default.DoneAll,
            contentDescription = stringResource(R.string.common_mark_all_read),
            tint = tint,
        )
    }
}
