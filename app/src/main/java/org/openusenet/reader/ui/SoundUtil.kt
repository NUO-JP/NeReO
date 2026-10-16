/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui

import android.content.Context
import android.media.RingtoneManager

/** Breve segnale sonoro di notifica (usa la suoneria di sistema). Silenzioso in caso di errore. */
fun playDing(context: Context) {
    runCatching {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        RingtoneManager.getRingtone(context.applicationContext, uri)?.play()
    }
}
