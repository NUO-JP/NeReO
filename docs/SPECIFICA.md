# NeReO — Specifica di progetto

> Nome visibile attuale: **NeReO**, acronimo di **Ne**(ws) **Re**(ader) **O**(pen-source).
> Package e applicationId restano `org.openusenet.reader`.

> **Stato di questo documento (7 settembre 2026).** È il documento di
> *progettazione e storia* del progetto: architettura, scelte di fondo e diario
> di sviluppo. Il racconto per versioni si ferma alla **beta11** (§7) e non è
> stato proseguito: da lì in poi la cronaca delle versioni sta nei tre README e
> nelle note di rilascio dentro l'app. Non è un documento obsoleto — le sezioni
> 1-6 (obiettivi, architettura, note tecniche) valgono tuttora — ma **non
> leggerlo come stato corrente**: la versione corrente è la **1.3**.

Newsreader Usenet **solo testo** per Android, open source (GPLv3), scritto da zero in Kotlin + Jetpack Compose. Base di riferimento funzionale: Piao Hong Usenet Client 2.02 (mod M17), analizzato per estrarne l'inventario delle feature.

## 1. Obiettivi e non-obiettivi

Obiettivo: un lettore di newsgroup testuali moderno, manutenibile e libero, con parità funzionale rispetto all'app di riferimento e un motore filtri nettamente superiore. Non-obiettivi espliciti: niente gruppi binari, niente decodifica yEnc/multipart di allegati binari (l'app di riferimento la aveva; qui è esclusa per scelta), niente pubblicità o telemetria.

## 2. Architettura

Pensala come un impianto ben progettato: la pompa (client NNTP) è separata dal serbatoio (database Room), e la centralina (repository) decide i flussi. La UI è solo il pannello di controllo.

- `nntp/` — client NNTP puro Kotlin/JVM (RFC 3977, AUTHINFO RFC 4643, OVER/XOVER), zero dipendenze Android, testabile su JVM. Include `MimeUtil` (RFC 2047, quoted-printable, base64, charset).
- `data/` — entità Room (Server, Group, Article, FilterRuleEntity), DAO, `NewsRepository` che orchestra sync incrementale, cache corpi, sottoscrizioni.
- `filter/` — `FilterEngine` a scoring + `KillFile` (import/export testuale).
- `thread/` — `Threader`, algoritmo JWZ semplificato su References.
- `ui/` — Compose Material 3: Groups, Threads, Article, Servers, Filters.

## 3. Parità funzionale con l'app di riferimento

Stato nella v0.1 (questo scaffold): ✅ incluso, 🔜 pianificato, ✖ escluso per scelta.

| Area | Feature originale | Stato |
|---|---|---|
| Server | Multi-server, SSL, login, porta | ✅ |
| Server | Ordinamento drag & drop | 🔜 v1.1 |
| Gruppi | Lista completa dal server, ricerca, sottoscrizione | ✅ |
| Headline | Fetch con limite, sync incrementale, auto-fetch all'ingresso | ✅ |
| Thread | Vista ad albero con indentazione | ✅ |
| Thread | Viste alternative (sequenziale, flusso risposte, singoli) | 🔜 v1.1 |
| Thread | Watched first / unread / orphan | ✅ (watched first, filtro non letti in v0.4, orfani come radici) |
| Lettura | Charset, RFC 2047, quoted-printable, base64 testo | ✅ |
| Lettura | Font monospaced, temi chiaro/scuro | ✅ (tema di sistema + dynamic color) |
| Lettura | Smart merge righe, swipe navigation, tema a fasce orarie | 🔜 v1.2 |
| Lettura | GBK↔BIG5 | ✖ (retaggio; charset generico basta) |
| Posting | Post/reply, 7 modalità quoting, firma, header custom, X-No-Archive | ✅ base in v0.4: nuovo thread + risposta con quote "> ", taglio firma, References corretti, identità salvata. ✅ v0.5: schermata "Identità e firma" con firma in uscita (delimitatore "-- ", attivabile/disattivabile, modificabile in composizione). Modalità quoting multiple e header custom → v1.1 |
| Allegati | Immagini camera/galleria, yEnc, multi-decode | ✖ (app solo testo) |
| Manutenzione | Cancella vecchi articoli, cache, backup/restore | 🔜 v1.1 (DAO `deleteOlderThan` già pronto) |
| Rete mobile | Limiti sync su rete mobile | 🔜 v1.2 |

## 4. Motore filtri (il salto di qualità)

L'originale: 3 campi "contains" (From, Subject, Xpost) e 4 azioni fisse. Il nostro: **scoring alla slrn/Gnus**.

Ogni regola: campo (From, Subject, Message-ID, Xpost, Body) × tipo match (contains, wildcard, regex) × score (±) × scope wildmat (`*`, `it.*`, gruppo esatto) × scadenza opzionale × case sensitivity. Le regole si sommano; le soglie decidono l'azione:

| Soglia | Default | Azione |
|---|---|---|
| kill | ≤ −9999 | scartato in sync, mai salvato |
| hide | ≤ −100 | nascosto, recuperabile con "mostra tutto" |
| highlight | ≥ +100 | evidenziato in lista |
| watch | ≥ +9999 | marcato watched, thread in testa |

Kill-file testuale import/export (formato slrn-like documentato in `KillFile.kt`): versionabile, condivisibile, editabile a mano. Un regex invalido rende la regola inerte, mai un crash.

## 4b. Novità v0.2 (feedback dal primo collaudo)

- Modifica server: tocco sulla riga (o icona matita) apre il dialog di modifica. Principio generale adottato: **ogni entità creata dall'utente deve essere modificabile**, mai solo cancellabile.
- Directory gruppi: al primo ingresso l'elenco completo del server (LIST ACTIVE) viene scaricato e messo in cache locale (`available_groups`); da lì la ricerca è offline e istantanea, con i match di prefisso in testa (digitando `it.tlc.c` compare subito la gerarchia `it.tlc.cellulari.*`). Pulsante refresh per riscaricare l'elenco.
- Riferimento UI dichiarato dal committente: **MesNews** (mesnews.net) — newsreader freeware classico: multi-server, multi-forum, layout a tre pannelli (gruppi | lista thread | corpo messaggio), personalizzazione dell'aspetto dei messaggi (colori/stili/font per sezione: quote, firma, header), gestione per-gruppo. Da MesNews prendiamo l'impostazione UX, non l'HTML nei messaggi (restiamo solo testo). Orienterà le viste della v1.x: tre pannelli su tablet/landscape, personalizzazione dell'aspetto del messaggio in lettura (colori distinti per livelli di quoting, firma, header).

## 4c. Novità v0.6 (ispirazione MesNews + richieste committente)

- **PLONK**: pulsante nel lettore articolo → l'autore finisce nel killfile (score −10000), i suoi articoli futuri non vengono scaricati e quelli esistenti vengono nascosti. Reversibile dalla gestione filtri (la regola si chiama "Plonk: <autore>").
- **Privacy posting**: User-Agent libero (vuoto = nessuna dichiarazione, o qualunque stringa si voglia); X-No-Archive: yes opzionale. Entrambi in "Identità e firma" → sezione Privacy.
- **Aspetto**: nuova schermata — tema Sistema/Chiaro/Scuro, Material You (Android 12+), 6 colori accento. Applicazione immediata senza riavvio.
- **Citazioni colorate alla MesNews**: in lettura i livelli di quote (>, >>, >>>) hanno colori distinti derivati dall'accento; la firma è attenuata.

## 4d. Novità v0.7

- **Modalità di quoting multiple** (default memorizzato): scrivi sotto la citazione (netiquette), scrivi sopra (top-posting), citazione ridotta (prime 6 righe + [...]), senza citazione. Il cursore viene posizionato automaticamente nel punto giusto per ciascuna modalità; la firma del citato è sempre tagliata al "-- ".
- **Segna tutto letto dal livello gruppi**: icona ✓✓ su ogni riga della schermata principale (attiva solo se ci sono non letti), oltre a quella già presente dentro al gruppo.
- **Default "solo thread con non letti"**: entrando in un gruppo si vedono solo i thread che contengono almeno un messaggio non letto — il thread resta però visibile per intero (i messaggi letti fanno da contesto). L'icona busta commuta sulla vista completa.

## 4e. Novità v0.8 (hardening + tre pannelli)

Due filoni: **Piano A** (consolidamento) e **Piano C** (salto UX verso MesNews).

Piano A — hardening:
- **Charset corretto end-to-end.** Il client NNTP ora legge socket, corpi e overview come BYTE grezzi; il charset viene deciso a valle (`MimeUtil.decodeTextBodyRaw`) dal `Content-Type` dichiarato, con euristica UTF-8→ISO-8859-15 per gli 8-bit non codificati dei gruppi legacy (es. `it.*`). Prima gli accenti Latin-9 si corrompevano perché il socket veniva letto UTF-8 fisso.
- **Password cifrate.** Le password server non sono più in chiaro nel DB: `KeystoreCipher` (Android Keystore, AES-256/GCM) le cifra col marcatore `enc:1:`. Migrazione trasparente (un valore legacy in chiaro viene usato e ricifrato al primo salvataggio) e cifratura idempotente. Il core resta testabile su JVM grazie all'interfaccia `PasswordCipher` (+ `NoOp`).
- **Gradle wrapper** incluso (`gradlew`, `gradlew.bat`, `gradle-wrapper.properties`, Gradle 8.9). Nota: il binario `gradle-wrapper.jar` va materializzato una volta dal primo sync di Android Studio o con `gradle wrapper --gradle-version 8.9` (non distribuibile offline).

Piano C — verso MesNews:
- **Layout a tre pannelli** (gruppi | thread | articolo) automatico su schermi larghi (≥ 840 dp: landscape/tablet), tramite `ThreePaneHome`. Il flusso a pagine per telefono resta invariato; le schermate di servizio continuano a usare la navigazione normale. Selezione di gruppo/articolo come stato locale, sync automatico all'ingresso nel gruppo, threading e corpo condivisi con la vista a pagine.
- **Personalizzazione aspetto messaggio**: in "Aspetto" → sezione "Lettura articoli", interruttore monospazio e scala testo (Compatta/Normale/Grande/Molto grande), applicati sia in ArticleScreen sia nel pannello destro. I livelli di quote restano colorati per profondità e la firma attenuata.

## 4f. Novità v0.9 (Piano B + tre pannelli, secondo passo)

Piano B — gestione e manutenzione:
- **Backup/ripristino configurazione** (schermata "Manutenzione e backup", icona archivio nella home). Esporta/importa server, filtri, impostazioni di aspetto e identità in un file JSON tramite il selettore file di sistema (`BackupManager`, `org.json`, nessuna dipendenza nuova). Le password NON vengono esportate (sono legate al Keystore del dispositivo): si reinseriscono dopo il ripristino. Il ripristino aggiunge le voci a quelle esistenti.
- **Housekeeping automatico**: opzione "Pulizia automatica" (Mai / 30 / 90 / 180 / 365 giorni). Dopo ogni sync elimina i messaggi **già letti** più vecchi della soglia, preservando sempre non letti e thread watched (nuova query `deleteReadOlderThan`). Pulsante "Pulisci adesso" per l'esecuzione manuale.

Piano C — secondo passo:
- **Composizione in-pannello**: nel layout a tre pannelli, "Nuovo thread" (icona matita sull'intestazione della colonna thread) e "Rispondi" (nel riquadro articolo) aprono la composizione **nel riquadro destro**, senza cambiare schermata. La logica di quoting/firma è ora condivisa (`ComposeLogic.kt`) tra la composizione a pagina e quella in-pannello: un'unica fonte di verità.
- **Indentazione visiva dei thread**: nel pannello centrale i livelli di risposta hanno rotaie verticali (stile albero) oltre al rientro, per leggere subito la gerarchia.

## 4g. Novità v0.10 (retention per gruppo + viste thread)

- **Housekeeping per gruppo.** Ogni gruppo può avere una propria soglia di conservazione (menù ⋮ → "Conservazione di questo gruppo": Usa predefinito / Mai / 30 / 90 / 180 / 365 giorni). Se impostata, dopo il sync elimina i letti più vecchi solo di quel gruppo; altrimenti vale il valore globale. Sempre preservati non letti e watched. Richiede una colonna nuova (`Group.purgeDays`): **prima migrazione Room vera** (v2→v3, `ALTER TABLE`), i dati esistenti non vengono azzerati.
- **Viste thread alternative** (menù ⋮ → "Vista", ricordata come predefinita): *Ad albero* (JWZ indentato), *Sequenziale* (tutti i messaggi per data, senza rientro), *Solo discussioni* (una riga per thread con il numero di risposte). Valgono sia sul telefono sia nel layout a tre pannelli.
- **Separazione grafica tra thread.** In vista ad albero (e tra le discussioni nelle altre viste) una linea divisoria segna l'inizio di ogni nuovo thread, così si distingue a colpo d'occhio dove finisce una discussione e ne comincia un'altra.

## 4h. Novità v0.11 (link, sfondo lettura, colori citazione)

- **URL cliccabili**: nel corpo del messaggio gli indirizzi web (http/https e www.) diventano link; toccandoli si apre il browser di sistema (via `LinkAnnotation.Url` + gestore URI di Compose). La punteggiatura finale non viene inglobata nel link.
- **Sfondo di lettura configurabile** (Aspetto → "Sfondo lettura"): Predefinito (tema), Sepia, Crema, Grigio, Nero. Ogni preset porta con sé un colore testo abbinato per garantire il contrasto; si applica alla lettura sia a pagina sia nel pannello del layout a tre pannelli.
- **Colori dei livelli di citazione ripristinati e resi distinti**: non dipendono più da primary/tertiary/secondary del tema (che con Material You risultavano tinte troppo simili), ma da una palette dedicata a gradini (verde/blu/arancio/viola/teal), con due varianti chiaro/scuro scelte in base alla luminosità dello sfondo di lettura. La firma resta attenuata.

## 4i. Novità v0.12 (backup completo: sottoscrizioni e password)

Correzione di un buco importante del backup precedente, che non salvava le sottoscrizioni né le password: dopo un ripristino "vero" (reinstallazione) restavano un server senza password e senza gruppi.

- **Sottoscrizioni nel backup.** Ora l'export include i gruppi sottoscritti e il ripristino li riaggancia al server giusto (chiave host:porta) tramite `subscribe()`.
- **Nessun server duplicato al ripristino.** Se esiste già un server con stesso host:porta viene aggiornato (mantenendo id e, se il backup non porta password, quella esistente) invece di crearne un altro (`restoreServer` + `ServerDao.byHostPort`).
- **Password opzionali nel backup.** Casella "Includi le password" (default OFF, con avviso): se attiva, il file le contiene in chiaro e il ripristino le rimette (poi ricifrate nel Keystore). Se disattiva, restano fuori come prima e vanno reinserite.
- Backup format v2; i backup v1 restano importabili (i campi mancanti vengono semplicemente ignorati). Nel backup ora entrano anche `thread_view` e `reading_bg`.

## 4j. Novità v0.13 (backup cifrato + plonk per contenuto)

- **Backup protetto da password.** In Manutenzione è possibile impostare una "Password del backup": con essa il file viene CIFRATO (PBKDF2WithHmacSHA256 + AES-256/GCM, `BackupCrypto`) e include anche le password dei server; al ripristino l'app chiede quella password e decifra. Senza password il file resta in chiaro ma non contiene le password dei server (nessuna esposizione). Nessun recupero se la password si dimentica (nessuna backdoor).
- **Plonk per contenuto (non legato al nick).** Nuovo uso del campo filtro "corpo": se il testo di un messaggio contiene la parola/frase indicata, l'autore viene automaticamente messo nel killfile e i suoi messaggi futuri bloccati. Il controllo avviene alla lettura del messaggio (quando il corpo viene scaricato). Utile per bloccare chi scrive certe cose a prescindere dal nick. Il `plonkAuthor` è ora idempotente (niente regole duplicate).

## 4k. Novità v0.14 (fix filtri + password backup + rinomina)

- **Fix filtri (bug):** i chip di scelta campo (da/oggetto/id/crosspost/corpo) stavano in una riga non a capo e su schermo stretto "corpo" e "crosspost" finivano fuori vista. Ora usano FlowRow e vanno a capo: il campo **corpo** (auto-plonk per contenuto) è finalmente visibile e selezionabile.
- **Password del backup richiesta all'esportazione:** toccando "Esporta" ora compare un dialog che chiede la password (vuota = backup in chiaro senza password server; impostata = file cifrato che include le password). Prima era un campo facile da non notare.
- **Nome visibile rinominato in "MyNewsReader"** (poi **NeReO** dalla beta7) (barra del titolo, nome app, User-Agent dichiarato nei post). Il package interno `org.openusenet.reader` resta invariato di proposito: cambiarlo trasformerebbe l'app in un'altra installazione, con perdita di dati e sottoscrizioni.
- Promemoria backup: il ripristino delle **sottoscrizioni** richiede un backup creato con v0.12+; i file esportati con versioni precedenti non le contengono (il messaggio di ripristino indica quanti gruppi sono stati importati).

## 4l. Novità v0.15 (fix: password persa dopo reinstallazione)

Causa del "non scarica più l'elenco gruppi": le password server sono cifrate con l'Android Keystore (chiave legata all'installazione, non inclusa nei backup). Con `allowBackup="true"`, Android ripristinava da solo il database con dentro le password cifrate, ma senza la chiave per decifrarle: `decrypt()` restituiva stringa vuota → autenticazione con password vuota → il server rifiutava LIST ACTIVE/GROUP → nessun elenco, nessun sync.

- **`android:allowBackup="false"`**: disattivato l'auto-backup di sistema, che re-introduceva password non più decifrabili. Per il trasferimento tra dispositivi resta il backup esplicito dell'app (cifrato a password).
- **Messaggio chiaro**: se una password cifrata non è più leggibile, l'app ora lo dice esplicitamente ("Password del server non leggibile… reinseriscila in Impostazioni → Server") invece di tentare in silenzio con password vuota. Vale per sync, lettura, directory e invio.

Nota: chi ha già l'app in questo stato deve reinserire una volta la password del server (Impostazioni → Server); da lì tutto riparte.

## 4m. Novità v0.16 (fix TLS/SNI per provider in hosting condiviso)

Alcuni server (tipicamente commerciali in hosting condiviso, es. dietro OVH) non scaricavano l'elenco gruppi mentre eternal-september sì: il client creava il socket SSL senza passare l'hostname, quindi non inviava l'SNI (Server Name Indication). Senza SNI questi server rifiutano l'handshake o presentano il certificato sbagliato; i client desktop l'SNI lo inviano sempre, perciò lì funzionava.

- **TLS con SNI**: la connessione ora sovrappone il TLS a un socket TCP già connesso passando l'hostname (`createSocket(socket, host, port, true)` + `SNIHostName`), così l'SNI parte correttamente. Nessun impatto sui server che non ne hanno bisogno (eternal-september continua a funzionare).

## 4n. Novità v0.17 (download gruppi per ramo — fix "Read timed out")

Su provider commerciali con feed completo (es. usenet.ovh) il `LIST ACTIVE` è enorme (milioni di gruppi, per lo più binari): il download sforava il timeout di lettura ("Read timed out") e rischiava l'out-of-memory. Non era una regressione: era un successo al limite dei 30 s, che cadeva dalla parte sbagliata quando rete/server erano più lenti.

- **Download per ramo (wildmat):** in "Gruppi disponibili" nuovo campo "Scarica solo" (es. `it.*`, `free.*`) passato a `LIST ACTIVE <wildmat>`. Scarichi solo la gerarchia che ti serve, in pochi secondi.
- **Download esplicito:** niente più scarico automatico all'ingresso (era lui a farti attendere il timeout). Si sceglie il filtro e si tocca "Scarica elenco".
- **Timeout di lettura** per il solo LIST ACTIVE portato a 120 s, come rete di sicurezza sui server medi.

## 4o. 1.0.0-beta1 (consolidamento per i tester)

Prima build destinata a tester esterni via APK firmato (sideload, senza store).

- Versione: `1.0.0-beta1` (versionCode 18).
- **Release build con R8/minify disattivato di proposito**: mai collaudato su quest'app, un crash solo-in-release sarebbe il peggior esordio con i tester. Verrà riattivato (con regole ProGuard complete e collaudo dedicato) verso la 1.0 finale.
- Pulizia: rimossa la query di purge distruttiva `deleteOlderThan` (sostituita da tempo dalla variante sicura che preserva non letti e watched); corretto il commento obsoleto sulla password in `Entities.kt` (è cifrata dal v0.8).
- Aggiunto `VERSIONE.txt` nella radice del progetto per identificare a colpo d'occhio la copia giusta dei sorgenti.

## 4p. 1.0.0-beta2 (lettura e gruppi stile Piao Hong)

Replica delle visualizzazioni del Piao Hong NewsReader (da screenshot — nessun codice riusato dall'APK, che resta protetto da copyright anche se abandonware).

Lettura messaggi:
- **Barra di citazione verticale** colorata a sinistra dei blocchi citati (colore e rientro per livello), come nel Piao Hong; corpo reso a blocchi (`ArticleBody` ristrutturato).
- **Scale testo estese**: aggiunte "Extra grande" (1.5×) ed "Enorme" (1.7×).
- **Fusione linee intelligente** (opzione): collassa le righe vuote consecutive.
- **Accorcia il testo citato** (opzione): i blocchi quote oltre 6 righe si comprimono, tocca per espandere.
- **Navigazione da lettura**: titolo con posizione `[n/tot]` nel gruppo e barra inferiore con ◀ precedente / ▶ successivo (ordine della vista thread corrente), ritorno all'indice, salto a inizio/fine messaggio. Cambio messaggio come stato locale (niente pile nel back stack).

Gruppi sottoscritti:
- **Raggruppati per server** con intestazione (host) e, per ogni gruppo, **busta chiusa/aperta** (non letti / tutto letto) e conteggio **non letti/totale** (es. 5/1331). Nuova query `allSubscribedWithTotals` (`GroupWithTotal`).

Impostazioni (ispirate al pannello Piao Hong):
- **Segna tutto letto uscendo dal gruppo** (freccia indietro e back di sistema).
- **Max intestazioni per sincronizzazione** configurabile (100–2000, era fisso 1000).

## 4q. 1.0.0-beta3 (pull-to-refresh, intestazioni personalizzate, Face)

- **Aggiorna con swipe down**: nella lista messaggi di un gruppo, trascinare verso il basso avvia la sincronizzazione (Material3 `PullToRefreshBox`); resta anche l'icona in alto.
- **Intestazioni personalizzate (stile MesNews)**: in Identità un campo "una per riga: Nome: valore" (es. Reply-To, Organization, Followup-To) aggiunte a ogni messaggio; in composizione a pagina il campo è precompilato e modificabile per singolo messaggio. Gli header vitali (From, Subject, Newsgroups, …) non sono sovrascrivibili.
- **Face (avatar 48×48, stile MesNews)**: supporto allo standard moderno "Face:" (PNG 48×48 in Base64). In lettura, la faccina dell'autore compare accanto al mittente (estratta alla lettura del messaggio e salvata: colonna `Article.faceB64`, migrazione DB v3→v4). In Identità si imposta la propria scegliendo un'immagine (ridotta a 48×48 automaticamente) e viene inviata con ogni post (header con folding). Il vecchio X-Face monocromatico (compressione compface) è rimandato a un giro dedicato con test: l'algoritmo è contorto e merita collaudo serio.

## 4r. 1.0.0-beta4 (data nella lista discussioni)

- **Data di pubblicazione nella lista thread** (telefono e tre pannelli), con formato compatto: ora se di oggi (14:32), giorno/mese se dell'anno corrente (12/07), completa altrimenti (03/11/24). Helper condiviso `formatThreadDate` in ThreadFlatten.kt.

## 4s. 1.0.0-beta5 (intestazioni, editor, dimensioni testo, richieste dei tester)

Interfaccia e testo:
- **Dimensione del testo applicata a TUTTA l'interfaccia** (liste, menù, impostazioni, lettura) tramite scalatura della tipografia nel tema, non più solo al corpo dei messaggi.
- **Nuova scala**: Piccolo (1.15) / Normale (1.3) / Grande (1.5) / Molto grande (1.7). Le vecchie "Compatta" (0.85) e "Normale" (1.0) sono state eliminate perché troppo piccole; le impostazioni esistenti sotto 1.15 vengono portate automaticamente al nuovo minimo.
- **Interlinea compatta** (rapporto 1.20 anziché ~1.45 di Material): più testo a schermo, stile lettore classico.

Lettura:
- **Pulsante "Intestazioni"**: mostra le intestazioni complete del messaggio (testo selezionabile, monospazio). Vengono salvate alla lettura — nuova colonna `Article.headersRaw`, migrazione DB v4→v5.
- **Pulsante "Albero della conversazione"**: apre la discussione del messaggio corrente in forma di albero indentato; toccando una voce ci si sposta su quel messaggio.
- Le azioni secondarie (segui discussione, segna non letto, plonk) sono passate in un menù ⋮ per non affollare la barra.
- **Firma**: in lettura si mostrano al massimo 4 righe da 72 colonne dopo il delimitatore "-- " (netiquette); lo stesso limite è applicato alla propria firma in uscita.

Scrittura:
- **Editor a tutta altezza con scorrimento interno**: il cursore resta sempre visibile mentre si scrive e la citazione sopra è liberamente modificabile. Rimosso lo scroll esterno che faceva "scappare" il campo; aggiunta la gestione dello spazio tastiera.
- I campi mittente/intestazioni sono in un pannello ripiegabile ("Mittente e intestazioni"), chiuso di default nelle risposte per lasciare spazio al testo.

Richieste dei beta tester:
- **"Tutti i messaggi (nessun limite)"** fra le opzioni di intestazioni per sincronizzazione: scarica l'intero storico disponibile sul server.
- **Segna letto / da leggere** con pressione lunga su una voce della lista: singolo messaggio o intera discussione.
- **Il filtro "solo non letti" viene ricordato** fra una sessione e l'altra (icona busta), così chi vuole vedere anche i thread già letti non deve riattivarlo ogni volta.

## 4t. 1.0.0-beta6 (composizione, gesture, layout, ordinamento, ROT13)

Composizione:
- **Pulsante Invia sempre raggiungibile**: spostato in una barra inferiore e attivata la modalità `adjustResize` nel manifest, così resta sopra la tastiera e non serve più chiuderla per spedire. Accanto compaiono il contatore caratteri e il tasto ROT13.
- **ROT13 in scrittura**: un tocco codifica/decodifica il testo del messaggio (la funzione è la sua stessa inversa).

Lettura:
- **Gesture di navigazione** (attivabili in Aspetto): scorrendo oltre il fondo del messaggio si passa al successivo — che è il messaggio seguente della stessa discussione e, a discussione finita, il primo della successiva; oltre l'inizio si torna al precedente. Trascinamento orizzontale per saltare avanti/indietro.
- **ROT13 in lettura**: voce di menù per decodificare al volo i messaggi offuscati (spoiler, battute).

Elenco messaggi:
- **Ordinamento crescente/decrescente** (menù ⋮ → Ordinamento): dal più recente o dal più vecchio. Nelle viste a thread l'ordine si applica alle discussioni usando la data dell'ultimo messaggio.

Layout multi-pannello:
- **Proporzioni regolabili** dei tre pannelli con cursori dedicati (0,5–3,0) e pulsante di ripristino.
- **Disposizione a tre RIGHE** in alternativa alle tre colonne, su richiesta di un tester.

Elenco messaggi (aggiunta successiva, stessa beta):
- **Colore per stato di lettura**: da leggere in rosso, già letti in verde, con due tinte per famiglia scelte in base alla luminosità dello sfondo (leggibili in tema chiaro e scuro). I non letti restano in grassetto: il colore non è mai l'unico segnale, così l'informazione resta accessibile anche a chi non distingue rosso e verde. Disattivabile in Aspetto.

Correzioni successive (stessa beta, dopo i primi riscontri):
- **Gesture: causa reale individuata.** Il rilevatore usava `positionChange()`, che restituisce zero per gli eventi già consumati: essendo ogni trascinamento consumato dal contenitore che scorre, gli accumulatori restavano a zero e nessun gesto scattava mai (né verticale né orizzontale). Corretto con `positionChangeIgnoreConsumed()`. Mappatura ora: destra = discussione successiva, sinistra = precedente, giù = messaggio successivo (con testo già in cima), su = messaggio precedente (con testo già in fondo). Aggiunta la navigazione per discussione (`goNextThread`/`goPrevThread`, salto all'inizio del thread).
- **Gesture, terza revisione**: il rilevatore è stato spostato dal contenitore che scorre a un contenitore ESTERNO e ascolta nella fase `Initial` (dal genitore verso i figli), così vede gli eventi prima che la zona di scorrimento li consumi; continua a non consumare nulla. Soglia abbassata a 60 dp.
- **Diagnostica gesture** (Aspetto, da tenere spenta nell'uso normale): mostra in fondo alla lettura i valori misurati a ogni gesto (dx, dy, posizione di scorrimento, azione decisa). Serve a distinguere fra rilevatore che non parte, valori errati e soglie non raggiunte.
- **Pulsanti espliciti per le discussioni** nella barra inferiore della lettura (precedente/successiva), indipendenti dalle gesture.
- **Gesture: causa finale individuata con la diagnostica.** Il rilevamento era corretto in tutte le direzioni (confermato dai valori dx/dy misurati), ma le funzioni di navigazione leggevano liste catturate da `pointerInput(Unit)` al primo istante, quando gli articoli del gruppo non erano ancora caricati (lista vuota → nessuno spostamento). Risolto agganciando il `pointerInput` ai dati — `pointerInput(currentId, flat, tree)` — così il blocco si riavvia quando le liste si popolano e cattura sempre i valori aggiornati; chiamate dirette a goNext/goPrev/goNextThread/goPrevThread. Rimossa l'indirezione `rememberUpdatedState`.
- I precedenti tentativi (scorrimento annidato; osservatore in fase Final sul contenitore interno; osservatore in fase Initial sul contenitore esterno) rilevavano ma non navigavano per questa cattura obsoleta delle liste. Sostituito da un osservatore passivo (`awaitEachGesture` in fase `Final`) che legge il gesto senza consumarlo e decide al rilascio del dito: orizzontale prevalente = messaggio precedente/successivo, verticale verso l'alto a fine testo = successivo, verso il basso a inizio testo = precedente. Non interferisce con scorrimento, selezione del testo e link.
- **Area di scrittura nei nuovi messaggi**: il pannello "Mittente e intestazioni" era aperto di default nei nuovi thread e toglieva tutta l'altezza al corpo (con la tastiera alzata restava invisibile). Ora è chiuso all'apertura in ogni caso e il campo del messaggio ha un'altezza minima garantita.
- **Layout a pannelli non attivabile**: la soglia automatica era 840 dp, irraggiungibile per un telefono in orizzontale. Portata a 600 dp e aggiunta l'opzione "Layout a pannelli: Automatico / Sempre attivo / Mai", così è forzabile su qualsiasi schermo.

Identità:
- **Firme multiple e casuali** (stile MesNews): più firme separate da `%%`, con estrazione a sorte se l'opzione è attiva. Restano validi i limiti di netiquette (4 righe da 72 colonne).
- **Introduzione alla citazione personalizzabile**, con segnaposto `%autore% %email% %data% %ora% %gruppo% %messageid%` e varianti multiple separate da `%%` scelte a caso.

## 4u. 1.0.0-beta6 — aggiunte successive (viste, avvisi, auto-controllo)

- **Vista ad albero + solo non letti**: attivando il filtro "solo non letti" (icona busta) la vista ad albero ora nasconde i messaggi già letti uno per uno, mantenendo il rientro delle risposte. Prima teneva l'intera discussione se conteneva anche un solo non letto.
- **Vista iniziale**: confermato il default "ad albero" (era già così nel codice; chi vedeva "solo discussioni" aveva la scelta memorizzata da un test precedente).
- **Segnale sonoro sulle risposte a me** (disattivabile, in Manutenzione): un "ding" quando il sync trova una risposta a un mio messaggio. Il riconoscimento avviene confrontando l'autore del messaggio-genitore (ultimo id in References) con l'email impostata in Identità. Nuova colonna `Article.replyToMe` (migrazione DB v5→v6) e indicatore "↩" davanti all'oggetto in lista.
- **Controllo automatico nuovi messaggi** (Manutenzione): ogni 3, 5 o 10 minuti oppure disattivato. Funziona mentre l'app è aperta (`LaunchedEffect` in MainActivity) e sincronizza tutti i gruppi sottoscritti (`syncAllSubscribed`); se attivo, "ding" all'arrivo di risposte a me.

## 4v. 1.0.0-beta6 — ricerca nella cache

- **Ricerca testuale** (icona lente nella schermata principale, telefono e tre pannelli): nuova schermata `SearchScreen` che cerca in tutta la cache locale — oggetto e autore di ogni messaggio scaricato, e corpo dei messaggi già aperti almeno una volta. Ricerca dal vivo da 2 caratteri, fino a 500 risultati ordinati per data; toccando un risultato si apre il messaggio. Query DAO `searchArticles` (LIKE su subject/fromField/body, esclusi i nascosti).

## 4w. 1.0.0-beta6 — taratura gesture

- **Verticali invertite** secondo il flusso di lettura naturale: in fondo al messaggio, scorrere ancora verso l'alto passa al successivo; in cima, tirare verso il basso torna al precedente (prima era il contrario).
- **Sensibilità ridotta**: soglia alzata a 110 dp e, soprattutto, le gesture verticali navigano solo se il gesto PARTE dal bordo del testo (posizione di scorrimento a inizio/fine all'inizio del gesto). Elimina gli scatti involontari mentre si scorre nel mezzo di un messaggio lungo.

## 4x. 1.0.0-beta6 — selezione testo e menù

- **Testo del messaggio selezionabile**: il corpo è ora dentro un `SelectionContainer` (prima lo era solo la finestra Intestazioni). Per evitare che lo swipe rubi la pressione lunga, la navigazione a gesto scatta solo se il gesto è rapido (< 500 ms): una pressione prolungata per selezionare non cambia più messaggio.
- **Barra principale con menù ⋮**: le sei icone in alto (telefono) sforavano fuori schermo e nascondevano la lente. Ora restano dirette la ricerca (lente) e un menù "Altro" che raccoglie Server, Filtri, Identità, Aspetto, Manutenzione.

## 4y. 1.0.0-beta7 — nome definitivo NeReO

- **L'app si chiama NeReO**, acronimo di Ne(ws) Re(ader) O(pen-source). Cambia il nome visibile in tre punti: icona nel launcher (`app_name`), barra del titolo (schermata gruppi e layout a pannelli) e User-Agent dichiarato nei post.
- **`Branding.kt`**: il nome non e' piu' scritto a mano nei singoli file. Un unico oggetto espone `APP_NAME` e `DEFAULT_USER_AGENT`; tutti i punti d'uso lo importano. Alla terza rinomina in un anno era il momento di smettere di cercare letterali con grep.
- **Migrazione dello User-Agent salvato** (`App.migrateUserAgent`): chi aveva gia' aperto e salvato la schermata Identita' si era portato il vecchio valore nelle preferenze, dove sarebbe rimasto anche dopo l'aggiornamento. Ora viene riscritto al primo avvio, ma **solo** se coincide esattamente con un vecchio predefinito (`MyNewsReader (Android)`, `OpenUsenet (Android)`). Uno User-Agent personalizzato non viene mai toccato.
- **`applicationId` invariato** (`org.openusenet.reader`): l'APK si installa sopra la beta6 conservando database, sottoscrizioni, filtri e stato di lettura.
- Lo stile del tema resta `Theme.OpenUsenet`: e' un identificatore interno, mai visibile, e rinominarlo avrebbe solo aggiunto rischio a un rilascio di sola facciata.

## 4z. 1.0.0-beta7 — funzioni

**Stato di lettura recuperabile.** La doppia spunta "segna tutto letto" era irreversibile: un tocco per sbaglio e il gruppo intero spariva dalla vista non-letti. Ora `markAllRead` restituisce gli id che *erano* da leggere e la schermata mostra una barra con ANNULLA. Non si rimette tutto a "non letto" alla cieca: si ripristina la fotografia esatta di un istante prima, quindi le letture fatte davvero prima del tocco restano letture.

Accanto, due comandi distinti nel menu ⋮, che risolvono problemi diversi:
- **Segna tutto come da leggere** — agisce solo sui messaggi gia' in memoria, nessun traffico di rete, istantaneo.
- **Riscarica il gruppo dal server** — `reloadGroupFromScratch`: cancella la copia locale, azzera `lastFetchedArticleNumber` e risincronizza. Senza l'azzeramento dell'avanzamento incrementale il server verrebbe interrogato solo per i numeri successivi all'ultimo visto e non tornerebbe indietro nulla. Ha un dialogo di conferma che dichiara cosa si perde.

**Vista "Discussioni raggruppate"** (ex "Solo discussioni"). Una riga per discussione; il `+` a sinistra apre le risposte indentate, il `-` le richiude. La radice non viene ripetuta fra le risposte: ogni articolo produce al massimo una riga, perche' le liste Compose usano l'id dell'articolo come chiave e non tollerano duplicati. Stato di apertura in memoria per la durata della permanenza nel gruppo (uscendo si riparte tutto chiuso: e' il colpo d'occhio lo scopo della vista). Nel menu ⋮ compaiono "Apri tutte" e "Richiudi tutte" quando la vista e' attiva. Vale sia sul telefono sia nel pannello centrale del layout a tre pannelli.

**Contatore dei non letti per discussione**: pastiglia a destra della riga radice con il numero di messaggi ancora da leggere nel thread, radice inclusa.

**Tinta dell'interfaccia** (`AppSettings.uiPalette`, nove scelte piu' "Predefinito"). Non e' lo sfondo di lettura, che riguarda solo il corpo del messaggio: qui si colora tutta l'app sovrascrivendo `background`, `surface` e i ruoli `surfaceContainer*` dello schema Material. Ogni tinta dichiara la propria polarita' (`dark`), e quando e' attiva decide lei chiaro/scuro e disattiva il colore dinamico di sistema — altrimenti Material You sceglierebbe sfondi propri e cancellerebbe la tinta. Il selettore in Aspetto mostra anteprime reali invece di nomi.

**Schermata Informazioni** (`AboutScreen`, rotta `about`). Nome, versione, autore (MCSM), licenza GPLv3, base tecnica, e un blocco di dati tecnici copiabile con un tocco (versione, build, pacchetto, Android, dispositivo) da allegare alle segnalazioni. Richiede `buildConfig = true` in `build.gradle.kts`: da AGP 8 `BuildConfig` non viene piu' generato di default.

**`Branding.kt`**: nome dell'app e User-Agent in un punto solo, con migrazione una-tantum del valore salvato nelle preferenze.

## 4aa. Numerazione dalle versioni successive

Dopo la beta7 la numerazione passa a **release candidate** e riparte da `1.0.0-rc1`. Il `versionCode` invece **prosegue** (24 → 25 → 26…): e' un intero monotono ed e' l'unica cosa che Android confronta per decidere se un APK e' un aggiornamento. Farlo ripartire da 1 renderebbe l'RC1 "piu' vecchia" della beta7 e il sistema rifiuterebbe l'installazione.

Il passaggio a RC porta con se' un impegno: da li' in poi si chiudono difetti, non si aprono funzioni. Una funzione nuova in RC riporta il conteggio a rc1.

## 4af. 1.0.0-beta10 (rifatta) — bozze, a-capo automatico, identità multiple, cancellazione ripensabile

> La beta10 precedente non è mai stata distribuita ai tester (collaudata solo da Marco), quindi il numero è stato riusato inglobando anche tutto quanto era stato raccolto per la beta11. versionCode invariato a 27.

**A-capo automatico prima dell'invio** (`wrapBody`, `BODY_MAX_COLS = 72`). Segnalazione di un tester: `441 Line 12 too long`. Causa: `postArticle` riceveva `body.lines()`, cioè il testo spezzato solo sui ritorni a capo esistenti; un paragrafo scritto di getto diventava una riga unica. Il wrap conserva il prefisso di citazione sulle righe di continuazione, non spezza le parole e lascia sforare i token più lunghi del limite (URL). Validato su 5 scenari: paragrafo lungo, citazione a due livelli, URL lungo, firma dopo il delimitatore, testo già conforme.

**Bozze** (entità `Draft`, `DraftDao`, migrazione **6→7**, `DraftsScreen`). Modello: salvataggio a comando più salvataggio automatico prima dell'invio con cancellazione a invio riuscito — così un invio fallito non fa perdere il testo. La bozza conserva il contesto (References, supersede, gruppo, identità, articolo padre) e non solo oggetto e corpo. La migrazione è una `CREATE TABLE`, che non tocca dati esistenti; `references` come nome colonna è già usato da `articles` da dieci beta, quindi il pattern è collaudato.

**Identità per singolo server** — vedi sezione precedente (invariata).

**Cancellazione col modello contrassegna-e-conferma** — vedi sezione precedente (invariata).

**Contatore discussioni "da leggere / totale"**: `unread` e `total` erano già calcolati in `ThreadItem`, mancava solo mostrarli. Badge in tinta primaria con `n/tot` quando c'è da leggere, in tinta neutra col solo totale quando è tutto letto.

**ROT13 sulla selezione** in composizione: se `bodyField.selection` non è vuota trasforma solo quella parte e mantiene la selezione (si può ripremere per tornare indietro), altrimenti agisce su tutto.

**Barra del gruppo alleggerita**: da cinque a tre azioni (letti/non letti, segna tutto letto, aggiorna). Ricerca e "solo discussioni seguite" scendono nel menu ⋮, dove guadagnano un'etichetta scritta. Risolve il titolo troncato con font di sistema grandi.

**Purple rivisto**: la prima versione aveva sfondo #502E92 e barre #5E3AA8 — separazione 1,23:1, cioè barre invisibili — e il rosso dei non letti a 4,28:1, sotto soglia. Ora sfondo #331C60 e barre #4A2C86: verificate tutte le combinazioni in uso (testo, non letti, letti, testo tenue, outline su entrambi i fondi), peggiore 4,6:1.

**Sovrapposizione etichette**: l'etichetta di un `OutlinedTextField` è disegnata sopra il proprio bordo superiore e senza spazio sotto l'elemento precedente ci finisce addosso. Applicato `verticalArrangement = Arrangement.spacedBy` ai contenitori (IdentityScreen, ComposeScreen, MaintenanceScreen), sul modello di FiltersScreen che era l'unica già immune.

## 4ag. 1.0.0-beta10 (prima versione, mai distribuita) — identità multiple, cancellazione ripensabile, menu a pannello, posizione lista (piano C)

**Identità per singolo server.** Esteso lo schema per-server già usato per la firma: `name.server.<id>` e `email.server.<id>` accanto a `signature.server.<id>`. `identityFor(prefs, serverId)` risolve con ricaduta sui valori generali; `ComposeScreen` la applica quando risolve il server di destinazione. `hasServerIdentity` impedisce che postare con l'identità di un server sovrascriva il mittente generale. Nessuna migrazione di schema: tutto in SharedPreferences.

**Cancellazione col modello "contrassegna e conferma uscendo"** (`PendingDeletions`, in memoria per gruppo). Eliminare marca invece di cancellare: il messaggio resta barrato (`TextDecoration.LineThrough` su titolo e riga di dettaglio) e toccandolo si offre il ripristino; `leaveGroup` esegue il `deleteLocal` effettivo dei contrassegnati. È il modello IMAP (flag Deleted + expunge alla chiusura). Scelto di tenerlo in memoria e non nel DB proprio perché il contrassegno deve vivere solo dentro il gruppo: evita una migrazione su installazioni già distribuite. Limite noto e accettato: app terminata dal sistema ⇒ contrassegni persi, messaggi conservati.

**Menu contestuale come `ModalBottomSheet`** (entrambi i layout). Prima era un `AlertDialog` con le voci impilate dentro `confirmButton`: il `dismissButton` veniva affiancato alla colonna e finiva sovrapposto alle voci a metà altezza. Un dialogo di conferma non è un contenitore per elenchi di azioni.

**Posizione lista thread — riscritta col piano C.** La versione beta9 salvava l'indice di riga e non funzionava per due cause sommate: (1) al rientro il Flow consegna prima una lista vuota, lo `snapshotFlow` registrava indice 0 e cancellava la posizione buona; (2) l'indice è fragile rispetto a espansioni, filtri e messaggi nuovi. Ora `ThreadScrollMemory` ancora l'`articleId` del primo visibile; il salvataggio è condizionato a `items.getOrNull(idx) != null`; il ripristino avviene una sola volta per ingresso (`posizioneRipristinata`) quando la lista è popolata, via `scrollToItem`. Validato in simulazione su 4 scenari: rientro con lista vuota iniziale, rientro con 3 messaggi nuovi in cima (dove l'indice avrebbe sbagliato bersaglio), messaggio sparito, aggiornamenti ripetuti durante lo scorrimento.

**Aspetto.** Tinta scura `purple` (#502E92 campionato dal riferimento, barre #5E3AA8, testo #EDE7F6, contrasto ≈8:1). Icona non-letti da `MarkEmailUnread` (busta con pallino, distinguibile solo da un dettaglio minuscolo) a `Markunread` (busta chiusa piena, distinta per sagoma dalla busta aperta).

## 4ae. 1.0.0-beta9 — thread troncati, prefetch corpi, supersede, cancel-locale, server preset, novità in-app

**Thread troncati risolti (threader con container vuoti).** Segnalazione (Roxy): discussioni che iniziano da una "Re:" perché il messaggio radice non è tra gli scaricati (mai preso dal sync incrementale, o caduto per conservazione). Il vecchio `Threader.thread` non creava i container vuoti del JWZ: se un `References` puntava a un padre assente, la risposta diventava una radice a sé, spezzando la discussione in tronconi. Ora il passo 2 materializza gli anelli mancanti come `ThreadNode(article = null)` e concatena l'intera catena di References; `flatten()` salta i container (non producono righe a schermo). `promoteLoneChild` evita un livello di rientro fantasma quando il padre mancante aveva un solo figlio. Validato con 6 scenari (radice mancante, anello intermedio mancante, thread completo, thread distinti, ciclo nelle references, orfano puro): l'infrastruttura per i nodi nullable c'era già, mancava solo la creazione dei container.

**Prefetch dei corpi delle risposte dirette** (`Repository.prefetchReplies`, `AppSettings.prefetchReplies`, default on). Aprendo un messaggio, in background scarica i corpi dei figli diretti (ultimo Message-ID di References == messageId del corrente), riusando una connessione. Disattivabile in Aspetto.

**Scarica tutti i corpi del gruppo** (`downloadAllBodies`, azione esplicita nel menu ⋮ con dialogo di avviso). Opt-in perché è una richiesta per messaggio: lunga sui gruppi grandi. Non marca come letti.

**Supersede** (`postArticle(supersedesId=…)`, rotta `COMPOSE_SUPERSEDE`, voce nel menu contestuale). Ripubblica un proprio articolo con header `Supersedes:`, precompilando oggetto/corpo/References dell'originale.

**Cancellazione solo locale** (`deleteLocal`/`restoreLocal`, voce nel menu contestuale con ANNULLA). Rimuove la copia sul dispositivo, non invia nulla al server; risincronizzando torna. Il cancel Usenet è stato scartato per ora (irreversibile, spesso ignorato) su decisione esplicita.

**Selezione multipla** (`selectionMode`/`selectedIds` in `ThreadListScreen`). Si entra dal menu contestuale ("Seleziona più messaggi"); in modalità selezione la TopAppBar è sostituita da una barra dedicata con conteggio, seleziona-tutto ed eliminazione di gruppo, i tocchi selezionano invece di aprire, e la spunta prende il posto del +/- nella riga (non servono mai insieme). `BackHandler` esce dalla selezione invece che dal gruppo. Il repository accettava già una lista di id, quindi l'aggiunta è tutta di interfaccia.

**Nome del file di backup**: da `mynewsreader-backup.json` fisso a `${Branding.APP_NAME}-Backup-AA-MM-DD.json`, con il marchio preso dalla fonte unica e la data per distinguere/ordinare i backup.

**`android:label` sull'activity**: il nome era dichiarato solo sull'`<application>`. L'etichetta mostrata nelle schermate di sistema per app già installate resta comunque in cache lato Android e si aggiorna solo con reinstallazione.

**Server preconfigurati** (`ServerPresets`, menu nel dialogo nuovo-server). Eternal September, Solani, OVH (registrazione), tcpreset, Paganini (aperti): compilano host/porta/SSL, l'utente aggiunge solo le credenziali.

**Posizione ricordata nella lista thread** (`ThreadScrollMemory`, holder in-memory per groupId + `rememberLazyListState`). Tornando da un thread la lista resta dov'era invece di risalire in cima. Non persistito su disco di proposito (comodità di sessione).

**Novità versione in-app** (`ReleaseNotes` + `ReleaseNotesScreen`, voce accanto a Informazioni). Il changelog della sola versione corrente dentro l'app; il README.txt resta lo storico completo esterno. Costante curata a mano, aggiornata a ogni release.

**Filtro "solo discussioni seguite"** (idea D della wishlist; toggle a stella nella barra). Limita la lista ai thread con almeno un messaggio watched, seguendo il legame via References. Applicato a monte in `ThreadListScreen`, senza toccare `buildThreadList`.

**Rifattorizzazione minore**: `openClient` centralizza connect+auth+gestione password illeggibile, prima duplicati in `fetchBody` e `postArticle`.

Idea E (linea "nuovi da qui") scartata definitivamente su richiesta.

## 4ac. 1.0.0-beta8 — swipe corretto, disiscrizione, ordinamento, firma per server, ricerca nel gruppo, refresh, nascondi letti + rifiniture

> Nota di numerazione: la precedente beta8 (correzione swipe + disiscrizione + densità pannello) non era mai stata pubblicata ai tester, quindi il numero è stato riusato. Questo rilascio raccoglie sia quelle funzioni sia quelle aggiunte in seguito, in un'unica beta8.

**Ordinamento delle discussioni a quattro criteri.** Il vecchio `sortAscending` (booleano recente/vecchio, sempre per ultima attività) diventa `threadSort`, chiave di `AppSettings.threadSorts` su due assi indipendenti: quale data (attività = messaggio più recente del thread; apertura = messaggio radice) e verso. I quattro valori: `activity_desc`, `activity_asc`, `opened_desc`, `opened_asc`. `orderedRoots` sceglie la chiave con `min`/`max` sulle date del sottoalbero. Retro-compatibilità all'avvio: se manca `thread_sort` ma c'è il vecchio `sort_ascending`, si traduce in `activity_*`.

**Firma diversa per ogni server.** Chiave `signature.server.<id>` accanto alla `signature` globale. `signatureBlock(prefs, serverId)` usa la firma del server se presente e non vuota, altrimenti ricade sulla globale — quindi chi non la compila non cambia comportamento. `ComposeScreen` risolve il `serverId` dal gruppo di destinazione e lo passa a `buildPrefill`. La UI in `IdentityScreen` (che ora riceve il repository) elenca i server con un campo firma ciascuno.

**Ricerca dentro il singolo gruppo.** Query `searchArticlesInGroup(groupId, q)` gemella di `searchArticles` con filtro `groupId`. `SearchScreen` accetta `groupId`/`groupName` opzionali; rotta `SEARCH_GROUP` con il nome URL-encoded. Pulsante lente nella barra di `ThreadListScreen` e nella testata del pannello centrale di `ThreePaneHome`.

**Swipe-down per aggiornare dall'elenco gruppi.** `PullToRefreshBox` (già usato nella lista discussioni) attorno alla lista gruppi in `GroupsScreen` e alla colonna sinistra di `ThreePaneHome`; lancia `syncAllSubscribed`.

**Nascondi i messaggi letti nel layout a pannelli.** Il pannello centrale aveva `unreadOnly` cablato a `false`: il filtro esisteva già in `treeList` (nasconde i singoli messaggi letti mantenendo il rientro) ma non era raggiungibile lì. Aggiunto il toggle in testata, condividendo la preferenza `AppSettings.unreadOnly` con la lista a schermo intero.

**Rifiniture da mia proposta (idee A/B/C).**
- A — "Segna la discussione come letta/da leggere": c'era già nel menu contestuale della lista a schermo intero; portata anche nel layout a pannelli (che non aveva menu contestuale).
- B — "Ignora sempre questo autore (plonk)": `repo.plonkAuthor` esisteva ma era raggiungibile solo dentro l'articolo aperto; aggiunta al menu contestuale di entrambi i layout.
- C — "Salta al primo non letto": `goNextUnread` in `ArticleScreen`, pulsante nella bottom bar, abilitato solo se più avanti nella sequenza c'è un non letto. Parte da position+1 perché il messaggio corrente conta già come letto.

Idee D (vista solo-watched) ed E (linea "nuovi da qui") accantonate su richiesta come wish list.

**`VERSIONE.txt` ora generato.** Il file di controllo versione era un promemoria scritto a mano, trascinato invariato di zip in zip dalla beta6: era rimasto fermo a `1.0.0-beta6 / versionCode 23` mentre `build.gradle.kts` avanzava, diventando l'unico dato incoerente del progetto — il guardiano che segnalava "copia vecchia" puntando al progetto giusto. Ora un task Gradle (`generaVersioneTxt`, dipendenza di `preBuild`) lo riscrive a ogni build leggendo `versionName`/`versionCode`/`applicationId` da `defaultConfig`. Non è più una copia da allineare ma un derivato: non può divergere per costruzione.

**Regressione beta7 sullo swipe verticale (correzione).** `ArticleScreen` costruiva la sequenza di prev/next con `AppSettings.threadView`. Dalla beta7 la vista `ROOTS` restituisce le sole radici, e il suo stato di apertura vive in `ThreadListScreen`, non in `ArticleScreen`. Conseguenza: leggendo una risposta, `position` valeva -1; `goNext()` cadeva su `flat[0]` — salto silenzioso alla prima discussione del gruppo — e `goPrev()` non faceva nulla.

Nota: il codice del gesto non era stato toccato, il difetto era tutto nei dati che riceveva. Il raggruppamento e' una scelta di *presentazione* della lista; dentro l'articolo la sequenza di lettura e' quella dell'albero completo. `navView` mappa quindi `ROOTS → TREE` per la sola navigazione. Aggiunte inoltre due guardie su `position < 0` in `goNext`/`goPrev`: senza, un indice non trovato produce un salto all'inizio che *sembra* un gesto riuscito, che e' il modo peggiore di fallire.

**Disiscrizione da un gruppo.** `unsubscribe(groupId)` esisteva gia' nel repository ma non era raggiungibile da nessuna schermata: c'era la porta d'ingresso (directory con il `+`) e non quella d'uscita. Aggiunto `unsubscribeGroup(groupId, eliminaMessaggi)` e il composable condiviso `UnsubscribeDialog`, richiamato con pressione prolungata sia dall'elenco gruppi sia dalla colonna sinistra del layout a pannelli.

La riga del gruppo **non** viene cancellata dal database: resta con `subscribed = false`, cosi' la conservazione personalizzata sopravvive a una risottoscrizione ed e' coerente con `subscribe`, che riusa la riga esistente. Se si sceglie di eliminare i messaggi si azzera anche `lastFetchedArticleNumber`, altrimenti una risottoscrizione futura non riscaricherebbe nulla.

**Densita' del pannello discussioni** (`AppSettings.paneListScale`, cinque livelli da 1.0 a 0.58). Agisce sulla sola colonna centrale del layout a pannelli. E' deliberatamente separato da `bodyFontScale`: su TV box e monitor guardati da lontano servono molte intestazioni a colpo d'occhio **e** un corpo del messaggio che resti leggibile — due esigenze opposte che un moltiplicatore globale non puo' soddisfare insieme.

Il fattore scala carattere, interlinea, rientro dell'albero e margini verticali: ridurre il solo carattere lascia le righe alte come prima e non fa guadagnare quasi nulla. L'estensione `TextStyle.ridotto()` salta i valori non specificati, perche' moltiplicare un `TextUnit` Unspecified solleva un'eccezione a tempo di esecuzione.

## 4ab. Convenzione del README per i tester

Impaginazione da mantenere invariata nei rilasci futuri:

- testo semplice, righe entro 70 colonne, nessun markup;
- intestazione racchiusa fra righe di `=`, con nome, versione, autore, data e scioglimento dell'acronimo;
- titoli di sezione principali fra righe di `=`, sottosezioni fra righe di `-`;
- ordine fisso: presentazione → NOVITA' DELLA VERSIONE → STORICO DELLE VERSIONI PRECEDENTI → NOTE DI INSTALLAZIONE → SEGNALAZIONI;
- chiusura con "Buon Test, Usenet Still Lives!" e firma MCSM (aka Marco).

## 5. Piani di sviluppo

- **Piano A (questa base)**: v0.1 compila, si connette, sottoscrive, sincronizza, filtra, legge. Rodaggio su server reale (es. eternal-september) e bugfix.
- **Piano B (v1.1)**: posting completo con modalità quoting, backup/restore, viste thread alternative, housekeeping automatico, password nel Keystore.
- **Piano C (v1.2+)**: sync schedulato con WorkManager, notifiche sui thread watched, ricerca full-text locale (FTS5), export mbox, F-Droid.

## 6. Note tecniche

- minSdk 26 (java.util.Base64), targetSdk 35, Kotlin 2.0, AGP 8.7, Room con KSP.
- Le password server sono per ora in chiaro nel DB locale (come l'app originale): la migrazione a EncryptedSharedPreferences/Keystore è segnata TODO v1.1.
- Il client NNTP è sincrono per semplicità e viene sempre usato da `Dispatchers.IO`; una connessione per operazione (niente pool nella v0.1).
- Test JVM in `app/src/test` coprono FilterEngine, KillFile, Threader, MimeUtil e parsing overview.

## 7. Beta11 — STORICO (lavorazione conclusa da tempo; testo lasciato com'era)

*Sezione storica: quando fu scritta la beta11 era in lavorazione. Tutto ciò che segue è stato poi compilato, collaudato e superato da decine di versioni — l'ultima è la 1.3. Il testo resta com'era perché documenta il ragionamento dell'epoca.*

Sette voci di wishlist, tre bugfix e quattro funzionalità, raccolte da segnalazioni tester (Roxy) e richieste dirette. Nota di processo: dopo aver sostituito i sorgenti, **Gradle Sync** prima di buildare — vedi promemoria in cima a questo documento di stato progetto.

**Bug — prefetch corpi risposte silenziosamente rotto su alcuni server.** `NntpClient.parseOverviewLine` assumeva l'ordine RFC 3977 "di default" dei campi OVER/XOVER per posizione fissa, ma il protocollo non lo garantisce: `LIST OVERVIEW.FMT` non veniva mai interrogato. Aggiunta `NntpClient.overviewFormat()` (cache per connessione, fallback all'ordine di default se il server non supporta il comando) e `parseOverviewLine` ora mappa i campi per nome. Sospetto originario: server Solani con `overview.fmt` non-standard.

**Bug — testo di composizione che spariva in fondo alla pagina, solo landscape.** `ComposeScreen` aveva sia `windowSoftInputMode="adjustResize"` (manifest) sia `Modifier.imePadding()` (Compose) sullo stesso `Scaffold`: due meccanismi di gestione tastiera sovrapposti che in landscape (poco spazio verticale) sottraevano spazio due volte. Rimosso `imePadding()`, resta solo `adjustResize`. Da riverificare su device reale in landscape prima di considerarlo chiuso — non è stato possibile testarlo in questo ciclo (nessun ambiente Android disponibile).

**Bug — menu ⋮ del gruppo assente in `ThreePaneHome` (landscape/tablet, larghezza ≥600dp).** Il layout a tre pannelli è una reimplementazione separata da `ThreadListScreen`, mai allineata: mancava l'intero menu contestuale del gruppo (vista, ordinamento, segna tutto letto, riscarica dal server, scarica corpi, mostra filtrati, conservazione gruppo) e persino i filtri "solo seguite"/"mostra filtrati" alla base. Portato per intero (Piano A: parità rapida, non il refactoring a composable condiviso — quello, Piano C, resta pianificato per la RC insieme a R8). `RetentionDialog` e `viewLabel`, prima `private` in `ThreadListScreen.kt`, sono stati resi visibili nel package per essere riusati invece di duplicati.

**Riordino manuale di gruppi sottoscritti e server.** `Server.sortOrder` esisteva già in schema e query ma non veniva mai scritto da nessuna parte (sempre 0, quindi ordine sempre alfabetico di fatto). Aggiunto lo stesso campo a `Group` (migrazione Room v7→v8, `ALTER TABLE groups ADD COLUMN sortOrder INTEGER NOT NULL DEFAULT 0`). `Repository.moveGroup`/`moveServer`: ad ogni spostamento rinumerano l'intera lista coinvolta in ordine attuale (0,1,2,...) prima di scambiare i due vicini, per non incepparsi quando più righe condividono ancora `sortOrder = 0` di default. UI: frecce su/giù nella riga (Piano B — niente drag & drop per ora).

**Intestazione gruppo a due righe.** In `ThreadListScreen`, `TopAppBar` conteneva nome gruppo e quattro icone azione sulla stessa riga: un nome lungo veniva troncato. Ora `TopAppBar` porta solo freccia indietro + nome (fino a 2 righe, `TextOverflow.Ellipsis` solo oltre), le azioni sono su una `Row` separata sotto.

**Scaricamento automatico dei corpi.** Nuove impostazioni `AppSettings.scaricaCorpiAutomatico` (bool, default `false`) e `maxCorpiPerApertura` (Int, campo libero — non una lista di preset come per le intestazioni, valore a discrezione dell'utente, default 50), esposte in Manutenzione e backup. Se attivo, dopo il sync automatico all'apertura di un gruppo richiama `Repository.downloadAllBodies(groupId, limit = maxCorpiPerApertura)` — stessa funzione già usata dall'azione manuale "Scarica tutti i corpi", solo con un tetto configurabile invece del default 5000. Agganciato sia in `ThreadListScreen` sia in `ThreePaneHome`.

**Gestione del cross-posting (rilevamento, non invio).** L'invio era già sicuro di suo: `Repository` scrive sempre `Newsgroups: ${group.name}`, un solo gruppo, header riservato non sovrascrivibile — nessuna modifica necessaria lì. Aggiunta `crosspostGroups(headersRaw)` in `ThreadFlatten.kt` (condivisa fra le schermate), che legge l'header `Newsgroups:` reale da `Article.headersRaw` già salvato da `fetchBody` per la vista "Intestazioni complete": **nessuna nuova richiesta di rete**. Badge "⚠ Crosspostato in N gruppi" visibile già in lettura (`ArticleScreen` e `ThreePaneHome.ArticlePane`); al tocco di "Rispondi" su un messaggio crosspostato, dialog di conferma esplicita prima di aprire la composizione.

