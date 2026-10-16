/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package org.openusenet.reader.ui

/*
 * 1.5 — PASSO 2 DEL PACCHETTO CITAZIONI: L'OGGETTO, E IL TESTO CHE NE ESCE.
 *
 * Richiesta di AMMAMMATA («Quote from another post»), allargata da BINGO3331
 * («quotare anche pezzi diversi da una discussione dello stesso gruppo»).
 *
 * LA REGOLA DI QUESTO PASSO, scritta nel documento di progetto prima che
 * esistesse una riga di codice:
 *
 *   > La B deve produrre un OGGETTO «citazione», NON una stringa.
 *
 * Perche' conta: il RACCOGLITORE (passo 4) tiene una LISTA di citazioni e le
 * formatta tutte in fila. Se la schermata «Cita...» scrivesse direttamente una
 * stringa negli appunti, il raccoglitore dovrebbe riaprirla e smontarla — ed e'
 * esattamente il modo in cui in questo progetto e' nata la sesta copia di una
 * logica (autenticazione pigra in quattro copie, appunti in cinque,
 * appartenenza al thread in cinque).
 *
 * Qui ci sono quindi DUE cose separate: un VALORE che descrive la citazione, e
 * una FUNZIONE che da quel valore produce il testo. Il raccoglitore si
 * aggiunge tenendo una lista del primo e chiamando la seconda, senza toccare
 * ne' la costruzione ne' la formattazione.
 *
 * ⚠️ NESSUN IMPORT, E NON E' UN VEZZO. Questo file e' Kotlin puro — modello
 * `ui/LinkArticolo.kt`, che e' nello stesso package e quindi si chiama senza
 * importare niente. Significa che si compila da solo con `kotlinc`, senza
 * Android e senza Gradle, e che si puo' PROVARE invece di crederci. Banco:
 * `controlli/citazioni/prova_citazioni.sh`. *Se il programma si puo' eseguire,
 * si esegue* — nella 1.4.11 due difetti su due li ha trovati l'esecuzione, e
 * zero la lettura.
 *
 * ⚠️ L'INTESTAZIONE NON SI INVENTA QUI. E' `introLine` dell'identita' in uso
 * (`ui/screens/ComposeLogic.kt`), con tutti i suoi segnaposto compreso
 * `%oggetto%` aggiunto nella 1.4.5. **Un modello solo in tutta l'app**: chi ha
 * personalizzato la propria introduzione se la ritrova anche qui. Per questo
 * [Citazione] porta l'introduzione GIA' ESPANSA e non gli ingredienti per
 * ricostruirla.
 */

/** Prefisso di citazione, uno solo in tutta l'app. */
const val PREFISSO_CITAZIONE = "> "

/**
 * Una citazione presa da un messaggio, pronta per essere formattata — da sola
 * o insieme ad altre.
 *
 * ⚠️ PERCHE' [introduzione] E' GIA' ESPANSA, e non si tengono invece autore,
 * data e oggetto per ricostruirla al momento della formattazione. Tre ragioni,
 * e la terza e' quella che decide:
 *
 *  1. `introLine` **sorteggia** fra piu' modelli quando l'identita' ne ha piu'
 *     di uno. Ricostruirla a ogni formattazione vorrebbe dire che la stessa
 *     citazione cambia faccia ogni volta che la si guarda — e rende la
 *     formattazione non deterministica, cioe' non provabile.
 *  2. Una citazione raccolta e' una **fotografia**. Se l'utente cambia la
 *     propria introduzione domani, cio' che ha gia' raccolto non deve
 *     cambiargli sotto le mani.
 *  3. Il raccoglitore sopravvive alla chiusura dell'app in un file suo, e i
 *     messaggi da cui le citazioni vengono possono essere stati **cancellati
 *     dalla pulizia automatica** nel frattempo. Un valore che dipendesse
 *     dall'articolo originale sarebbe un valore che un giorno non si puo' piu'
 *     formattare.
 *
 * ⚠️ Compromesso dichiarato: cambiare il modello di introduzione NON riscrive
 * le citazioni gia' raccolte. E' voluto (punto 2), non dimenticato.
 *
 * [gruppo] e [oggetto] non servono alla formattazione — l'introduzione li
 * contiene gia' se il modello li nomina. Stanno qui per l'ELENCO del
 * raccoglitore, dove l'utente deve riconoscere che cosa ha messo da parte.
 */
data class Citazione(
    /** `introLine` gia' espansa al momento della cattura. Puo' essere vuota. */
    val introduzione: String,
    /** Le righe scelte, GREZZE: il prefisso lo mette [formattaCitazione]. */
    val righe: List<String>,
    /** Message-ID canonico, con le parentesi angolari. Vuoto = niente coda. */
    val messageId: String = "",
    /** Link permanente usenet.id. Vuoto = la coda porta il solo Message-ID. */
    val link: String = "",
    /** Solo per l'elenco del raccoglitore. */
    val gruppo: String = "",
    /** Solo per l'elenco del raccoglitore. */
    val oggetto: String = "",
)

/**
 * Costruisce una [Citazione] a partire dall'introduzione gia' espansa e dalle
 * righe scelte.
 *
 * Il Message-ID viene reso **canonico** e il link **derivato** da esso con le
 * funzioni che esistono dalla 1.3.6 in `LinkArticolo.kt`, invece di essere
 * passati separatamente: due dati che devono concordare non si fanno arrivare
 * da due strade.
 *
 * ⚠️ E' la differenza fra una citazione e una citazione **verificabile**: chi
 * legge puo' risalire all'originale invece di fidarsi. Non l'aveva chiesto
 * nessuno dei due, ed e' la ragione per cui sta qui e non in un'opzione.
 */
fun citazioneDa(
    introduzione: String,
    righe: List<String>,
    messageIdGrezzo: String? = null,
    gruppo: String = "",
    oggetto: String = "",
): Citazione = Citazione(
    introduzione = introduzione.trim(),
    righe = righe,
    messageId = messageIdCanonico(messageIdGrezzo),
    link = linkUsenetId(messageIdGrezzo),
    gruppo = gruppo,
    oggetto = oggetto,
)

/**
 * Mette il prefisso di citazione davanti a ogni riga.
 *
 * ⚠️ ESTRATTA, NON SCRITTA DA ZERO. Questa riga esisteva gia', dentro
 * `buildPrefill` (`ComposeLogic.kt`), come `corpoDaCitare.map { "> $it" }`.
 * Scriverne una seconda qui sarebbe stata la copia numero due di una logica, e
 * la 1.3 e' uscita con un difetto pubblico perche' una correzione era stata
 * applicata a due copie su quattro. `buildPrefill` adesso chiama questa.
 *
 * **Comportamento identico a prima, di proposito**, compreso il caso che segue.
 *
 * ⚠️ NOTA PER CHI PASSERA' DI QUI CON IL FORMAT=FLOWED IN TESTA: una riga
 * VUOTA diventa `"> "`, cioe' una riga che finisce con uno SPAZIO. Nel
 * Format=Flowed (RFC 3676) uno spazio in coda significa «la riga continua».
 * E' il comportamento che NeReO ha sempre avuto e non lo si cambia qui: con
 * l'emissione f=f spenta (predefinito) non ha effetto, e cambiarlo dentro il
 * passo 2 del pacchetto citazioni vorrebbe dire modificare TUTTE le risposte
 * dell'app dentro una voce che parla d'altro. *Annotato perche' non si perda,
 * non perche' si faccia adesso.*
 */
fun righeCitate(righe: List<String>, prefisso: String = PREFISSO_CITAZIONE): List<String> =
    righe.map { prefisso + it }

/**
 * Il testo di UNA citazione: introduzione, righe citate, e in coda il
 * Message-ID col link permanente.
 *
 * Forma (dal documento di progetto, §4):
 *
 * ```
 * In it.tlc.cellulari.android, il 14/09/2026 alle 10:27, Tizio ha scritto in «oggetto»:
 * > testo scelto
 * > ancora testo scelto
 * [<id@server> — https://usenet.id/id@server]
 * ```
 *
 * Ogni pezzo e' facoltativo e **la sua assenza non lascia righe vuote**:
 * introduzione vuota, nessuna riga scelta o Message-ID assente producono un
 * blocco piu' corto, mai un blocco con dei buchi.
 */
fun formattaCitazione(c: Citazione, prefisso: String = PREFISSO_CITAZIONE): String {
    val pezzi = ArrayList<String>(c.righe.size + 2)
    if (c.introduzione.isNotBlank()) pezzi.add(c.introduzione)
    pezzi.addAll(righeCitate(c.righe, prefisso))
    val coda = codaCitazione(c)
    if (coda.isNotEmpty()) pezzi.add(coda)
    return pezzi.joinToString("\n")
}

/**
 * La coda `[<message-id> — link]`, vuota se non c'e' un Message-ID.
 *
 * Con il solo Message-ID e nessun link la coda si accorcia a `[<id>]` invece
 * di portarsi dietro un trattino che non separa piu' niente.
 */
private fun codaCitazione(c: Citazione): String = when {
    c.messageId.isEmpty() -> ""
    c.link.isEmpty() -> "[" + c.messageId + "]"
    else -> "[" + c.messageId + " — " + c.link + "]"
}

/**
 * Il testo di PIU' citazioni in fila, separate da una riga vuota.
 *
 * E' tutto quello che il raccoglitore (passo 4) dovra' chiamare: nessuna
 * logica di formattazione gli appartiene, e nessuna delle due funzioni sopra
 * sa che esiste un raccoglitore. *E' la divisione che rende il passo 4
 * economico, ed e' l'unica ragione per cui il passo 2 esiste come passo a se'.*
 *
 * Le citazioni senza alcun contenuto (niente introduzione, niente righe,
 * niente Message-ID) non lasciano un buco nell'elenco.
 */
fun formattaCitazioni(
    citazioni: List<Citazione>,
    prefisso: String = PREFISSO_CITAZIONE,
): String = citazioni
    .map { formattaCitazione(it, prefisso) }
    .filter { it.isNotEmpty() }
    .joinToString("\n\n")

/*
 * 1.5 / 158 — PASSO 3, PIANO C: DALLA SELEZIONE ALLE RIGHE ORIGINALI.
 *
 * Compose 1.12 espone il testo selezionato (`SelectionState.selectedTexts`,
 * verificato sui metadati Kotlin dell'APK 157: PUBLIC). Ma quel testo e' il
 * testo MOSTRATO, non quello del messaggio, e non per caso — la lettura lo
 * rielabora di proposito (`ui/components/ArticleBody.kt`):
 *
 *  - stile «barre» (il predefinito): i `>` sono TOLTI e disegnati come barre;
 *  - stile «numeri»: `>>>>>>>>>` diventa `9 `;
 *  - Format=Flowed: le righe morbide sono RIUNITE in un capoverso;
 *  - citazioni lunghe accorciate, con una riga «N righe in piu'»;
 *  - citazioni silenziate, con un segnaposto al posto del testo.
 *
 * Citare il testo mostrato vorrebbe dire perdere i livelli `>` di cio' che era
 * gia' citato — l'esatto difetto che la modalita' «ridotta» esiste per evitare.
 *
 * ⚠️ QUINDI LA SELEZIONE SERVE SOLO A DIRE QUALI RIGHE. Si citano poi le righe
 * ORIGINALI, intere e coi loro `>`, come fa Rispondi da sempre. Una selezione
 * che parte a meta' riga si allarga alla riga intera: e' una scelta, non un
 * limite — si cita cio' che l'autore ha scritto, non un frammento ritagliato.
 */

/** Il testo ridotto all'osso per il confronto: niente spazi, niente `>`. */
private fun chiaveConfronto(s: String): String =
    s.filter { !it.isWhitespace() && it != '>' }

/**
 * Le righe ORIGINALI di [corpo] che la selezione [selezionato] copre, intere.
 *
 * Come: [corpo] viene ridotto a una sola stringa di confronto (senza spazi ne'
 * `>`) di cui si ricorda, carattere per carattere, la riga di provenienza. Poi
 * le righe della selezione vengono cercate IN ORDINE, ognuna dopo la
 * precedente; quelle che nell'originale non esistono (il segnaposto di una
 * citazione silenziata, «N righe in piu'») si saltano. La prima trovata dice
 * dove comincia la citazione, l'ultima dove finisce.
 *
 * Il numero del livello dello stile «numeri» (`9 testo`) non esiste
 * nell'originale: se una riga non si trova cosi' com'e', si riprova senza le
 * cifre iniziali.
 *
 * ⚠️ RIPIEGO DICHIARATO: se NIENTE della selezione si ritrova nell'originale,
 * si restituiscono le righe selezionate cosi' come sono. Succede solo se il
 * testo mostrato e' stato trasformato per intero — il ROT13 e' l'unico caso
 * noto, e il chiamante lo evita passando la selezione gia' riconvertita.
 * Meglio citare il testo visto che non citare niente.
 *
 * Le righe vuote in testa e in coda al risultato si tolgono: sono quasi sempre
 * il bordo di un capoverso preso per sbaglio, mai il contenuto.
 */
fun righeOriginaliDi(corpo: String, selezionato: String): List<String> {
    val righe = corpo.lines()
    val intervallo = intervalloOriginale(righe, selezionato)
    val scelte = if (intervallo == null) selezionato.lines() else righe.subList(intervallo.first, intervallo.last + 1)
    return scelte.dropWhile { it.isBlank() }.dropLastWhile { it.isBlank() }
}

/**
 * Gli indici (prima..ultima riga di [righe]) che la selezione copre, o null se
 * niente della selezione si ritrova. Vedi [righeOriginaliDi] per il come.
 */
private fun intervalloOriginale(righe: List<String>, selezionato: String): IntRange? {
    val confronto = StringBuilder()
    val provenienza = ArrayList<Int>()
    righe.forEachIndexed { i, r ->
        val k = chiaveConfronto(r)
        confronto.append(k)
        repeat(k.length) { provenienza.add(i) }
    }
    val testo = confronto.toString()

    var cursore = 0
    var prima = -1
    var ultima = -1
    for (riga in selezionato.lines()) {
        val k = chiaveConfronto(riga)
        if (k.isEmpty()) continue
        var dove = testo.indexOf(k, cursore)
        var lunghezza = k.length
        if (dove < 0) {
            val senzaNumero = k.dropWhile { it.isDigit() }
            if (senzaNumero.isNotEmpty() && senzaNumero.length < k.length) {
                dove = testo.indexOf(senzaNumero, cursore)
                lunghezza = senzaNumero.length
            }
        }
        if (dove < 0) continue
        if (prima < 0) prima = provenienza[dove]
        ultima = provenienza[dove + lunghezza - 1]
        cursore = dove + lunghezza
    }
    return if (prima < 0) null else prima..ultima
}

/*
 * 1.5 / 159 — PIANO D: RI-RADICARE UNA CITAZIONE PRESA DENTRO UNA CITAZIONE.
 *
 * IL DIFETTO, trovato dal collaudo di Marco sulla 158 (post di Roberto Deboni
 * DMIsr in una discussione a sei livelli): selezionando righe che nel messaggio
 * erano GIA' citate, l'introduzione nominava l'autore del messaggio aperto —
 * «Roberto ha scritto:» — mentre il testo l'aveva scritto Giangi, sei livelli
 * piu' in basso. La forma era giusta (`> >>>>>>`), il senso sbagliato: chi
 * legge attribuisce a Roberto parole di Giangi.
 *
 * Il RIMEDIO scelto da Marco (piano D): se tutte le righe scelte stanno almeno
 * al livello N >= 1, si cerca sopra di loro la riga di ATTRIBUZIONE del livello
 * N-1 che le introduce («Il 05/10/2026 14:30, Giangi ha scritto:»), la si usa
 * COME INTRODUZIONE, e si tolgono gli N livelli in piu'. La citazione torna a
 * dire chi ha scritto cosa.
 *
 * Piani scartati: A (portarsi dietro la catena intera: corretta ma, in quel
 * post, sette `>` davanti a ogni riga); B (risalire References fino al
 * messaggio originale: il migliore, ma solo se e' ancora in archivio — si
 * aggiungera' DAVANTI a questo, senza toccarlo).
 *
 * ⚠️ LIMITI DICHIARATI:
 *  - l'introduzione e' scritta dal newsreader dell'AUTORE, non col modello
 *    dell'utente: puo' essere in inglese («On ..., X wrote:»);
 *  - il link in coda resta quello del messaggio in cui la citazione SI TROVA,
 *    non dell'originale (quello e' il piano B);
 *  - se l'attribuzione non c'e' o non e' riconoscibile, si torna al
 *    comportamento della 158: forma corretta, introduzione dell'autore del
 *    messaggio aperto.
 */

/**
 * La riga sembra un'attribuzione («Tizio ha scritto:», «On ..., X wrote:»)?
 *
 * ⚠️ ESTRATTA, NON RISCRITTA: era dentro `buildPrefill` (modalita' «ridotta»,
 * beta21, Roxy) in DUE copie con due elenchi di marcatori identici. Adesso le
 * due copie chiamano questa, e «Cita» e' il terzo chiamante invece della terza
 * copia. Regola invariata: finisce con `:`, oppure contiene uno dei marcatori
 * tipici in italiano o inglese.
 */
fun sembraAttribuzione(riga: String): Boolean {
    val r = riga.trim()
    return r.endsWith(":") || MARCATORI_ATTRIBUZIONE.any { r.contains(it, ignoreCase = true) }
}

private val MARCATORI_ATTRIBUZIONE =
    listOf("ha scritto", "wrote", "scriveva", "ha detto", "said", "writes")

/**
 * La riga (gia' senza `>`) e' un SEGNO DI TAGLIO messo da chi ha accorciato
 * una citazione? Le forme in uso su Usenet: le forbici `8<` / `8><` / `>8`, i
 * puntini fra parentesi, `[snip]` e simili, o una riga di soli segni
 * (trattini, puntini...) senza lettere ne' cifre.
 */
fun sembraTaglio(testo: String): Boolean {
    val t = testo.trim().lowercase()
    if (t.isEmpty()) return true
    if (FORBICI.any { it in t }) return true
    if (t in PAROLE_TAGLIO) return true
    return t.none { it.isLetterOrDigit() }
}

private val FORBICI = listOf("8<", "8><", ">8", "><8", "✂")
private val PAROLE_TAGLIO = setOf(
    "[...]", "[…]", "(...)", "(…)", "...", "…", "[snip]", "<snip>", "(snip)", "snip",
    "[cut]", "<cut>", "[taglio]", "[taglia]", "[omissis]", "[omissis...]",
)

/** Quanti `>` stanno in testa alla riga, contando anche la forma `> > >`. */
fun livelloCitazione(riga: String): Int {
    var n = 0
    for (c in riga) {
        when (c) {
            '>' -> n++
            ' ', '\t' -> {}
            else -> return n
        }
    }
    return n
}

/** Toglie i primi [n] `>` (e gli spazi fra loro), piu' UNO spazio dopo. */
private fun togliLivelli(riga: String, n: Int): String {
    if (n <= 0) return riga
    var tolti = 0
    var i = 0
    while (i < riga.length && tolti < n) {
        when (riga[i]) {
            '>' -> tolti++
            ' ', '\t' -> {}
            else -> return riga.substring(i)
        }
        i++
    }
    return if (i < riga.length && riga[i] == ' ') riga.substring(i + 1) else riga.substring(i)
}

/**
 * Il risultato di [riradica]: le righe da citare e, se il testo era di un
 * altro, l'introduzione da usare al posto di quella dell'utente.
 */
data class CitazioneRiradicata(
    /** null = usare l'introduzione dell'identita' (`introLine`), come prima. */
    val introduzione: String?,
    val righe: List<String>,
)

/**
 * Come [righeOriginaliDi], ma se TUTTE le righe scelte sono citate a livello N
 * almeno 1, cerca l'attribuzione che le introduce e ri-radica la citazione su
 * di essa. Vedi il commento del piano D qui sopra.
 *
 * L'attribuzione si cerca SALENDO dalla prima riga scelta. Si scavalcano:
 *  - le righe senza testo, A QUALUNQUE LIVELLO (`>>>>` vuota compresa);
 *  - le righe di livello >= N (lo stesso blocco, o un blocco piu' vecchio);
 *  - i SEGNI DI TAGLIO a qualunque livello ([sembraTaglio]).
 * La prima riga con del testo vero decide: se sta al livello N-1 e sembra
 * un'attribuzione, e' lei; altrimenti la ricerca si ferma.
 *
 * ⚠️ 1.5 / 160 — PERCHE' I TAGLI SI SCAVALCANO A QUALUNQUE LIVELLO. Il post
 * VERO di Roberto (testo grezzo dato da Marco il 12/10) ha, fra
 * l'attribuzione di Giangi (livello 5) e il testo (livello 6), un
 * `>>>> 8>< ----` al livello 4: e' il taglio che ROBERTO aveva fatto il 07/10
 * nel messaggio di Delacroix, quindi sta al livello di chi ha tagliato, piu'
 * BASSO del blocco. La 159 lo prendeva per l'uscita dal blocco e si fermava.
 * La prova del banco passava perche' il caso era stato ricostruito da uno
 * SCREENSHOT, cioe' dal testo mostrato, col taglio messo al livello 6.
 * *Il testo mostrato non e' il testo: la stessa lezione da cui questa
 * funzione e' nata, ripetuta sul suo banco di prova.*
 */
fun riradica(corpo: String, selezionato: String): CitazioneRiradicata {
    val righe = corpo.lines()
    val intervallo = intervalloOriginale(righe, selezionato)
        ?: return CitazioneRiradicata(null, righeOriginaliDi(corpo, selezionato))
    var prima = intervallo.first
    var ultima = intervallo.last
    while (prima <= ultima && righe[prima].isBlank()) prima++
    while (ultima >= prima && righe[ultima].isBlank()) ultima--
    if (prima > ultima) return CitazioneRiradicata(null, emptyList())
    val scelte = righe.subList(prima, ultima + 1)

    // il livello si misura sulle righe con del TESTO: una `>>>>` vuota o un
    // segno di taglio dentro la selezione sono scorie di chi ha citato, non
    // dicono chi ha scritto (caso vero di Roberto, taglio al livello 4).
    val n = scelte
        .filter { val lv = livelloCitazione(it); togliLivelli(it, lv).isNotBlank() && !sembraTaglio(togliLivelli(it, lv)) }
        .map { livelloCitazione(it) }.minOrNull() ?: 0
    if (n == 0) return CitazioneRiradicata(null, scelte)

    var j = prima - 1
    var attribuzione: String? = null
    while (j >= 0) {
        val r = righe[j]
        val lv = livelloCitazione(r)
        val testo = togliLivelli(r, lv).trim()
        if (testo.isEmpty() || lv >= n || sembraTaglio(testo)) { j--; continue }
        if (lv == n - 1 && sembraAttribuzione(testo)) attribuzione = testo
        break
    }
    if (attribuzione == null) return CitazioneRiradicata(null, scelte)
    return CitazioneRiradicata(attribuzione, scelte.map { togliLivelli(it, n) })
}

/*
 * 1.5.4 — «CITA E RISPONDI»: LA CITAZIONE CHE ASPETTA LA RISPOSTA.
 *
 * Richiesta di un utente (via Marco, 15 ottobre 2026): «una volta selezionato
 * un testo in un post vorrei avere il comando "cita e rispondi" che mi prepara
 * il nuovo post». Fino alla 1.5.3 servivano cinque passi: Cita, Rispondi,
 * «Senza citazione», tenere premuto, Incolla.
 *
 * La voce del menu di selezione prepara il testo e apre la risposta; la
 * schermata di scrittura (le DUE: a pagina intera e nel pannello) lo prende
 * da qui. Non passa dalla rotta di navigazione: un testo arbitrario in un
 * indirizzo va codificato e ha un limite di lunghezza, e il pannello non
 * naviga affatto.
 *
 * ⚠️ PRESA UNA VOLTA SOLA, E CON UNA SCADENZA. Se fra la voce e la risposta
 * l'utente annulla (il dialogo del crosspost nei pannelli), la citazione non
 * deve comparire dieci minuti dopo in una risposta normale allo stesso
 * messaggio. Due minuti bastano per qualunque dialogo, e la chiave e' l'id
 * del messaggio: una citazione non finisce mai nella risposta a un altro.
 *
 * Vive in memoria: se Android chiude l'app in mezzo, la risposta si apre con
 * la citazione normale. E' un ripiego accettabile, non una perdita di dati.
 */
object CitazioneInAttesa {
    const val VALIDITA_MS = 2 * 60 * 1000L

    private var articoloId: Long = -1
    private var testo: String? = null
    private var quando: Long = 0

    fun metti(idArticolo: Long, citazione: String, ora: Long = System.currentTimeMillis()) {
        articoloId = idArticolo
        testo = citazione
        quando = ora
    }

    /** La citazione per [idArticolo], se c'e' e non e' scaduta. La consuma. */
    fun prendi(idArticolo: Long, ora: Long = System.currentTimeMillis()): String? {
        val t = testo ?: return null
        if (idArticolo != articoloId || ora - quando > VALIDITA_MS || ora < quando) return null
        testo = null
        return t
    }
}
