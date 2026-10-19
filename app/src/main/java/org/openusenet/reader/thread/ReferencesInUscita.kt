/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.thread

/**
 * 1.5.5 / 171 — IL References DI UNA RISPOSTA NON SUPERA MAI I 998 CARATTERI.
 *
 * LA SEGNALAZIONE (18 ottobre 2026, su Usenet). Rispondere a un post di
 * nl.politiek il cui References portava 27 identificativi falliva all'invio:
 * il server rifiutava l'articolo. NeReO costruiva il References della risposta
 * come vuole la RFC — References del padre + Message-ID del padre — ma lo
 * spediva cosi' com'era, senza tetto: 28 identificativi, ~1,4 KB su una riga.
 *
 * LE REGOLE, lette alla fonte:
 *  - RFC 5537 §3.4.4: se il campo, una volta srotolato, supera 998 caratteri
 *    (nome del campo compreso, CRLF finale escluso) DEVE essere accorciato;
 *    si tolgono identificativi, ma il PRIMO e gli ULTIMI DUE non si toccano.
 *  - RFC 5536 §2.2: nessuna riga di intestazione oltre 998 ottetti.
 *
 * COSA FA, in quest'ordine:
 *  1. legge gli identificativi con [Threader.idNelleReferences] — lo stesso
 *     riconoscimento del resto dell'app, col suo tetto di sicurezza: niente
 *     terza copia della regex;
 *  2. toglie i DOPPIONI tenendo la prima occorrenza. Un padre scritto bene non
 *     elenca il proprio Message-ID fra i suoi References, quindi il doppione
 *     nasce solo da client che sbagliano; toglierlo non costa niente;
 *  3. se «References: » + gli identificativi supera 998 ottetti (UTF-8), toglie
 *     il piu' VECCHIO dopo la radice, uno alla volta, finche' rientra. Restano
 *     la radice (che tiene insieme la discussione) e quanti piu' antenati
 *     recenti possibile: piu' di quanto la RFC chiede, mai piu' di quanto serve.
 *
 * Sotto il limite il risultato e' identico all'ingresso (identificativi
 * separati da uno spazio). Se anche radice + ultimi due superano il limite
 * (identificativi lunghi centinaia di caratteri: solo per costruzione
 * malevola) si restituiscono quei tre: la RFC vieta di toglierli.
 *
 * Kotlin puro, nessun import: si prova da solo con kotlinc.
 */
const val LIMITE_RIGA_INTESTAZIONE = 998
private const val NOME_CAMPO = "References: "

fun referencesInUscita(catena: String): String {
    val ids = Threader.idNelleReferences(catena).distinct().toMutableList()
    fun lunghezza(): Int = (NOME_CAMPO + ids.joinToString(" ")).encodeToByteArray().size
    while (ids.size > 3 && lunghezza() > LIMITE_RIGA_INTESTAZIONE) ids.removeAt(1)
    return ids.joinToString(" ")
}
