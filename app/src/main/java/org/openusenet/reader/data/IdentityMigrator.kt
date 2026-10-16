/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Migrazione una-tantum verso la 1.2.9 (multi-identità, richiesta di Marco:
 * "un multi utente reale, come in MesNews e/o NR MOD17 di Piao Hong").
 *
 * Prima della 1.2.9 c'era un'identità GENERALE (preferenze sciolte, file
 * "identity") più, per server, un override libero di solo nome/email/firma
 * (chiavi name.server.<id>/email.server.<id>/signature.server.<id>, vedi lo
 * storico ComposeLogic.kt). Qui quei dati diventano righe vere nella tabella
 * `identities`: una "Generale" (marcata default) più una dedicata per ogni
 * server che aveva un override non vuoto — assegnata subito a quel server,
 * così chi già postava come "MCSM" su un server continua a farlo, INVARIATO,
 * al primo avvio della 1.2.9. Nessuna sorpresa silenziosa.
 */
package org.openusenet.reader.data

import android.content.Context

/** Identità nel formato pre-1.2.9 (quello che viveva nelle preferenze "identity"). */
data class LegacyIdentityFields(
    val name: String = "",
    val email: String = "",
    val signature: String = "",
    val sigEnabled: Boolean = true,
    val sigRandom: Boolean = false,
    val userAgent: String = "",
    val xNoArchive: Boolean = false,
    val customHeaders: String = "",
    val replyIntro: String = "",
    val faceB64: String? = null,
    val faceSize: Int = 48,
) {
    /**
     * 1.2.13: true se non c'è PROPRIO NIENTE da migrare — cioè se le vecchie
     * preferenze sono tutte vuote, cosa che capita solo su un'installazione
     * appena fatta. Serve a distinguere "primo avvio su app nuova" da
     * "aggiornamento di chi l'app ce l'aveva già": due casi che vanno trattati
     * in modo opposto quanto allo User-Agent (vedi migrateFromPrefsIfNeeded).
     *
     * Gemella della proprietà omonima in [LegacyServerOverride] qui sotto.
     * Booleani e dimensioni NON entrano nel conto: hanno un valore anche su
     * un'installazione vergine, quindi non distinguono nulla.
     */
    val isBlank get() = name.isBlank() && email.isBlank() && signature.isBlank() &&
        userAgent.isBlank() && customHeaders.isBlank() && replyIntro.isBlank() &&
        faceB64.isNullOrBlank()
}

/** Override per-server pre-1.2.9: solo nome/email/firma, campo vuoto = "usa la generale". */
data class LegacyServerOverride(
    val serverId: Long,
    val name: String,
    val email: String,
    val signature: String,
) {
    val isBlank get() = name.isBlank() && email.isBlank() && signature.isBlank()
}

object IdentityMigrator {
    private const val PREFS = "identity"
    private const val FLAG_KEY = "migrated_to_identities_v1"

    /**
     * Da chiamare una volta all'avvio dell'app (App.onCreate, dentro
     * appScope.launch: serve accesso al database). Idempotente: dopo la prima
     * esecuzione riuscita non fa più nulla, nemmeno rileggere le preferenze.
     */
    suspend fun migrateFromPrefsIfNeeded(repo: NewsRepository, ctx: Context) {
        val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (prefs.getBoolean(FLAG_KEY, false)) return
        // Difesa aggiuntiva: se esistono già identità (per esempio perché un
        // ripristino da backup 1.2.9 è avvenuto prima ancora di questo primo
        // avvio) non tocco nulla — segno solo il flag per non ripassare di qui.
        if (repo.identitiesOnce().isNotEmpty()) {
            prefs.edit().putBoolean(FLAG_KEY, true).apply()
            return
        }
        val global = LegacyIdentityFields(
            name = prefs.getString("name", "") ?: "",
            email = prefs.getString("email", "") ?: "",
            signature = prefs.getString("signature", "") ?: "",
            sigEnabled = prefs.getBoolean("sig_enabled", true),
            sigRandom = prefs.getBoolean("sig_random", false),
            userAgent = prefs.getString("user_agent", "") ?: "",
            xNoArchive = prefs.getBoolean("x_no_archive", false),
            customHeaders = prefs.getString("custom_headers", "") ?: "",
            replyIntro = prefs.getString("reply_intro", "") ?: "",
            faceB64 = prefs.getString("face_b64", null),
            faceSize = prefs.getInt("face_size", 48),
        )
        val servers = repo.allServersOnce()
        val overrides = servers.mapNotNull { s ->
            val n = prefs.getString("name.server.${s.id}", "") ?: ""
            val e = prefs.getString("email.server.${s.id}", "") ?: ""
            val sig = prefs.getString("signature.server.${s.id}", "") ?: ""
            LegacyServerOverride(s.id, n, e, sig).takeUnless { it.isBlank }
        }
        // 1.2.13 (Merovingio, tramite Marco): distinguo INSTALLAZIONE NUOVA da
        // AGGIORNAMENTO. Se non c'è proprio nulla da migrare (nessun nome,
        // nessuna email, nessuna firma, nessun override per-server) siamo al
        // primo avvio su un'app appena installata: l'identità che nasce qui
        // avrebbe lo User-Agent vuoto, cioè posterebbe anonima senza che
        // l'utente lo abbia scelto — il difetto segnalato. Le si dà quindi il
        // valore proposto, che resta modificabile e svuotabile.
        //
        // Se invece qualcosa da migrare c'è, si tratta di un utente che l'app
        // già ce l'aveva: il suo User-Agent viene ripreso ESATTAMENTE com'era,
        // vuoto compreso. Uno che aveva scelto l'anonimato non se lo deve
        // ritrovare revocato da un aggiornamento.
        val primaInstallazione = global.isBlank && overrides.isEmpty()
        val daMigrare = if (primaInstallazione) {
            global.copy(userAgent = org.openusenet.reader.Branding.USER_AGENT_PREDEFINITO)
        } else {
            global
        }
        migrateFromValues(repo, daMigrare, overrides, servers.associate { it.id to it.name })
        prefs.edit().putBoolean(FLAG_KEY, true).apply()
    }

    /**
     * Nucleo condiviso della migrazione, indipendente dalla FONTE dei dati:
     * crea l'identità di default da [global] e, per ogni voce di [perServer],
     * una nuova identità dedicata assegnata a quel server. [nomiServer] serve
     * solo a dare un nickname leggibile alla nuova identità quando l'override
     * non aveva un nome proprio.
     *
     * Riusata sia dalla migrazione al primo avvio (sopra, dati da
     * SharedPreferences) sia dal ripristino di un backup PRE-1.2.9
     * (BackupManager, dati dallo stesso JSON che prima finiva nelle
     * preferenze) — stessa logica, fonte diversa. Ritorna l'id dell'identità
     * di default appena creata.
     */
    suspend fun migrateFromValues(
        repo: NewsRepository,
        global: LegacyIdentityFields,
        perServer: List<LegacyServerOverride>,
        nomiServer: Map<Long, String> = emptyMap(),
    ): Long {
        val defaultId = repo.saveIdentity(
            Identity(
                nickname = global.name.ifBlank { "Generale" },
                name = global.name,
                email = global.email,
                signature = global.signature,
                sigEnabled = global.sigEnabled,
                sigRandom = global.sigRandom,
                userAgent = global.userAgent,
                xNoArchive = global.xNoArchive,
                customHeaders = global.customHeaders,
                replyIntro = global.replyIntro,
                faceB64 = global.faceB64,
                faceSize = global.faceSize,
                isDefault = true,
            ),
        )
        for (ov in perServer) {
            val nomeServer = nomiServer[ov.serverId] ?: "Server ${ov.serverId}"
            val newId = repo.saveIdentity(
                Identity(
                    nickname = ov.name.ifBlank { nomeServer },
                    name = ov.name.ifBlank { global.name },
                    email = ov.email.ifBlank { global.email },
                    signature = ov.signature.ifBlank { global.signature },
                    // Campi che il vecchio schema non prevedeva per-server: ereditano
                    // dalla generale. Nessuna perdita, nessuna sorpresa silenziosa.
                    sigEnabled = global.sigEnabled,
                    sigRandom = global.sigRandom,
                    userAgent = global.userAgent,
                    xNoArchive = global.xNoArchive,
                    customHeaders = global.customHeaders,
                    replyIntro = global.replyIntro,
                    faceB64 = global.faceB64,
                    faceSize = global.faceSize,
                    isDefault = false,
                ),
            )
            repo.assignIdentityToServer(ov.serverId, newId)
        }
        return defaultId
    }
}
