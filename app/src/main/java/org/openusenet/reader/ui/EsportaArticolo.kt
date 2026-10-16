/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Esportazione di un articolo verso un'altra app: UN SOLO punto in tutta l'app.
 *
 * PERCHE' ESISTE (1.3.6).
 * Richiesta di **Adam H. Kerman** su un gruppo anglofono, il 13 settembre 2026.
 * NeReO e' un lettore Usenet e non sa mandare posta: quando un autore chiede
 * risposte private (`Followup-To: poster`, o semplicemente perche' e' la cosa
 * giusta da fare) l'utente resta senza strade. La sua proposta —
 * «allow the user to save the article to an archived messages folder to allow
 * him to reply with an email client» — risolve il problema senza mettere un
 * client di posta dentro un newsreader: si consegna l'articolo GREZZO a
 * un'altra app, e a quel punto se ne occupa quella.
 *
 * E HA SCOPERTO ANCHE DELL'ALTRO. Andando a verificare se esistesse gia' una
 * condivisione, e' emerso che NON ESISTE e non e' mai esistita fuori dalla
 * barra di selezione del testo. «Cerca» e «Condividi» stavano in
 * SelectionToolbar.kt, cancellato nella 1.3.4 quando Compose 1.12 ha smesso di
 * passare da LocalTextToolbar — e sono morte con lui. Due documenti di progetto
 * affermavano che fossero «ancora raggiungibili dalla barra dell'articolo e dal
 * menu ⋮»: era falso, e i documenti sono stati corretti.
 *
 * COSA SI ESPORTA. L'articolo com'e' arrivato: intestazioni complete piu' il
 * corpo, separati da una riga vuota — la forma che un client di posta o un
 * archivio sanno leggere. Se le intestazioni non sono disponibili (corpo mai
 * scaricato per intero) si esporta il solo corpo, invece di inventare
 * intestazioni che non abbiamo visto.
 *
 * COSA NON FA. Non scrive file e non chiede permessi sul disco: passa il testo
 * a un Intent di condivisione, ed e' l'app scelta dall'utente a decidere dove
 * finisce. E' la strada che non richiede nessun permesso nuovo.
 */
package org.openusenet.reader.ui

import android.content.Context
import android.content.Intent

/**
 * L'articolo nella forma in cui e' arrivato: intestazioni + riga vuota + corpo.
 *
 * [headersRaw] e' il blocco salvato da `fetchBody`; puo' mancare se il corpo non
 * e' mai stato scaricato. In quel caso si restituisce il solo corpo: meglio un
 * export incompleto e onesto di uno con intestazioni ricostruite a mano.
 */
fun articoloGrezzo(headersRaw: String?, corpo: String?): String {
    val h = headersRaw?.trimEnd().orEmpty()
    val b = corpo.orEmpty()
    return if (h.isEmpty()) b else "$h\n\n$b"
}

/**
 * Consegna [testo] a un'altra app. [titolo] e' il titolo del selettore, gia'
 * tradotto dal chiamante (questa funzione non e' composable e non risolve
 * risorse da se').
 *
 * `runCatching`: su un dispositivo senza nessuna app capace di ricevere testo —
 * raro ma possibile su un box TV spoglio, ed e' proprio il genere di apparecchio
 * che usano due dei nostri collaudatori — `startActivity` lancia
 * ActivityNotFoundException. Un'eccezione non intercettata qui chiuderebbe
 * l'app per un pulsante che non poteva funzionare.
 */
fun condividiTesto(ctx: Context, testo: String, titolo: String) {
    if (testo.isBlank()) return
    runCatching {
        val invio = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, testo)
        }
        ctx.startActivity(Intent.createChooser(invio, titolo))
    }
}
