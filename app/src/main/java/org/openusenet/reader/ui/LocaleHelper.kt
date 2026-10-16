/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Interruttore lingua DENTRO l'app (richiesta utenti). Strategia IBRIDA:
 *
 *  - Android 13+ (API 33+): si usa il LocaleManager di SISTEMA. La scelta
 *    fatta qui e quella in Impostazioni di sistema -> App -> NeReO -> Lingua
 *    sono LA STESSA COSA: una sola fonte di verita', nessuna divergenza. Il
 *    framework applica la lingua e ricrea l'Activity da se'.
 *
 *  - Android <= 12 (API < 33): il percorso di sistema non esiste, quindi qui
 *    l'interruttore in-app e' l'UNICO modo. Si salva una preferenza e si
 *    avvolge il Context in attachBaseContext (vedi App.kt e MainActivity.kt),
 *    poi si ricrea l'Activity a mano.
 *
 * Nessuna dipendenza AppCompat: l'Activity resta ComponentActivity/Compose e
 * il tema (compreso lo splash) non viene toccato.
 *
 * Lingue supportate: solo "" (segui il sistema), "it", "en" (decisione di
 * prodotto: nessun'altra lingua).
 */
package org.openusenet.reader.ui

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object LocaleHelper {

    private const val PREFS = "settings"
    private const val KEY = "app_language"

    /** Tag delle lingue offerte, nell'ordine del selettore. "" = segui il sistema. */
    val TAG_SISTEMA = ""
    val tagSupportati = listOf(TAG_SISTEMA, "it", "en")

    /**
     * Avvolge il Context con la lingua salvata. Da chiamare in attachBaseContext.
     * Su Android 13+ non fa nulla: al locale ci pensa il framework. Su <= 12
     * legge la preferenza DIRETTAMENTE (attachBaseContext gira prima dell'init
     * di AppSettings) e crea un context con la lingua scelta.
     */
    fun applica(base: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) return base
        val tag = base.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, "") ?: ""
        if (tag.isEmpty()) return base
        val locale = Locale.forLanguageTag(tag)
        Locale.setDefault(locale)
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        return base.createConfigurationContext(config)
    }

    /**
     * Imposta la lingua dell'app. [tag] in {"", "it", "en"} ("" = segui il
     * sistema). Su 13+ delega al LocaleManager (che ricrea l'Activity); su <= 12
     * salva la preferenza e ricrea l'Activity, cosi' attachBaseContext riapplica
     * la lingua.
     */
    fun impostaLingua(context: Context, tag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val lm = context.getSystemService(LocaleManager::class.java)
            lm?.applicationLocales =
                if (tag.isEmpty()) LocaleList.getEmptyLocaleList()
                else LocaleList.forLanguageTags(tag)
        } else {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY, tag).apply()
            activityDa(context)?.recreate()
        }
    }

    /**
     * Tag della lingua attualmente in uso, per evidenziare la voce nel selettore.
     * Su 13+ lo chiede al sistema; su <= 12 legge la preferenza. "" = sistema.
     */
    fun tagCorrente(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val lm = context.getSystemService(LocaleManager::class.java)
            val list = lm?.applicationLocales
            if (list == null || list.isEmpty) "" else list.get(0)?.language ?: ""
        } else {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, "") ?: ""
        }
    }

    /** Risale la catena dei ContextWrapper fino all'Activity, per poterla ricreare. */
    private fun activityDa(context: Context): Activity? {
        var c: Context = context
        while (c is ContextWrapper) {
            if (c is Activity) return c
            c = c.baseContext
        }
        return null
    }
}
