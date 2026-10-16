/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Astrazione per cifrare/decifrare le password server. Il core (Repository)
 * dipende solo da questa interfaccia, così resta testabile su JVM con il
 * NoOp; su Android viene iniettato KeystoreCipher.
 */
package org.openusenet.reader.security

interface PasswordCipher {
    /** Cifra il testo in chiaro. DEVE essere idempotente: cifrare un valore già cifrato lo lascia invariato. */
    fun encrypt(plain: String): String
    /** Decifra un valore cifrato; un valore legacy in chiaro (senza marcatore) viene restituito com'è. */
    fun decrypt(stored: String): String

    /** Passthrough per i test JVM (nessun Keystore disponibile fuori da Android). */
    object NoOp : PasswordCipher {
        override fun encrypt(plain: String): String = plain
        override fun decrypt(stored: String): String = stored
    }
}
