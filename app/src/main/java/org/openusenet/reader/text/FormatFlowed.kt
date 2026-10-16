/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * 1.4.6 (§B6a) — FORMAT=FLOWED IN LETTURA: smettere di indovinare.
 *
 * PERCHE' VIENE PRIMA DELLA META' CHE SCRIVE. NeReO ricompone gia' i capoversi
 * dei messaggi ricevuti, ma lo fa **a naso**: `reflowLines` stima la larghezza
 * di impaginazione e decide quali righe unire. I commenti di quel file
 * raccontano due ritarature, e la seconda spiega perche' — con righe lunghe ~39
 * contro una soglia di 40 «il riflusso non univa NIENTE». Un'euristica fa
 * esattamente questo: funziona finche' il caso somiglia a quelli che aveva in
 * mente chi l'ha scritta.
 *
 * Ma un messaggio che dichiara `format=flowed` **non va indovinato**: chi l'ha
 * scritto ha marcato una per una le righe che continuano. Leggere quella marca
 * e' sostituire un dato a una stima — ed e' la stessa forma di correzione di
 * §A12 e §A13.1, dove un valore «quasi sempre uguale a un altro» aveva smesso
 * di esserlo.
 *
 * ⚠️ E QUESTA META' NON TOCCA LA RETE. Interpretare cambia solo come vediamo i
 * messaggi altrui; generare cambia cio' che spediamo. Il principio 1 del
 * quaderno dice che davanti a una modifica rischiosa la build giusta e' quella
 * che **mette il metro**: qui il metro e' questo file, che ci fa vedere su
 * messaggi veri — Thunderbird e quasi tutti i lettori moderni usano f=f — se
 * abbiamo capito il formato, prima di emetterne uno.
 *
 * ⚠️ FILE DI KOTLIN PURO: nessun import, come `WrapBody.kt`, e per la stessa
 * ragione — le prove di `CoreTests` devono poterlo caricare senza Compose.
 *
 * Le regole qui sotto sono VERIFICATE ALLA FONTE (RFC 3676), non ricordate:
 * §4.1 interpretazione, §4.3 firma, §4.4 space-stuffing, §4.5 citazioni.
 */
package org.openusenet.reader.text

/** Il separatore di firma della convenzione Usenet: finisce con uno spazio. */
private const val SEPARATORE_FIRMA = "-- "

/**
 * Come il mittente dichiara di aver impaginato il corpo.
 *
 * [flowed] viene dal parametro `format=flowed` del `Content-Type`; [delSp] dal
 * parametro `delsp`, che per le lingue europee e' quasi sempre assente.
 */
data class FormatoCorpo(val flowed: Boolean, val delSp: Boolean)

/**
 * Legge `format` e `delsp` dal `Content-Type` fra le intestazioni grezze.
 *
 * Tollerante come dev'essere un lettore di Usenet: nome dell'intestazione e
 * valori senza distinzione di maiuscole, virgolette facoltative, parametri in
 * qualunque ordine. Un'intestazione assente o incomprensibile vale
 * «non flowed», che e' il comportamento di prima — un messaggio che non dichiara
 * niente continua a passare per l'euristica.
 */
fun formatoCorpo(headersRaw: String?): FormatoCorpo {
    val h = headersRaw ?: return FormatoCorpo(false, false)
    // Le intestazioni si possono spezzare su piu' righe (RFC 5322 folding): le
    // continuazioni cominciano con spazio o tab e vanno riattaccate, altrimenti
    // un `Content-Type` andato a capo prima di `format=flowed` sembra non averlo.
    val unite = StringBuilder()
    for (riga in h.lines()) {
        if (riga.startsWith(" ") || riga.startsWith("\t")) {
            unite.append(' ').append(riga.trim())
        } else {
            unite.append('\n').append(riga)
        }
    }
    val ct = unite.toString().lines()
        .firstOrNull { it.trimStart().lowercase().startsWith("content-type:") }
        ?: return FormatoCorpo(false, false)
    fun parametro(nome: String): String? =
        Regex(""";\s*$nome\s*=\s*"?([^";\s]+)"?""", RegexOption.IGNORE_CASE).find(ct)?.groupValues?.get(1)
    return FormatoCorpo(
        flowed = parametro("format")?.equals("flowed", ignoreCase = true) == true,
        delSp = parametro("delsp")?.equals("yes", ignoreCase = true) == true,
    )
}

/** Il numero di `>` in testa alla riga: la «quote-depth» della RFC 3676 §4.5. */
fun profonditaCitazione(riga: String): Int {
    var i = 0
    while (i < riga.length && riga[i] == '>') i++
    return i
}

/** Una riga letta secondo la RFC: profondita', contenuto vero, e se continua. */
private class RigaFlowed(val prof: Int, val contenuto: String, val continua: Boolean)

/**
 * Legge una riga nell'ordine che la RFC prescrive, e restituisce `null` se e'
 * il separatore di firma — che non e' ne' fisso ne' flowed (§4.3).
 *
 * UN SOLO POSTO. L'ordine dei tre passi — firma, `>`, spazio di protezione, e
 * poi di nuovo firma — e' gia' stato sbagliato una volta: scritto due volte
 * sarebbe tornato a divergere.
 */
private fun analizzaRiga(riga: String): RigaFlowed? {
    // §4.3: la firma si cerca PRIMA del test sulla citazione...
    if (riga == SEPARATORE_FIRMA || riga.trimEnd() == "--") return null
    val prof = profonditaCitazione(riga)
    var resto = riga.substring(prof)
    // §4.4: lo spazio di protezione se ne va PRIMA di ogni altro esame
    if (resto.startsWith(" ")) resto = resto.substring(1)
    // §4.3: ...e di nuovo dopo, cosi' anche un `> -- ` citato chiude il capoverso
    if (resto == SEPARATORE_FIRMA || resto.trimEnd() == "--") return null
    return RigaFlowed(prof, resto, resto.endsWith(" "))
}

/**
 * 1.4.6 (§B6a-bis) — DICHIARARE IL FORMATO NON VUOL DIRE USARLO.
 *
 * ⚠️ IL DIFETTO CHE CHIUDE, ed era mio, trovato da Marco sul dispositivo il
 * 1 ottobre 2026. MesNews manda `Content-Type: text/plain; charset="iso-8859-15";
 * format=flowed` e poi impagina **con a-capo rigidi**: nessuna riga finisce con
 * uno spazio. Per la norma e' legittimo — un corpo in cui tutte le righe sono
 * «fisse» e' Format=Flowed valido — ma e' f=f che non porta nessuna
 * informazione.
 *
 * Io avevo spento l'euristica di riflusso per TUTTI i messaggi che dichiarano il
 * formato, dando per scontato che chi lo dichiara lo usi. Su quei messaggi il
 * risultato era peggiore di prima: capoversi frastagliati dove prima venivano
 * ricomposti a naso. **Avevo sostituito una stima con un dato senza chiedermi
 * cosa fare quando il dato e' dichiarato ma vuoto.**
 *
 * LA REGOLA: il percorso esatto si usa solo se c'e' davvero qualcosa da leggere,
 * cioe' se ALMENO UNA riga continua. Altrimenti non sappiamo piu' di prima, e si
 * torna all'euristica — che per quei messaggi funzionava.
 *
 * *Vale oltre questo caso: una dichiarazione e' una promessa, non una prova. Un
 * formato annunciato e non usato lascia chi legge esattamente dove stava, e
 * trattarlo come informazione e' peggio che ignorarlo — perche' fa buttare via
 * quel poco che si aveva.*
 */
fun usaFlowed(formato: FormatoCorpo, righe: List<String>): Boolean =
    formato.flowed && almenoUnaRigaContinua(righe)

/**
 * 1.4.8 (par. B6b) — C'E' ALMENO UNA RIGA CHE CONTINUA?
 *
 * Estratta da [usaFlowed] perche' serve anche all'altro capo: al momento di
 * SPEDIRE, per decidere se dichiarare `format=flowed` nel `Content-Type`.
 *
 * ⚠️ E' LO STESSO PREDICATO, di proposito, non una copia. La 1.4.6 ha pagato
 * MesNews che dichiara il formato e poi impagina con a-capo rigidi (vedi la
 * nota di [usaFlowed]); usare qui la stessa funzione che decide in lettura
 * rende impossibile fare a nostra volta la stessa cosa: **non possiamo
 * dichiarare un formato che il nostro stesso lettore non userebbe.** Il
 * vincolo e' per costruzione, non per manutenzione.
 */
fun almenoUnaRigaContinua(righe: List<String>): Boolean =
    righe.any { analizzaRiga(it)?.continua == true }

/**
 * Ricompone i capoversi di un corpo `format=flowed`, secondo la RFC 3676.
 *
 * Restituisce le righe LOGICHE: ogni capoverso su una riga sola, col suo
 * prefisso di citazione. Chi disegna le manda a capo come gia' fa per il testo
 * normale — qui si ricostruisce il testo, non si impagina.
 *
 * Le quattro regole, nell'ordine in cui la RFC le prescrive:
 *
 * 1. **La firma si riconosce due volte** (§4.3). Il separatore `"-- "` va
 *    cercato PRIMA del test sulla citazione *e* di nuovo DOPO aver contato e
 *    tolto i `>`, cosi' anche un `> -- ` citato chiude il capoverso. La RFC e'
 *    esplicita: quella riga «is neither fixed nor flowed» — non continua mai,
 *    nonostante finisca con uno spazio.
 *
 * 2. **Si contano e si tolgono i `>`** (§4.5), e la profondita' ottenuta e'
 *    quella del capoverso.
 *
 * 3. **Lo spazio di protezione si toglie PRIMA di guardare se la riga
 *    continua** (§4.4: «this leading space is deleted before examining the line
 *    further, that is, before checking for flowed»). L'ordine non e' un
 *    dettaglio: una riga che contiene solo lo spazio di protezione diventa
 *    vuota, cioe' FISSA, mentre guardandola prima sembrerebbe continuare.
 *
 * 4. **Una riga che finisce con uno spazio continua; le altre no** (§4.1). E
 *    due righe di profondita' DIVERSA non si uniscono mai (§4.5): la RFC dice
 *    che chi riceve «SHOULD treat it as the last line of a paragraph», anche se
 *    quella riga continuava.
 *
 * [delSp] a `true` (dichiarato dal mittente) toglie lo spazio finale mentre
 * unisce; a `false` — il caso normale per le lingue europee — lo spazio resta e
 * fa da separatore fra le parole.
 */
fun srotolaFlowed(righe: List<String>, delSp: Boolean = false): List<String> {
    val out = ArrayList<String>()
    val acc = StringBuilder()
    var aperto = false
    var profAperta = 0

    fun chiudi() {
        if (aperto) {
            out.add(acc.toString())
            acc.setLength(0)
            aperto = false
        }
    }

    for (riga in righe) {
        // il separatore di firma esce tale e quale e chiude il capoverso (§4.3)
        val r = analizzaRiga(riga)
        if (r == null) { chiudi(); out.add(riga); continue }
        // profondita' diversa: il capoverso precedente finisce comunque qui (§4.5)
        if (aperto && r.prof != profAperta) chiudi()
        if (!aperto) {
            acc.append(">".repeat(r.prof))
            profAperta = r.prof
            aperto = true
        }
        acc.append(if (delSp && r.continua) r.contenuto.dropLast(1) else r.contenuto)
        if (!r.continua) chiudi()
    }
    chiudi()
    return out
}
