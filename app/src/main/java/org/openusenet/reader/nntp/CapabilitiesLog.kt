/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.nntp

/*
 * 1.4.11 / 156 — LA RIGA `CAPABILITIES:` DEL LOG NNTP.
 *
 * PERCHE' ESISTE QUESTO FILE, e perche' e' Kotlin PURO senza un import.
 *
 * Fino alla build 155 la riga di diagnostica era:
 *
 *     NntpLog.diag("CAPABILITIES: " + caps.joinToString(", ").take(300))
 *
 * `take(300)` taglia E NON LO DICE. Una riga tagliata ha esattamente l'aspetto
 * di una riga completa, e il 12 ottobre 2026 ha portato fuori strada DUE VOLTE
 * nello stesso pomeriggio, durante il collaudo del par. B3:
 *
 *  1. `news.tcpreset.net:119` e `news.solani.org:563` producevano righe di
 *     ESATTAMENTE 300 caratteri — misurate — mentre quella di
 *     `news.usenet.ovh:119` ne faceva 225 ed era intera.
 *  2. Da quelle righe tagliate si era concluso che tcpreset non offrisse
 *     STARTTLS. Poi Marco ha acceso l'interruttore su tcpreset e il server ha
 *     risposto `382 Begin TLS negotiation now`: LO OFFRE, ed era oltre il
 *     taglio. Il nome che decideva era proprio quello che mancava.
 *
 * > Una troncatura senza un segno non e' un difetto cosmetico quando il dato
 * > tagliato e' quello che decide.
 *
 * ⚠️ Il taglio era solo nella STRINGA STAMPATA: `advertisedCapabilities`
 * contiene la lista intera e il par. B3 lavora sul dato completo. Chi veniva
 * ingannato era la PERSONA che legge il log — cioe' Marco, e chiunque gli
 * mandi un log. Una decisione di sicurezza (accendere o no STARTTLS su un
 * server) stava per essere presa su una stringa interrotta.
 *
 * COSA SI FA, E PERCHE' NON SI ALZA SOLO IL TETTO. Il gonfiore non viene dal
 * NUMERO di capability: viene da UNA capability con una lista di argomenti
 * enorme (`SASL` con sette meccanismi, `LIST` con dodici parole). Alzare il
 * tetto a 600 avrebbe spostato il problema di qualche server. Si accorciano
 * invece GLI ARGOMENTI, una capability per volta e partendo dalla piu'
 * ingombrante, finche' la riga ci sta — cosi' **nessun NOME di capability si
 * perde mai**, e i nomi sono esattamente cio' che serve a chi legge.
 *
 * ⚠️ Scartato «marcare il taglio e basta»: avrebbe detto al lettore di essere
 * cieco, lasciandolo cieco. Su tcpreset si sarebbe letto «TRONCATA» e si
 * sarebbe dovuto fare l'esperimento comunque.
 *
 * PERCHE' IN UN FILE SUO. E' Kotlin puro, senza un solo import: si compila da
 * solo con `kotlinc`, senza Android e senza Gradle, quindi si puo' PROVARE
 * invece di crederci — come `WrapBody.kt` e `FormatFlowed.kt`, ed e' la stessa
 * ragione per cui la voce 11 li ha spostati nel package `text`. *Se il
 * programma si puo' eseguire, si esegue.*
 */

/** Lunghezza massima della riga `CAPABILITIES:` nel log NNTP. */
internal const val LIMITE_LOG_CAPABILITIES = 300

/**
 * Rende la lista CAPABILITIES in una riga di log lunga al massimo [limite]
 * caratteri, **senza perdere nessun nome di capability** finche' e' possibile.
 *
 * Tre stadi, in quest'ordine:
 *
 *  1. se la riga intera ci sta, si restituisce intera;
 *  2. altrimenti si accorciano gli ARGOMENTI, una capability per volta,
 *     partendo da quella che occupa piu' spazio, finche' la riga rientra.
 *     Una capability accorciata diventa `SASL (+7 argomenti)`;
 *  3. se anche i soli nomi sforano — nessun server noto ci arriva, ma non si
 *     lascia un ramo senza esito — si taglia e **lo si dichiara**, col numero
 *     di capability e la lunghezza vera.
 */
internal fun capabilitiesPerIlLog(
    caps: List<String>,
    limite: Int = LIMITE_LOG_CAPABILITIES,
): String {
    val nomi = ArrayList<String>(caps.size)
    val argomenti = ArrayList<List<String>>(caps.size)
    for (c in caps) {
        val pezzi = c.trim().split(' ', '\t').filter { it.isNotEmpty() }
        if (pezzi.isEmpty()) continue
        nomi.add(pezzi[0])
        argomenti.add(pezzi.drop(1))
    }
    val ridotta = BooleanArray(nomi.size)

    fun riga(): String = nomi.indices.joinToString(", ") { i ->
        when {
            argomenti[i].isEmpty() -> nomi[i]
            ridotta[i] -> nomi[i] + " (+" + argomenti[i].size + " argomenti)"
            else -> nomi[i] + " " + argomenti[i].joinToString(" ")
        }
    }

    var r = riga()
    while (r.length > limite) {
        // La piu' ingombrante fra quelle non ancora ridotte. Si guarda lo
        // spazio OCCUPATO dagli argomenti, non il loro numero: sette sigle
        // lunghe pesano piu' di dodici parole corte.
        var scelta = -1
        var peso = 0
        for (i in nomi.indices) {
            if (ridotta[i] || argomenti[i].isEmpty()) continue
            var costo = 0
            for (a in argomenti[i]) costo += a.length + 1
            if (costo > peso) {
                peso = costo
                scelta = i
            }
        }
        if (scelta < 0) break // non resta piu' niente da accorciare
        ridotta[scelta] = true
        r = riga()
    }

    if (r.length <= limite) return r
    val intera = caps.joinToString(", ")
    return r.take(limite) + "… [TRONCATA: " + nomi.size +
        " capability, " + intera.length + " caratteri in tutto]"
}
