/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui

import androidx.annotation.StringRes
import org.openusenet.reader.R
import org.openusenet.reader.nntp.RifiutoInvio
import org.openusenet.reader.nntp.riconosciRifiutoInvio

/**
 * 1.5.5 — la riga di spiegazione da aggiungere a un invio fallito, oppure
 * `null`. Un posto solo per le DUE scritture (a pagine e a pannelli): la
 * regola che decide sta in [riconosciRifiutoInvio], qui c'e' solo la scelta
 * del testo.
 */
@StringRes
fun spiegazioneRifiutoInvio(messaggio: String?): Int? =
    when (riconosciRifiutoInvio(messaggio)) {
        RifiutoInvio.CONTENUTO_INSUFFICIENTE -> R.string.cmp_441_content
        RifiutoInvio.RIFIUTATO -> R.string.cmp_441_generic
        null -> null
    }
