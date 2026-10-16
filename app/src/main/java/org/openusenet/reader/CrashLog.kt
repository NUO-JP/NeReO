/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.2.9.b (Piano C.1 — segnalazione di un utente del collaudo pubblico: "da
 * quando ho installato la 1.2.9, NeReO si chiude spesso improvvisamente".
 * Indagine completa in NeReO_bug_1.2.9.md, voce X). Senza uno stack trace
 * l'unica cosa possibile era formulare ipotesi (abduzione); niente di più.
 * Marco stesso non ha mai potuto riprodurlo, e i suoi tester non sanno usare
 * adb — quindi l'unico modo realistico di avere PRIMA O POI una prova vera è
 * far sì che l'app se la raccolga da sola.
 *
 * Un gestore delle eccezioni non gestite salva l'ultimo arresto anomalo su
 * un file interno PRIMA di lasciarlo proseguire esattamente come sempre: il
 * crash avviene comunque, nel momento in cui capita niente cambia per chi
 * sta usando l'app. Il file si legge da Manutenzione con lo stesso schema
 * copia/esporta già in uso per NntpLog — infrastruttura che i tester hanno
 * già dimostrato di saper usare per la diagnostica NNTP.
 *
 * PRIVACY: lo stack trace può contenere nomi di classi/metodi interni
 * dell'app, mai credenziali o contenuto dei messaggi (non sono nel percorso
 * di esecuzione di un crash tipico). Il file resta locale sul dispositivo:
 * lascia il device solo se l'utente lo copia/esporta e lo invia di persona.
 */
package org.openusenet.reader

import android.content.Context
import android.os.Build
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CrashLog {
    private const val FILE_NAME = "last_crash.txt"

    /**
     * Da chiamare una volta sola, il prima possibile in App.onCreate — PRIMA
     * di qualunque altra inizializzazione, così anche un crash durante
     * l'avvio (es. una futura migrazione del database) viene catturato.
     * Avvolge (non sostituisce) un eventuale gestore già presente: raro su
     * Android, ma se un domani se ne aggiungesse un altro per qualche altro
     * motivo, i due continuano a cooperare invece di scavalcarsi a vicenda.
     */
    fun install(ctx: Context) {
        val app = ctx.applicationContext
        val precedente = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Il salvataggio non deve MAI impedire la normale segnalazione del
            // crash (logcat, eventuale gestore di sistema): qualunque problema
            // qui dentro viene ignorato in silenzio, mai propagato.
            runCatching { scrivi(app, throwable) }
            if (precedente != null) {
                precedente.uncaughtException(thread, throwable)
            } else {
                // Nessun gestore precedente (caso normale su Android): si
                // ripete il comportamento di sempre della JVM per un thread
                // senza gestore, così il crash resta visibile in logcat e nel
                // dialog di sistema "l'app si è arrestata" come oggi.
                android.os.Process.killProcess(android.os.Process.myPid())
                kotlin.system.exitProcess(10)
            }
        }
    }

    private fun scrivi(ctx: Context, t: Throwable) {
        val sw = StringWriter()
        t.printStackTrace(PrintWriter(sw))
        val quando = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
        val testo = buildString {
            appendLine("NeReO — arresto anomalo")
            appendLine("Quando: $quando")
            appendLine("Versione: ${BuildConfig.VERSION_NAME} (versionCode ${BuildConfig.VERSION_CODE})")
            appendLine("Dispositivo: ${Build.MANUFACTURER} ${Build.MODEL}, Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
            appendLine()
            append(sw.toString())
        }
        file(ctx).writeText(testo)
    }

    private fun file(ctx: Context): File = File(ctx.filesDir, FILE_NAME)

    /** Testo dell'ultimo arresto anomalo registrato, o stringa vuota se non ce n'è uno. */
    fun snapshot(ctx: Context): String =
        runCatching { file(ctx).takeIf { it.exists() }?.readText() }.getOrNull().orEmpty()

    /** Cancella il log salvato (dopo averlo copiato/esportato, o solo per fare pulizia). */
    fun clear(ctx: Context) {
        runCatching { file(ctx).delete() }
    }
}
