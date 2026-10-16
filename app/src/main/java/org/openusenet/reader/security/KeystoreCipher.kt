/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Cifratura delle password server con Android Keystore (AES-256/GCM).
 * La chiave non lascia mai il Keystore; nel DB finisce solo il blob cifrato,
 * prefissato dal marcatore "enc:1:".
 *
 * Migrazione trasparente: un valore senza marcatore è considerato legacy in
 * chiaro, viene usato così com'è e verrà ricifrato al primo salvataggio.
 */
package org.openusenet.reader.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeystoreCipher : PasswordCipher {

    private val alias = "openusenet.server.pw"
    private val marker = "enc:1:"
    private val ks = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private fun key(): SecretKey {
        (ks.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.let { return it.secretKey }
        val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        kg.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
            )
                .setKeySize(256)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        return kg.generateKey()
    }

    override fun encrypt(plain: String): String {
        if (plain.isEmpty()) return ""
        if (plain.startsWith(marker)) return plain // idempotente: già cifrato
        val c = Cipher.getInstance("AES/GCM/NoPadding")
        c.init(Cipher.ENCRYPT_MODE, key())
        val iv = c.iv
        val ct = c.doFinal(plain.toByteArray(Charsets.UTF_8))
        val blob = ByteArray(1 + iv.size + ct.size)
        blob[0] = iv.size.toByte()
        System.arraycopy(iv, 0, blob, 1, iv.size)
        System.arraycopy(ct, 0, blob, 1 + iv.size, ct.size)
        return marker + Base64.encodeToString(blob, Base64.NO_WRAP)
    }

    override fun decrypt(stored: String): String {
        if (stored.isEmpty()) return ""
        if (!stored.startsWith(marker)) return stored // legacy in chiaro
        return try {
            val blob = Base64.decode(stored.removePrefix(marker), Base64.NO_WRAP)
            val ivLen = blob[0].toInt()
            val iv = blob.copyOfRange(1, 1 + ivLen)
            val ct = blob.copyOfRange(1 + ivLen, blob.size)
            val c = Cipher.getInstance("AES/GCM/NoPadding")
            c.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(128, iv))
            String(c.doFinal(ct), Charsets.UTF_8)
        } catch (_: Exception) {
            "" // chiave persa/ruotata: meglio credenziale vuota che un crash
        }
    }
}
