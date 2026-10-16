/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui

/*
 * 1.5.1 — LA BOZZA CHE SI SALVA DA SOLA: LA DECISIONE, SEPARATA DAL MECCANISMO.
 *
 * IL DIFETTO (verificato il 14 settembre 2026, NeReO_wishlist_cita_da_altro_
 * messaggio.md §7): la bozza si salvava solo col pulsante «Salva bozza» e
 * prima di un invio. Chi usciva dalla scrittura senza toccarlo PERDEVA IL
 * TESTO. Con «Cita» (1.5) la trappola diventa piu' probabile: si incolla una
 * citazione, si esce a cercarne un'altra, e il messaggio non c'e' piu'.
 *
 * Marco ha scelto il piano A di allora: autosalvataggio con attesa (debounce)
 * piu' un salvataggio all'uscita, con TRE VINCOLI che il documento chiamava
 * «casi di collaudo, non buone intenzioni». Stanno QUI, in Kotlin puro senza un
 * solo import, perche' si possono PROVARE (controlli/bozza/prova_bozza.sh):
 *
 *  T1 — una risposta NON e' vuota quando la apri: nasce con introduzione e
 *       testo citato. Il confronto non e' col vuoto, e' col contenuto generato
 *       ALL'APERTURA. Una risposta aperta per sbaglio non deve diventare una
 *       bozza.
 *  T2 — la bozza che risorge: invio -> cancellazione -> l'attesa scade -> si
 *       salva di nuovo il testo appena spedito. L'autosalvataggio si ferma nel
 *       momento in cui l'invio PARTE, e riprende solo se l'invio fallisce.
 *  T3 — la spazzatura: con l'autosalvataggio ogni messaggio cominciato diventa
 *       una bozza. Serve «Scarta» — che qui e' `chiudi()` seguita dalla
 *       cancellazione, nella schermata.
 *
 * Il MECCANISMO (attesa, uscita, Mutex) sta in ComposeScreen: dipende da
 * Compose e dal ciclo di vita, e non si prova da qui.
 */

/**
 * Decide se lo stato attuale della scrittura va salvato come bozza.
 *
 * Non e' thread-safe da sola: la schermata la usa sotto un Mutex, lo stesso che
 * protegge salvataggio e cancellazione (vedi T2).
 */
class GuardiaBozza {
    private var iniziale: String? = null
    private var giaSalvata = false

    /** true dal momento in cui parte un invio o uno «Scarta» (T2, T3). */
    var chiusa: Boolean = false
        private set

    /**
     * Fissa il contenuto generato all'apertura (T1). Solo la PRIMA volta: le
     * chiamate successive non lo spostano, altrimenti il riferimento seguirebbe
     * le modifiche dell'utente e niente risulterebbe mai «cambiato».
     *
     * [esisteGia] = si sta riprendendo una bozza salvata: ogni modifica la
     * aggiorna, anche quella che riporta il testo com'era all'apertura.
     */
    fun fissaIniziale(oggetto: String, corpo: String, esisteGia: Boolean = false) {
        if (iniziale != null) return
        iniziale = chiave(oggetto, corpo)
        giaSalvata = esisteGia
    }

    /**
     * Salvare adesso? No se l'apertura non e' finita, no dopo un invio o uno
     * «Scarta». Si' se il testo e' diverso da quello generato all'apertura —
     * OPPURE se una bozza esiste gia': un utente che modifica e poi torna al
     * testo iniziale deve ritrovare quello, non la versione intermedia.
     */
    fun deveSalvare(oggetto: String, corpo: String): Boolean {
        val i = iniziale ?: return false
        if (chiusa) return false
        return giaSalvata || chiave(oggetto, corpo) != i
    }

    /** Da chiamare dopo un salvataggio riuscito (id della bozza > 0). */
    fun segnaSalvata() {
        giaSalvata = true
    }

    /** Parte un invio o uno «Scarta»: da qui in poi non si salva piu' (T2). */
    fun chiudi() {
        chiusa = true
    }

    /** L'invio e' fallito: la bozza deve tornare a proteggere il testo. */
    fun riapri() {
        chiusa = false
    }

    // Separatore che un testo scritto a mano non contiene: «ab»+«c» non deve
    // coincidere con «a»+«bc».
    private fun chiave(oggetto: String, corpo: String) = oggetto + "\u0000" + corpo
}
