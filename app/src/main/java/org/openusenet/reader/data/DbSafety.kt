/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Rete di protezione contro la perdita silenziosa di dati.
 *
 * Room, di default (fallbackToDestructiveMigration), quando incontra uno schema
 * che non sa migrare AZZERA il database senza avvisare. Due scenari reali per
 * un'app distribuita via APK: un domani manca una migrazione in avanti (errore
 * umano), oppure l'utente installa un APK piu' VECCHIO sopra un database piu'
 * nuovo (downgrade). In entrambi i casi i dati sparirebbero senza traccia.
 *
 * Qui stanno solo le decisioni PURE (nessuna dipendenza Android), cosi' sono
 * verificabili con unit test. Le operazioni sul file (lettura versione, copia
 * di backup) vivono in Db.kt, dove serve il Context.
 *
 * PROPRIETA' DI SICUREZZA: questo codice non decide MAI il wipe (lo controlla
 * Room con le migrazioni registrate). Decide solo se fare una COPIA di scorta
 * prima. Se la logica di versione qui fosse sbagliata, il caso peggiore e' una
 * copia inutile: mai una perdita di dati, mai una migrazione legittima impedita.
 */
package org.openusenet.reader.data

object DbSafety {

    /** SharedPreferences che segnala all'interfaccia un reset gia' avvenuto. */
    const val PREFS = "db_maintenance"
    /** Percorso del file di backup salvato (String, puo' mancare se la copia e' fallita). */
    const val KEY_BACKUP_PATH = "pending_reset_backup"
    /** Versione trovata su disco al momento del reset (Int). La sua PRESENZA = "avviso da mostrare". */
    const val KEY_FROM_VERSION = "pending_reset_from_version"

    /**
     * Insieme delle versioni di schema da cui esiste un percorso di migrazione
     * (anche a piu' passi) che arriva fino a [current], date le coppie
     * (from, to) delle migrazioni registrate. Include [current] stesso.
     *
     * Calcolo a ritroso da [current]: parto da {current} e aggiungo ogni `from`
     * la cui `to` e' gia' raggiungibile, fino a punto fisso. Gestisce anche
     * catene a piu' passi, non solo migrazioni a passo singolo.
     */
    fun versioniMigrabili(passi: List<Pair<Int, Int>>, current: Int): Set<Int> {
        val raggiungibili = mutableSetOf(current)
        var cambiato = true
        while (cambiato) {
            cambiato = false
            for ((from, to) in passi) {
                if (to in raggiungibili && from !in raggiungibili) {
                    raggiungibili.add(from)
                    cambiato = true
                }
            }
        }
        return raggiungibili
    }

    /**
     * Vero se, per un database la cui versione su disco e' [onDiskVersion], una
     * migrazione DISTRUTTIVA e' imminente e conviene quindi fare un backup prima.
     *
     * - 0  = database nuovo/vuoto (user_version di default): niente da perdere.
     * - == [current]: gia' aggiornato, nessuna migrazione.
     * - tra le versioni [migrabili]: Room sa migrarlo, dati preservati.
     * - tutto il resto (versione ignota, downgrade, file illeggibile marcato con
     *   un sentinella non-zero): Room lo azzererebbe -> serve il backup.
     */
    fun serveBackup(onDiskVersion: Int, current: Int, migrabili: Set<Int>): Boolean {
        if (onDiskVersion == 0) return false
        if (onDiskVersion == current) return false
        return onDiskVersion !in migrabili
    }
}
