/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Cifratura del file di backup con una password scelta dall'utente.
 * Chiave derivata con PBKDF2WithHmacSHA256 (salt casuale), payload cifrato
 * con AES-256/GCM (IV casuale). L'envelope è un JSON leggibile che dichiara
 * i parametri e contiene salt/iv/ciphertext in Base64 — così il ripristino
 * è possibile su qualsiasi dispositivo conoscendo la password.
 *
 * Nota: senza la password il file NON è recuperabile (nessuna backdoor).
 */
package org.openusenet.reader.backup

import android.util.Base64
import org.json.JSONObject
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object BackupCrypto {

    private const val ITER = 120_000
    private const val KEYLEN = 256

    /** true se il testo è un envelope cifrato di OpenUsenet. */
    fun isEncrypted(text: String): Boolean = try {
        JSONObject(text).has("openusenet_enc")
    } catch (_: Exception) {
        false
    }

    private fun deriveKey(password: String, salt: ByteArray, iter: Int): SecretKeySpec {
        val spec = PBEKeySpec(password.toCharArray(), salt, iter, KEYLEN)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    }

    fun encrypt(plaintext: String, password: String): String {
        val rnd = SecureRandom()
        val salt = ByteArray(16).also { rnd.nextBytes(it) }
        val iv = ByteArray(12).also { rnd.nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, deriveKey(password, salt, ITER), GCMParameterSpec(128, iv))
        val ct = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
        return JSONObject()
            .put("openusenet_enc", 1)
            .put("kdf", "pbkdf2-sha256")
            .put("iter", ITER)
            .put("salt", Base64.encodeToString(salt, Base64.NO_WRAP))
            .put("iv", Base64.encodeToString(iv, Base64.NO_WRAP))
            .put("ct", Base64.encodeToString(ct, Base64.NO_WRAP))
            .toString(2)
    }

    /** @throws Exception se la password è errata o il file è danneggiato (tag GCM non valido). */
    fun decrypt(envelope: String, password: String): String {
        val o = JSONObject(envelope)
        // 1.2.12 (audit di sicurezza): il numero di iterazioni arriva dal file,
        // perché serve a rileggere backup fatti con versioni diverse. Senza un
        // tetto, però, un file con `iter` assurdo (un miliardo) fa girare la
        // derivazione della chiave per minuti o ore a interfaccia bloccata. Il
        // minimo evita l'opposto: un envelope che dichiari 1 iterazione
        // renderebbe la password quasi immediata da provare a forza bruta.
        val iter = o.optInt("iter", ITER).coerceIn(10_000, 1_000_000)
        val salt = Base64.decode(o.getString("salt"), Base64.NO_WRAP)
        val iv = Base64.decode(o.getString("iv"), Base64.NO_WRAP)
        val ct = Base64.decode(o.getString("ct"), Base64.NO_WRAP)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, deriveKey(password, salt, iter), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(ct), Charsets.UTF_8)
    }
}
