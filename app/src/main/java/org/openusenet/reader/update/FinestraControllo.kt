/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.update

/*
 * 1.5.4 — QUANDO SI CONTROLLA SE C'E' UNA VERSIONE NUOVA. Kotlin puro, senza
 * un solo import: si prova con kotlinc (controlli/aggiornamenti), come le
 * altre decisioni del progetto. UpdateChecker resta il punto d'ingresso.
 *
 * «UNA VOLTA AL GIORNO» CHE NON CONTROLLAVA (segnalazione di un utente via
 * Marco, 15 ottobre 2026: «messo su "una volta al giorno" sembra che non
 * controlli; il workaround e' "ad ogni apertura"»).
 *
 * Non era un guasto: era il significato. Dalla 1.2.8 «una volta al giorno»
 * voleva dire «24 ore dall'ultimo controllo riuscito»: controllato ieri alle
 * 21, oggi alle 18 non si controlla. Con quattro versioni in una settimana
 * l'avviso arrivava fino a un giorno dopo, e sembrava non arrivare mai.
 *
 * Piano B (Marco): si controlla se sono passate 24 ore OPPURE se l'ultimo
 * controllo e' di un giorno di calendario PRECEDENTE. La prima apertura di ogni
 * giorno controlla sempre. ⚠️ NON torna il difetto della 1.2.8 (Bingo3331): li'
 * il problema era il SOLO confronto per data, che dopo un controllo mattutino
 * «bruciava» la giornata. Qui la data e' un'ALTRA via per controllare, mai una
 * ragione per non farlo. Il timestamp si scrive sempre e solo a controllo
 * riuscito (MainActivity), come dalla 1.2.8.
 */

/**
 * 1.2.8 (Bingo3331 + HAL): intervallo minimo fra due controlli automatici,
 * per modalita' (`AppSettings.autoCheckMode`). null = mai controllare;
 * 0 = nessuna attesa (a ogni apertura); altrimenti millisecondi minimi
 * trascorsi dall'ultimo controllo RIUSCITO.
 */
fun intervalloControlloMs(mode: String): Long? = when (mode) {
    "never" -> null
    "always" -> 0L
    "4h" -> 4L * 60 * 60 * 1000
    else -> 24L * 60 * 60 * 1000 // "daily" e fallback per valori sconosciuti
}

/**
 * Controllare adesso? [giornoUltimo] e [giornoOggi] sono giorni di calendario
 * (epoch day) gia' calcolati dal chiamante nel fuso del telefono: qui niente
 * orologi e niente fusi, quindi niente da indovinare nelle prove.
 */
fun deveControllareAggiornamenti(
    mode: String,
    ultimoMs: Long,
    oraMs: Long,
    giornoUltimo: Long,
    giornoOggi: Long,
): Boolean {
    val intervallo = intervalloControlloMs(mode) ?: return false
    if (ultimoMs <= 0L) return true
    if (oraMs - ultimoMs >= intervallo) return true
    // il giorno nuovo conta solo per «daily» (e per i valori sconosciuti, che
    // valgono «daily»): «ogni 4 ore» e «a ogni apertura» hanno gia' la loro regola
    val giornaliero = mode != "always" && mode != "4h"
    return giornaliero && giornoOggi > giornoUltimo
}
