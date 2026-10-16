/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Appunti di sistema: UN SOLO punto in tutta l'app.
 *
 * PERCHE' ESISTE QUESTO FILE (1.3.5).
 * Fino alla 1.3.4 le stesse due righe erano ripetute in CINQUE schermate —
 * AboutScreen, MaintenanceScreen, ThreePaneHome, ArticleScreen, ComposeScreen —
 * per un totale di sei punti di chiamata, cresciuti uno alla volta fra la 1.2.8
 * e la 1.3.3 senza che nessuno si fermasse a contarli.
 *
 * E' esattamente la forma del difetto che la 1.3 ha pagato in pubblico: una
 * correzione applicata a due copie su quattro. La regola che ne era uscita —
 * "prima di aggiungere la N-esima copia di una logica, estrarla" — qui vale
 * doppio, perche' sappiamo gia' che quel codice DOVRA' cambiare: Compose ha
 * deprecato LocalClipboardManager a favore di LocalClipboard, che espone
 * funzioni sospendibili. Quando si fara' quella migrazione, ci sara' una sola
 * funzione da riscrivere e un solo punto da collaudare invece di cinque file.
 *
 * COSA NON FA QUESTO FILE, DI PROPOSITO.
 * Non migra niente. L'API usata qui dentro e' identica a quella di prima, byte
 * per byte: il codice che gira e' lo stesso, solo scritto una volta. Era la
 * condizione per farlo entrare in una release che cambia anche altro — nessun
 * cambio di comportamento significa nessun sospettato in piu'.
 *
 * L'unico effetto visibile e' sugli avvisi di compilazione: c'era un
 * `LocalClipboardManager.current` per ciascuno dei cinque file, quindi cinque
 * avvisi identici; adesso ce n'e' uno solo, qui sotto. Cinque avvisi diventano
 * uno senza che una riga si comporti diversamente.
 *
 * NOTA SUL TIPO: il tipo `ClipboardManager` non viene mai nominato. Se anche
 * quello finisse deprecato in una versione futura di Compose, l'avviso
 * resterebbe confinato alle due lambda qui sotto invece di spargersi sulle
 * firme.
 */
package org.openusenet.reader.ui

import android.content.Context
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
import org.openusenet.reader.R

/**
 * Accesso agli appunti di sistema. Si ottiene con [rememberAppunti] dentro una
 * funzione composable; non costruirla a mano.
 */
class Appunti internal constructor(
    private val scrivi: (String) -> Unit,
    private val leggi: () -> String,
) {
    /** Mette [testo] negli appunti, sostituendo quello che c'era. */
    fun copia(testo: String) {
        scrivi(testo)
    }

    /**
     * Il testo attualmente negli appunti, stringa vuota se non ce n'e'.
     *
     * 1.4.1 (build 106) — VA CHIAMATA AL MOMENTO DELL'USO, MAI IN COMPOSIZIONE.
     *
     * Da Android 10 un'app puo' LEGGERE gli appunti solo mentre ha il focus di
     * input; scriverli no, quello le riesce sempre. E' esattamente l'asimmetria
     * misurata da Roxy sul suo box TV: "copia in NeReO e incolla altrove"
     * funziona, "copia altrove e apri la risposta in NeReO" no.
     *
     * Chiamandola mentre si disegna la schermata, la risposta dipende da se il
     * focus sia gia' arrivato — e sul telefono arriva subito dopo, perche' la
     * comparsa della tastiera fa ridisegnare la barra e la seconda lettura
     * riesce. Su un dispositivo SENZA tastiera a schermo quella seconda lettura
     * non avviene mai: la barra si disegna una volta sola e il risultato vuoto
     * resta per sempre. Il difetto c'era per tutti; sul telefono era riparato
     * per caso da una cosa che non c'entra.
     *
     * Chiamata sul clic dell'utente, il focus c'e' per definizione.
     */
    fun incolla(): String = leggi()
}

/**
 * L'unico punto dell'app che legge `LocalClipboardManager`.
 *
 * Il risultato e' memorizzato con [remember] sulla chiave del gestore: cambia
 * solo se cambia il gestore stesso, cioe' praticamente mai. Le due lambda non
 * vengono percio' ricreate a ogni ricomposizione.
 */
@Composable
fun rememberAppunti(): Appunti {
    val gestore = LocalClipboardManager.current
    val contesto = LocalContext.current
    return remember(gestore, contesto) {
        Appunti(
            // la scrittura resta quella di sempre: funziona, e non si tocca
            // cio' che funziona nello stesso giro in cui si corregge altro.
            scrivi = { testo -> gestore.setText(AnnotatedString(testo)) },
            leggi = { leggiAppuntiDiSistema(contesto) },
        )
    }
}

/**
 * 1.4.1 (build 106) — perche' NON si usa piu' `LocalClipboardManager.getText()`.
 *
 * Quella legge il solo campo di testo semplice della clip: se l'app da cui si
 * e' copiato ha messo HTML, un URI o un intent — cosa comunissima copiando da
 * un browser — torna null, e NeReO conclude che gli appunti siano vuoti mentre
 * qualunque altra app, incollando, il testo lo ottiene.
 *
 * `coerceToText` e' cio' che usano le altre app: prende il testo semplice se
 * c'e', altrimenti converte quello che trova. E' il secondo dei due rami che
 * spiegano il caso di Roxy, e si chiude qui senza dover sapere quale dei due
 * fosse.
 *
 * Tutto dentro un runCatching: una lettura degli appunti che fallisce e' un
 * fastidio, non un motivo per far cadere la schermata di scrittura.
 */
private fun leggiAppuntiDiSistema(contesto: Context): String = runCatching {
    val gestore = contesto.getSystemService(Context.CLIPBOARD_SERVICE)
        as? android.content.ClipboardManager ?: return@runCatching ""
    val clip = gestore.primaryClip ?: return@runCatching ""
    if (clip.itemCount == 0) return@runCatching ""
    clip.getItemAt(0)?.coerceToText(contesto)?.toString().orEmpty()
}.getOrDefault("")

/**
 * IL PULSANTE INCOLLA DELLA SCRITTURA, IN UN POSTO SOLO.
 *
 * 1.3.3 (Roxy): sul suo box TV il menu Incolla di Android dentro un campo di
 * testo si apre a pressione prolungata, e col trackpad — parole sue —
 * "l'operazione sarebbe davvero lenta e poco immediata doverlo fare ogni
 * volta". Questo pulsante fa la stessa cosa con un clic.
 *
 * 1.4.1 (105/H, Roxy): L'ICONA C'E' SEMPRE. Fino alla 1.4 compariva solo con
 * gli appunti pieni, e Roxy — che il pulsante l'aveva chiesto LUI — ha passato
 * settimane a cercarlo senza trovarlo, concludendo che non esistesse. Un
 * pulsante che compare solo a volte insegna che non c'e'. Appunti vuoti si
 * dice con un avviso al clic, non facendo sparire il comando: distinguere
 * "appunti vuoti" da "funzione assente" era esattamente l'informazione mancata
 * per tre tornate di corrispondenza sul suo box.
 *
 * 1.4.1 (build 106): gli appunti si leggono QUI, dentro il clic, non mentre la
 * barra si disegna. Vedi [Appunti.incolla] per il perche'.
 *
 * 1.4.4 (Roxy, 24 settembre 2026) — PERCHE' ORA E' UNA FUNZIONE E NON UN
 * BLOCCO DENTRO UNA SCHERMATA. Tutto il lavoro qui sopra era stato fatto in
 * `ComposeScreen`, la vista a pagine. Il pannello di scrittura dei pannelli
 * quel pulsante NON L'HA MAI AVUTO: non c'era niente da correggere perche' non
 * c'era niente. Roxy lo diceva da mesi ("con i pannelli non funziona") e ogni
 * volta gli si rispondeva che la correzione c'era — avevamo ragione tutti e
 * due, guardando schermate diverse. Sesto caso della serie "presente in una
 * copia su due". Estratto, non ricopiato.
 *
 * Inserisce nel PUNTO DEL CURSORE (sostituendo l'eventuale selezione) e sposta
 * il cursore in fondo all'inserito, che e' come si comporta l'incolla di
 * sistema.
 */
@Composable
fun PulsanteIncolla(
    testo: TextFieldValue,
    onTesto: (TextFieldValue) -> Unit,
    appunti: Appunti,
    snackbar: SnackbarHostState,
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    IconButton(
        onClick = {
            val daIncollare = appunti.incolla()
            if (daIncollare.isEmpty()) {
                scope.launch { snackbar.showSnackbar(ctx.getString(R.string.cmp_paste_empty)) }
                return@IconButton
            }
            val t = testo.text
            val sel = testo.selection
            val inizio = minOf(sel.start, sel.end).coerceIn(0, t.length)
            val fine = maxOf(sel.start, sel.end).coerceIn(0, t.length)
            val nuovo = t.substring(0, inizio) + daIncollare + t.substring(fine)
            onTesto(
                TextFieldValue(
                    text = nuovo,
                    selection = TextRange(inizio + daIncollare.length),
                ),
            )
        },
    ) {
        Icon(
            Icons.Default.ContentPaste,
            contentDescription = stringResource(R.string.cmp_paste_cd),
        )
    }
}
