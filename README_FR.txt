(Traduit avec l'aide de Gemini)
(Révision humaine en cours - Hulan revision in progress)
======================================================================
NeReO 1.5.5 - Ne(ws) Re(ader) O(pen-source)
Lecteur de news Usenet (NNTP) texte seul pour Android
NeReO 1.5.5 (versionCode 173) - Par MCSM - 18/10/2026
***  VERSION 1.5.5  ***

Trois éléments dans le carnet à faire: un défaut connu et deux petites confusions rencontrées par les utilisateurs. Et une nouvelle apparence pour l'auteur du message, plus l'icône thématique ; et une correction de la 1.5.4 signalée par un utilisateur.

L'AUTEUR DU MESSAGE A UNE NOUVELLE APPARENCE. À côté du nom, sa Face ou, lorsqu'elle manque, ses initiales sur un cercle coloré : la couleur vient de l'adresse, donc la même personne a toujours la même couleur, dans chaque groupe. La date indique "aujourd'hui", "hier" ou le jour de la semaine pour les messages récents (touchez-la pour la date complète). Également dans les panneaux, où la Face manquait jusqu'à présent.

RÉPONDRE DANS LES DISCUSSIONS TRÈS PROFONDES FONCTIONNE À NOUVEAU. Lorsqu'un message comportait une ligne References très longue (un dans nl.politiek en avait 27), la réponse dépassait la limite de 998 caractères par ligne d'en-tête et le serveur la refusait. Maintenant, NeReO supprime les identifiants en double et, au-delà de la limite, supprime les plus anciens après le premier, en conservant le début de la discussion et les messages les plus récents (RFC 5537). En dessous de la limite, rien ne change.

LES X-FACE SONT VISIBLES. Le smiley classique en noir et blanc d'Usenet (l'en-tête X-Face, envoyé par MesNews et par de nombreux lecteurs traditionnels) apparaît à côté du nom de l'auteur, où n'apparaissait jusqu'à présent que la Face en couleurs. D'abord la Face, puis la X-Face, puis les initiales colorées. En la touchant, elle s'agrandit et reste nette. NeReO les affiche, il ne les envoie pas.

LES NOUVEAUX MESSAGES RESTENT VRAIMENT VISIBLES. La correction de la 1.5.4 ne fonctionnait que si NeReO venait de vous ramener là où vous en étiez : lors de la première entrée dans le groupe après avoir ouvert l'application, ou avec la liste déjà tout en haut, les messages arrivés avec la synchronisation pouvaient encore finir au-dessus du bord de l'écran. Désormais, à chaque ouverture du groupe, si de nouveaux messages arrivent et que vous n'avez pas encore fait défiler la liste avec le doigt, elle remonte tout en haut pour les montrer. Avec les tris "du plus récent", elle remarque également une réponse dans la discussion déjà en haut ; avec ceux "du plus ancien", où les nouveaux sont en bas, vous restez où vous êtes. Également dans la vue en panneaux.

ICÔNE THÉMATIQUE : sur Android 13 et versions ultérieures, avec les icônes thématiques activées, celle de NeReO prend également les couleurs de votre thème.

LA RECHERCHE INDIQUE OÙ ELLE A TROUVÉ LE MOT. Si le mot se trouve uniquement dans le texte du message, un court extrait avec le mot en jaune apparaît sous le résultat. Auparavant, un tel résultat n'avait pas de jaune et semblait incorrect.

LE REFUS 441 DU SERVEUR EXPLIQUÉ. La réponse du serveur, qui reste entière, est suivie d'une ligne d'explication. Pour le cas le plus courant, "Article must contain real content", elle vous dit quoi faire : écrire quelque chose de plus.

DANS LA VUE EN PANNEAUX, LE CHAMP DU MESSAGE N'EST PLUS ÉCRASÉ : la zone de saisie défile et le champ conserve une hauteur utilisable, comme dans la vue en pages depuis la 1.4.9.

LE NOM EST RÉSERVÉ. Le code reste sous GPL, mais les versions modifiées doivent utiliser un autre nom et un autre applicationId : voir TRADEMARK.txt.

***  VERSION 1.5.4  ***

Trois demandes d'utilisateurs, parvenues à Marco le lendemain de la 1.5.3.

"CITER ET RÉPONDRE". En sélectionnant du texte dans un message, le menu propose à côté de "Citer" une commande qui ouvre directement la réponse avec cette citation déjà à l'intérieur, le curseur en dessous et la signature à la fin. Auparavant, cela nécessitait cinq étapes.

EN OUVRANT UN GROUPE, LES NOUVEAUX MESSAGES NE S'ÉCHAPPENT PLUS. La liste retournait à l'endroit où vous l'aviez laissée avant que les nouveaux n'arrivent en haut, et même en rouvrant, vous retourniez toujours là. Désormais, si de nouveaux messages arrivent et que vous n'avez pas encore touché la liste, elle remonte en haut pour les montrer.

LES MISES À JOUR "UNE FOIS PAR JOUR" vérifient à la première ouverture de chaque jour, et de toute façon après 24 heures. Auparavant, cela attendait 24 heures depuis le dernier contrôle, et une nouvelle version pouvait être remarquée même un jour plus tard.

***  VERSION 1.5.3  ***

D'après les tests de Marco : une règle supprimée doit emporter avec elle ce qu'elle avait fait.

UNE RÈGLE SUPPRIMÉE, SON EFFET DISPARAÎT. En supprimant ou en modifiant une règle "Ignorer", les messages qu'elle cachait redeviennent visibles SANS LES TÉLÉCHARGER À NOUVEAU depuis le serveur ; en supprimant une règle "Mettre en évidence", la couleur disparaît ; en supprimant une règle "Suivre le fil", l'étoile et la couleur disparaissent - à moins qu'une autre règle active ne les cache, les mette en évidence ou les suive encore. "Appliquer maintenant" fonctionne désormais dans les deux sens. Les étoiles ajoutées manuellement restent. Limites : un message étoilé manuellement qui tombait également sous la règle perd l'étoile ; les auteurs masqués avec le plonk rapide avant la 1.5.3 restent masqués.

LE JOURNAL DE DIAGNOSTIC RACONTE LES FILTRES : lignes "Filtres :" pour chaque règle enregistrée ou supprimée et pour chaque "Appliquer maintenant", avec le nombre de messages redevenus visibles, ayant perdu l'étoile ou ayant changé de score.

***  VERSION 1.5.2  ***

Aucune nouvelle fonction : l'application est la même, mais pèse beaucoup moins.

L'APK MAIGRIT. NeReO emportait avec lui toute la bibliothèque d'icônes Material - plus de ONZE MILLE icônes, soit plus de la moitié du code compilé - et en utilise 75. Maintenant, il ne porte que celles-ci, copiées dans les sources avec les mêmes noms et vérifiées comme identiques une par une (formes, couleurs, mise en miroir pour les langues de droite à gauche). À l'écran, rien ne change.

***  VERSION 1.5.1  ***

Première version de fermeture : protège le texte que vous écrivez et retire une promesse qu'il n'est pas nécessaire de tenir.

LE BROUILLON S'ENREGISTRE TOUT SEUL. Une seconde et demie après la dernière modification, et à nouveau lorsque vous quittez l'écran ou que l'application passe en arrière-plan. Quitter sans toucher "Enregistrer le brouillon" ne fait plus perdre le texte. Une réponse ouverte et laissée telle quelle NE DEVIENT PAS un brouillon ; après un envoi réussi, le brouillon NE RÉAPPARAÎT PAS ; si l'envoi échoue, le brouillon revient protéger le texte.

"IGNORER" dans la barre inférieure de rédaction : jette le texte et supprime le brouillon, après confirmation. Avec la sauvegarde automatique, c'est le moyen de quitter sans laisser de trace.

FILTRES : LA PROPAGATION AUX RÉPONSES SUIT L'ACTION DE LA RÈGLE (versionCode 163). "Étendre aux réponses" sur une règle "Suivre le fil" ou "Mettre en évidence" CACHAIT les réponses, c'est-à-dire précisément les branches à voir : maintenant, elle les suit avec l'étoile ou les met en évidence. "Appliquer maintenant" met l'étoile même aux messages déjà téléchargés d'une règle "Suivre le fil". Pour les règles qui masquent, le comportement est IDENTIQUE. La révocation d'une règle qui mettait en évidence supprime la couleur ; les étoiles restent, car elles peuvent avoir été mises manuellement.

L'ÉCRAN À LIGNES POUR LA TÉLÉCOMMANDE, annoncé dans la 1.5, n'arrive pas pour l'instant. "Citer" fonctionne avec la sélection du texte ; l'écran sera fait s'il est vraiment utile à quelqu'un.

***  VERSION 1.5  ***

Ouvre un cycle long : le PACK CITATIONS, demandé par Ammammata et élargi par Bingo3331. Cette première pierre ne se voit pas, et nous le disons au lieu de le taire.

"CITER" DANS LE MENU DE SÉLECTION (versionCode 158). Vous sélectionnez une partie d'un message et, dans le menu qui apparaît au-dessus de la sélection, il y a "Citer" : une citation complète finit dans le presse-papiers - votre introduction, les lignes avec "> " devant et, à la fin, le Message-ID avec le lien permanent. On cite toujours les LIGNES ORIGINALES, ENTIÈRES : le texte à l'écran est retravaillé (barres au lieu de ">", numéros de niveau, paragraphes flowed réunis), la sélection indique donc seulement QUELLES lignes, et ce qui était déjà cité conserve ses niveaux. En lecture plein écran et, depuis le versionCode 161, également dans la vue en panneaux ; l'écran à lignes pour la télécommande arrive dans une étape ultérieure.

"CITER" DANS UNE CITATION INDIQUE QUI A VRAIMENT ÉCRIT (versionCode 159). Si les lignes choisies étaient déjà citées dans le message - par exemple la phrase de Giangi dans la réponse de Roberto - la citation ne commence plus par "Roberto a écrit :" : elle utilise l'attribution qui introduit ces lignes dans le message ("Le 05/10, Giangi a écrit :") et supprime les niveaux supplémentaires. Si l'attribution est absente ou non reconnue, le comportement précédent reste.

LE README ITALIEN S'APPELLE DÉSORMAIS README_IT.txt (versionCode 160), à côté de README_EN.txt : un seul nom, identique dans le zip des sources et dans le dossier SourceForge.

LE MOTEUR DE CITATIONS A ÉTÉ DIVISÉ EN DEUX. D'une part, une VALEUR qui décrit une citation - qui l'a écrite, où, quand, avec quel objet, le Message-ID avec le lien permanent et les lignes choisies ; d'autre part, une FONCTION qui produit le texte à partir de cette valeur. Ce n'est pas un détail de style : le collecteur qui arrivera plus tard devra conserver une LISTE de citations et les formater toutes à la suite, et si la capture écrivait directement une chaîne dans le presse-papiers, le collecteur devrait la rouvrir et la démonter. C'est exactement ainsi qu'est née la sixième copie d'une logique dans ce projet.

L'EN-TÊTE DE LA CITATION NE S'INVENTE PAS : c'est votre introduction à la citation, avec tous ses espaces réservés, y compris %objet%. Un seul modèle dans toute l'application - ceux qui ont personnalisé le leur le retrouvent ici aussi.

LA LIGNE QUI MET LE "> " DEVANT LE TEXTE CITÉ existe désormais à un seul endroit, au lieu d'être dans la fonction qui prépare les réponses. Comportement identique, vérifié ligne par ligne par un banc d'essai.

TESTÉ AVANT D'ÊTRE COMPILÉ. Le nouveau fichier est du pur Kotlin, sans un seul import : il se compile tout seul avec kotlinc et exécute 21 assertions sur le code réel avant que l'APK n'existe. Dans la 1.4.11, deux défauts sur deux avaient été trouvés par l'exécution et zéro par la lecture - cette fois, le banc d'essai naît en même temps que le code au lieu d'après une panne.

***  VERSION 1.4.11  ***

Un seul point, demandé par ObiWan. Il déclare de lui-même sa propre limite, car c'est la RFC qui la décrit qui la déclare.

SI UN SERVEUR PROPOSAIT STARTTLS ET CESSE DE L'ANNONCER, IL VOUS LE DIT MAINTENANT. STARTTLS est le passage d'une connexion en clair à une connexion chiffrée : le serveur l'annonce parmi ses capacités, et quiconque s'interpose dans la connexion peut supprimer cette seule ligne de la réponse - un lecteur qui ne sait rien de mieux n'essaie même pas de chiffrer, et tout semble normal. NeReO se souvient maintenant de quels serveurs le proposent et signale à côté du nom du serveur, dans la liste des serveurs, celui qui le proposait et ne le propose plus. C'est exactement la défense suggérée par la RFC 4642 par. 5, avec ses propres mots : "recording the fact" et "generating an alarm".

ET LA LIMITE, DITE IMMÉDIATEMENT : la RFC écrit "partially protect", et ce "partially" n'est pas une nuance. La comparaison nécessite une connexion propre PRÉCÉDENTE : si quelqu'un s'interpose dès la toute première fois que vous parlez à un serveur, ce mécanisme ne le verra jamais. Il protège du DÉCLASSEMENT d'un serveur qui avait déjà été vu chiffré, pas de la première rencontre.

IL RESTE SILENCIEUX DANS LES DEUX CAS OÙ IL CRIERAIT AU LOUP. Si le serveur ne répond pas du tout à la question sur les capacités, la liste arrive vide, et une liste vide ne signifie pas "STARTTLS supprimé" : rien n'est signalé. Et sur les serveurs déjà chiffrés dès le premier octet (TLS implicite, généralement port 563), rien n'est signalé non plus en les regardant, car ils n'ont aucune raison d'annoncer un passage inutile - sinon chaque serveur chiffré deviendrait une alarme permanente.

PAS DE TRAFIC SUPPLÉMENTAIRE, AUCUN NOUVEAU PARAMÈTRE. La donnée était déjà en main : NeReO lit les capacités à chaque connexion AVANT de tenter STARTTLS, depuis la 1.3. La mémoire est par ADRESSE ET PORT et non par ligne du tableau des serveurs, car lors de la restauration d'une sauvegarde, les serveurs prennent de nouveaux identifiants et une mémoire par identifiant aurait fini sur le mauvais serveur - une fausse alarme est pire que le silence.

***  VERSION 1.4.10  ***

Cinq points : trois d'ENTRETIEN que personne n'a demandés, et deux signalés par des utilisateurs. Nous le disons au lieu de le taire, car ceux qui ouvrent les nouveautés et ne trouvent rien de nouveau ont le droit de savoir pourquoi le numéro a changé.

DANS LA LISTE DES GROUPES, UN NOM LONG SE RACCOURCIT au lieu d'être tronqué. Il supprime le milieu de la hiérarchie au lieu de couper la fin avec des points de suspension, et seulement lorsqu'il ne rentre vraiment pas ; les noms courts restent intacts et les groupes avec des messages non lus restent en gras. Le composant partagé qui fait ce travail était déjà utilisé une soixantaine de lignes plus bas dans le même écran : seul ce point avait été laissé de côté, et pour une raison technique et non de produit - il fallait du gras et le composant n'a pas de paramètre pour le poids de la police. Il n'en avait pas besoin d'un : il a déjà le style, et le gras est un attribut du style.

SOUS LE CAPOT, COMPORTEMENT IDENTIQUE. L'appel obsolète fallbackToDestructiveMigration() est remplacé par l'équivalent que Room lui-même prescrit dans son propre ReplaceWith ; et les deux fichiers qui gèrent le retour à la ligne et le Format=Flowed se DÉPLACENT dans leur propre paquet "text", clôturant la dette déclarée dans la 1.4.8 - ils étaient parmi les écrans, et le niveau des données allait pêcher là-dedans. Le déplacement est vérifié par le banc d'essai qui compile ces deux VRAIS fichiers avec kotlinc : il passe toutes les assertions au nouveau chemin.

NOTE POUR CEUX QUI SURVEILLENT LES NUMÉROS : 1.4.10 est le premier numéro à deux chiffres du projet. Le contrôle des mises à jour compare les versions segment par segment COMME DES NOMBRES, donc 1.4.10 est plus récent que 1.4.9 - vérifié avant de l'adopter, car une comparaison textuelle aurait dit le contraire et aurait cessé de voir les nouvelles versions en silence.

LE PANNEAU DES ACTIONS, en maintenant enfoncé un message, défile désormais. Lorsqu'il y avait des messages de vous dans la discussion, un élément supplémentaire apparaissait et les dernières actions - dont "Retirer du serveur" - restaient coupées en bas, sans moyen de les atteindre. Il ne se bloquait pas : il ne défilait pas. Le même panneau dans la vue en panneaux avait le même défaut et il est corrigé aussi, car une correction appliquée à une copie sur deux est la façon dont ce projet s'est déjà fait mal. (Signalé par Macchia, qui a isolé la condition tout seul)

LE "+" OUVRE LA DISCUSSION même lorsque vous avez déjà lu les réponses. Dans la vue à discussions groupées, avec "seulement non lus" activé, une discussion dont les réponses étaient toutes lues affichait toujours le "+" et le nombre de réponses à côté - mais en le touchant, rien n'apparaissait. Le filtre décide de ce que montre la LISTE ; toucher le "+" est une demande explicite de voir UNE discussion, et maintenant le geste explicite l'emporte sur la préférence générale. Signalé en cinq mots par un utilisateur qui pensait se tromper : il ne se trompait pas. Une commande sur laquelle on peut appuyer mais qui ne fait rien est pire qu'une commande absente.

***  VERSION 1.4.9  ***

Trois points. Les deux premiers de Jeff, un qui ENLÈVE et un qui AJOUTE ; le troisième d'un utilisateur avec un téléphone qu'aucun testeur ne possède. Le premier rend désactivable un ornement qu'il avait pris pour un autre ; le second répond à une de ses questions d'une ligne. Le deuxième point déclare également son propre revers dans le corps du texte : un message conservé n'est plus vu par la recherche. Ceux qui conservent le font pour retrouver, et une note qui tairait cette limite vendrait une fonction différente de celle qui existe.

UN INTERRUPTEUR POUR LES RAILS VERTICAUX DU RETRAIT (Apparence, juste en dessous du style des citations). Ce sont les lignes grises qui dans les listes marquent chacune un niveau de profondeur, activées partout depuis la 1.4.7. Jeff avait choisi le style de citation "seulement les >" et voyait toujours des lignes verticales : ce n'était PAS une régression - la conception du corps du message est identique octet par octet depuis la 1.4.6 - c'étaient les rails, c'est-à-dire un ornement différent qui y ressemble. L'indice qui les distingue était devant lui : les barres de citation prennent la COULEUR DU NIVEAU, les rails sont d'un gris unique. Un indice visible par tous n'est pas un indice remarqué par quelqu'un. Donc on ne corrige pas : on rend optionnel, et ACTIVÉ par défaut, car ces rails avaient été demandés par Dave Royal. L'interrupteur est sous le style des citations car c'est là que Jeff a cherché. En les désactivant, le retrait reste, seules les lignes disparaissent. (Signalé par Jeff)

"CONSERVER CE MESSAGE". Nouvelle action dans la barre de lecture, et dans le menu à trois points dans la vue en panneaux : enregistre le message sous forme de fichier .eml dans un dossier que vous choisissez UNE SEULE FOIS (Entretien -> Messages conservés). À partir de ce moment, conserver ne prend qu'un toucher. "Enregistrer sous fichier .eml" reste à sa place et continue de demander à chaque fois où mettre le fichier : c'est la bonne forme pour le tour occasionnel vers un client de messagerie, et c'est la raison pour laquelle avec celle-ci personne ne mettait de côté un message après l'autre. Un message conservé SORT DE L'ARCHIVE LOCALE et devient votre propre fichier : il n'est pas emporté par le nettoyage automatique, ni par un désabonnement, ni par un retéléchargement. En échange, LA RECHERCHE DE NEREO NE LE VOIT PLUS - la recherche lit l'archive locale, et ce message en est sorti ; pour le relire, vous ouvrez le fichier avec une autre application. Il ne s'appelle pas "archive" à dessein : le nom est la promesse, et une archive promet liste, recherche et permanence, c'est-à-dire trois choses que cet élément ne donne pas. Il naît d'une question de Jeff, "NeReO conserve-t-il les messages ?" : la réponse était non, et maintenant c'est "si vous le lui demandez". (D'après une question de Jeff)

LE CHAMP DU MESSAGE NE S'ÉCRASE PLUS. Sur un petit écran, avec de gros caractères et un clavier haut, les lettres tout juste écrites semblaient COUPÉES EN DEUX. Signalé par un utilisateur sur un Samsung J6, et reproduit ici en augmentant la taille du texte et en ouvrant "Expéditeur et en-têtes". Ce n'était pas le clavier qui couvrait le champ - la barre avec "Envoyer" était à sa place au-dessus : c'était le champ qui ne recevait presque aucune hauteur, car le module d'écriture ne défilait pas et le champ prenait "ce qu'il reste". La hauteur minimale était déjà dans le code, avec à côté un commentaire qui la déclarait GARANTIE ; elle ne l'était pas, car un minimum ne peut pas demander plus que le maximum qu'il reçoit. Maintenant, le module défile et le minimum est un minimum. Le même utilisateur, avec un clavier plus bas, réussissait à écrire : le clavier n'était pas la cause, c'était la condition qui révélait le défaut.

***  VERSION 1.4.8  ***

Sept points : deux sur les filtres, trois de BIG Umberto sur le chemin "chercher -> ouvrir -> regarder où c'est", qui est la fonction qu'il avait demandée dans la 1.4.6 - il teste sa propre demande et en a trouvé les trois trous. Le sixième est en dehors de l'application, et le septième est le seul de cette version qui change CE QUE NEREO ENVOIE - c'est pourquoi il naît désactivé. Le deuxième point dit ce qu'il ne fait PAS : la commande "Appliquer maintenant" couvrait deux actions sur quatre, cette version en ajoute une des deux manquantes, et taire l'autre ferait croire avoir une fonction qui n'existe pas.

"APPLIQUER MAINTENANT" VAUT ÉGALEMENT POUR LES RÈGLES QUI AJOUTENT. Une règle "Mettre en évidence", ou un score mis manuellement en mode avancé, ne s'appliquait qu'aux messages qui arrivaient ensuite : sur l'historique, rien ne se voyait. La cause n'était pas un filtre qui évaluait mal - le moteur calculait le bon score et la fonction qui applique les règles à l'historique LE JETAIT, car elle ne l'enregistrait que dans la branche qui sert à masquer. Et dans NeReO, "mis en évidence" n'est pas un marqueur en soi : c'est le score que la liste des discussions lit pour colorer la ligne. Pas de nouvelle colonne, pas de migration : il manquait une écriture. Le résumé final a maintenant un troisième chiffre à côté des éliminés et masqués. (Signalé par Cercaparole sur Usenet)

LIMITE DÉCLARÉE, POUR LA MOITIÉ QUI MANQUE. Une règle "Suivre le fil" appliquée à l'historique donne maintenant le score - la ligne se colore - mais N'ATTRIBUE PAS encore l'étoile des discussions suivies ; et la case "étendre aux réponses" continue de MASQUER les branches quelle que soit l'action de la règle, même lorsque l'action est "suivre". Ce sont des écritures qui touchent de nombreuses lignes de la base de données et qui sont testées une par une. Les messages déjà masqués et les discussions que vous suivez ne sont pas touchés, comme avant.

LA CIBLE FONCTIONNE AUSSI SUR LES RÉPONSES. Depuis les résultats de la recherche, "Afficher dans la liste" ne ramenait à la discussion que si le message était le PREMIER POST ; dans la vue des discussions groupées, une réponse n'était pas mise en évidence. L'arborescence et la vue séquentielle étaient en ordre, et ce n'était pas une différence entre les vues : dans la vue groupée, une discussion fermée est UNE SEULE LIGNE, donc pour une réponse, il faut d'abord ouvrir la discussion - et c'était cette étape qui sautait, car elle était tentée alors que la liste des messages n'était pas encore arrivée de la base de données. (Signalé par BIG Umberto)

DANS LA RECHERCHE, LE TIRET BAS ET LE POURCENTAGE SE CHERCHENT EUX-MÊMES. C'étaient des caractères génériques de la base de données : chercher _114 signifiait "n'importe quel caractère, puis 114", et parmi les résultats apparaissaient des messages sans aucun tiret bas. L'indice qui les trahissait : dans ces lignes, la mise en évidence jaune n'apparaissait pas, car l'objet ne contenait pas vraiment le texte recherché. Il vaut la peine de rappeler que la recherche examine l'OBJET, L'AUTEUR ET LE CORPS des messages téléchargés, donc un résultat sans jaune dans l'objet peut avoir trouvé le mot dans le texte. (Signalé par BIG Umberto)

LA FLÈCHE RETOUR NE REFAIT PLUS TOUT LE CHEMIN. Après "Afficher dans la liste", la liste s'ouvrait AU-DESSUS du message d'où on était parti, et reculer le faisait réapparaître avant d'arriver là où on le voulait. Maintenant, le message sort de la scène et la flèche ramène aux RÉSULTATS DE LA RECHERCHE, d'où l'on passe au suivant. (Signalé par BIG Umberto)

HORS DE L'APPLICATION : L'HISTORIQUE DES VERSIONS À LA FIN DE CE FICHIER. C'était UNE SEULE LIGNE de près de quatre mille caractères, avec tous les éléments de la 1.2.7 à la beta1 collés à la suite - un tableau lu comme un paragraphe par un reformatage de fin septembre. Et il repartait de 1.2.7, c'est-à-dire qu'il répétait sous forme courte SEPT VERSIONS DÉJÀ DÉCRITES EN DÉTAIL ci-dessus. Maintenant, c'est une ligne par point et ça commence à la 1.1, la première que les sections ne décrivent pas. Dès la prochaine publication, en outre, ce README italien est également chargé déjà extrait dans le dossier sur SourceForge, à côté du fichier anglais. (Les deux signalés par le Mérovingien)

FORMAT=FLOWED EN SORTIE, DÉSACTIVÉ PAR DÉFAUT (Entretien -> Mise en page des messages que vous envoyez). Activé, les longs paragraphes sortent avec la rupture FAIBLE de la RFC 3676 - un espace à la fin des lignes que NeReO coupe - au lieu d'un retour à la ligne fixe à 72 colonnes : ceux qui lisent avec un programme qui connaît le format voient le paragraphe recomposé à la largeur de leur propre écran, même sur un téléphone étroit, et ceux qui lisent avec un programme qui ne le connaît pas voient les lignes à 72 colonnes comme toujours. La signature n'est pas touchée, et l'espace après la marque de citation reste à sa place - la norme le permet "to maintain the relative indentation of the lines when viewed by clients which are not aware of Format=Flowed". C'est LE SEUL PARAMÈTRE QUI CHANGE CE QUI SORT DE NEREO, et un message publié ne se retire pas : c'est pourquoi il naît désactivé, et le format n'est déclaré que lorsqu'il y a vraiment quelque chose à recomposer. NeReO sait LIRE le f=f depuis la 1.4.6, il peut donc vérifier sa propre sortie par lui-même.

***  VERSION 1.4.7  ***

Six points, et cinq viennent de deux testeurs. Ils ont tous la même forme : quelque chose qui existait pour un écran et pas pour son écran frère. C'est la version des JONCTIONS - les points où deux parties de l'application, nées à des moments différents, avaient cessé de se ressembler sans que personne ne le décide.

LES RAILS VERTICAUX DE L'ARBRE apparaissent dans les trois vues : liste des messages sur le téléphone, arbre de la discussion et vue en panneaux. Avant, ils n'étaient que dans les panneaux. Dave Royal a demandé si c'était un choix de conception : ce n'en était pas un. La même formule du retrait était écrite dans TROIS fichiers différents et un seul dessinait également les rails, donc cette différence n'avait jamais été décidée par personne - c'était une copie restée en arrière. Unifiée en un seul point et activée partout en deux builds séparés : d'abord l'extraction à rendu invariable, puis le nouvel aspect, de sorte qu'une panne n'aurait eu qu'une seule cause à chercher. (Signalé par Dave Royal)

CET ÉCRAN S'APPELLE "arbre de la discussion", dans les deux langues. Il s'appelait "arbre de la conversation" alors que le reste de l'application dit "discussion" partout, et se contredisait de lui-même : le titre disait conversation, le message d'erreur disait discussion. Ce n'est pas une question de style : une conversation est un ensemble de messages ESTIMÉ par les objets, ce que font les programmes de messagerie ; une discussion est un GRAPHE DE RÉFÉRENCES DÉCLARÉES, ce que NeReO construit vraiment. (Signalé par Dave Royal)

DANS APPARENCE, IL Y A UN INTERRUPTEUR pour ne montrer que les messages non lus. Le paramètre a toujours existé et naît activé, mais on ne pouvait le modifier que par l'icône en forme d'enveloppe au-dessus d'une liste, et nulle part dans les Paramètres. Un testeur au premier jour d'utilisation a cherché où le paramètre devrait se trouver, ne l'a pas trouvé et a conclu que l'application SUPPRIMAIT ses messages lus. Rien n'a jamais été supprimé : l'interrupteur n'était pas là où n'importe qui l'aurait cherché. (Signalé par Jeff)

LE RETÉLÉCHARGEMENT D'UN GROUPE démarre également depuis la liste des groupes, en maintenant appuyé. Avant, il n'était que dans le menu à l'intérieur du groupe, alors que tout le reste de cette famille - télécharger les corps, se désabonner, mettre à jour tous les groupes de ce serveur - habitait déjà dans la liste. C'était la commande qui n'était pas à sa place, pas l'endroit où on la cherchait. (Signalé par Jeff)

LA BOÎTE DE DIALOGUE DU RETÉLÉCHARGEMENT indique combien de messages reviendront réellement. Retélécharger NE restaure PAS : cela reprend du serveur autant d'en-têtes que prévus par les paramètres de synchronisation, et ce que vous aviez de plus ancien en local est perdu à jamais. Un groupe de 415 messages en a repris 298, car le paramètre était de 300. Le dialogue avertissait sur l'état de lecture, les drapeaux et les corps, mais pas sur le risque de se retrouver avec MOINS de messages qu'avant. Le nouvel avertissement porte le numéro réel de votre configuration.

LA LISTE DES GROUPES SE SOUVIENT D'OÙ VOUS ÉTIEZ. En défilant vers le bas pour chercher les groupes avec de nouveaux messages, en entrant dans l'un et en revenant en arrière, la liste repartait du début : avec des dizaines de groupes sur deux serveurs, cela signifiait tout faire défiler à chaque fois. On n'enregistre pas la ligne, mais CE QU'IL Y AVAIT en haut, de sorte que se désabonner d'un groupe n'envoie pas la position vers un point aléatoire. (Signalé par Jeff)

***  VERSION 1.4.6  ***

Neuf points, et le tiroir est vide. Le premier bug ouvert du projet - le respect du Followup-To, signalé par Dave Royal le 21 septembre - se ferme ici. Six bugs sur neuf ont été trouvés par Marco en test, après qu'on lui ait dit que tout était en ordre.

LE FOLLOWUP-TO ne se perd plus en ouvrant le sélecteur de groupes. Lorsque l'auteur demande que les réponses se poursuivent ailleurs, le sélecteur part des groupes qu'IL a indiqués au lieu de celui que vous lisez, et les affiche même si vous n'êtes pas abonné : quelqu'un d'autre les a choisis, vous ne devriez pas avoir à vous y abonner pour répondre là où on vous l'a demandé.

Ainsi, une destination comme "seulement uk.test", qui avant n'était même pas exprimable, s'obtient en confirmant sans rien ajouter. Et ouvrir le sélecteur pour REGARDER ne jette plus la demande : c'était le pire cas, car le geste de contrôle détruisait justement ce qu'on voulait contrôler.

Enfin, l'avertissement en haut de la rédaction et la ligne qui dit où sortira le message lisent maintenant la même valeur : avant ils pouvaient se contredire à une ligne de distance. (Signalé par Dave Royal, avec les tests 9A et 9B.)

LA VUE EN PANNEAUX gagne les commandes de déplacement, qui jusqu'à hier n'existaient que dans la vue en pages : message précédent et suivant, premier non lu, discussion précédente et suivante, début et fin du message, "recharger". Pour arriver au message d'après, il fallait faire défiler la liste et toucher - et avec une télécommande, ce défilement est la partie difficile de la journée.

Les nouvelles entrées naissent dans le menu, pour ne pas changer la barre sous les doigts de ceux qui se la sont déjà arrangée ; deux touches dans l'éditeur des barres les amènent parmi les icônes. Une commande qui dans un certain panneau ne peut pas être utilisée se présente désactivée, au lieu de ne rien faire. (Né de la façon de lire de Roxy, sur une box TV.)

L'OBJET DU MESSAGE ne se réduit plus à une colonne de lettres. Dans la vue en panneaux sur un panneau étroit, l'en-tête et la barre se disputaient la ligne : la barre était mesurée en premier et prenait ce qu'elle demandait, au texte revenait ce qu'il restait - et sur un panneau plus étroit que la barre, il ne restait rien.

Le fait n'était pas que la barre soit longue : c'était que c'était toujours la seule chose qu'on doit pouvoir lire qui payait. Maintenant, sous une certaine largeur, la barre descend sous l'en-tête, et va à la ligne elle-même au lieu de se faire couper la dernière icône - qui était le bouton du menu, c'est-à-dire la voie pour arriver à tout le reste. (Trouvé par Marco sur la configuration qu'aucun testeur n'a : les panneaux sur un téléphone tenu horizontalement.)

NeReO LIT LE FORMAT=FLOWED (RFC 3676), la façon dont presque tous les lecteurs modernes disent où se termine vraiment un paragraphe. Jusqu'à hier, il le devinait : il estimait la largeur de mise en page et unissait les lignes qui semblaient s'appartenir. Ça marchait presque toujours, et "toujours" et "presque toujours" sont deux choses différentes.

Maintenant, lorsque celui qui écrit le déclare et l'utilise vraiment, on n'estime plus rien : les lignes qui continuent ont été marquées par lui, une par une. Pour les messages qui ne le déclarent pas, rien ne change.

"AFFICHER DANS LA LISTE", depuis la lecture d'un message : amène à la liste du groupe, ouvre la discussion qui le contient et le met en évidence. Naît d'une phrase exacte - "la recherche fonctionne, mais t'ouvre le post, ne te fait pas voir où il est" - et il avait raison à la lettre : d'un résultat on finissait dans le message sans savoir dans quelle discussion il était ni qui l'entourait. (Signalé par BIG Umberto.)

L'ÉTIQUETTE EN HAUT DE LA RÉDACTION indique où ira le message, et non plus le groupe que vous lisiez. Avec un Followup-To vers un seul groupe, les deux choses avaient cessé de coïncider, et l'écran déclarait le faux à une ligne de distance de l'avertissement qui disait le vrai. (Test 9A de Dave Royal.)

DANS LES PANNEAUX, LE CHOIX DES GROUPES de réponse n'est plus écarté. La boîte de dialogue s'ouvrait, on choisissait, et un instant après la demande de l'auteur passait par-dessus en silence : le message sortait là où le disait l'en-tête, pas là où vous l'aviez envoyé. Ce n'était pas un dialogue confus : c'était une décision de l'utilisateur jetée. (Test 9B de Dave Royal.)

***  VERSION 1.4.5  ***

Six points. Deux étaient restés en arrière - parqués dans une liste qui a été remplacée, et jamais transportés dans la nouvelle - et un naît d'un signalement qui semblait esthétique et qui ne l'était pas.

SUIVRE UNE DISCUSSION suit maintenant aussi son futur, pas seulement le passé. Jusqu'à présent "suivre la discussion" marquait les messages qui existaient au moment où vous appuyiez : les réponses qui arrivaient ensuite naissaient non suivies, c'est-à-dire l'exact contraire de ce que vous aviez demandé.

Et ce n'était pas seulement l'étoile qui manquait. Le marqueur protège également des filtres et de l'effacement automatique, donc les messages plus nouveaux d'une discussion que vous suiviez étaient MOINS protégés que les anciens, et la discussion pouvait se vider par le centre sans rien dire.

Maintenant une réponse hérite du marqueur de n'importe quel ancêtre suivi, à n'importe quelle profondeur : il tient même si un message au milieu de la chaîne n'a jamais été téléchargé.

Valable pour les messages qui arrivent dorénavant. Les réponses déjà dans les archives restent telles quelles : pour remettre à niveau une ancienne discussion, enlevez et remettez le "suivre". (signalé par BIG Umberto, qui l'avait défini comme "seulement une question esthétique" - ce ne l'était pas)

UN MERCI À DAVE ROYAL, et ce n'est pas une formalité. Depuis la 1.4, la majeure partie de ce qui a été corrigé dans NeReO naît d'un de ses signalements : le Followup-To, le menu de mise à jour, les menus des trois petits points qui s'ouvraient au mauvais endroit, le "tout marquer comme lu" dans la vue en panneaux, les réponses aux crossposts sur le téléphone.

Ce qui les rend précieux n'est pas le nombre. Il dit aussi où le défaut N'EST PAS, et fait économiser la moitié de l'enquête ; il nous corrige quand nous le comprenons mal au lieu de laisser couler ; il accepte un compromis et dit lequel ; il envoie des dessins, pas seulement des plaintes ; et il tient des cas de test prêts sur uk.test et it.test, afin que la correction se vérifie ensuite sur le même terrain.

"TOUT MARQUER COMME LU" revient dans la barre de la vue en panneaux, où il coûte à nouveau un toucher au lieu de deux. Il avait été retiré dans la 1.4.1 pour une bonne raison - il était collé au filtre des non lus et on appuyait par erreur - mais la vue en pages avait reçu un meilleur remède pour le même problème : là, l'icône avait été DÉPLACÉE loin du filtre, pas enlevée. Maintenant les panneaux ont le même remède, dans la même position, et la voix sort du menu : une seule voie par vue, et la même dans les deux. "Tout marquer comme non lu" reste dans le menu, car celle-là est vraiment rare. (signalé, deux fois et sur deux choses différentes, par Dave Royal)

"TOUJOURS RÉPONDRE À TOUS LES GROUPES" fonctionne aussi sur le téléphone. Le paramètre existe depuis la 1.2.7, mais il n'était lu qu'à un seul endroit : l'ouverture du dialogue du crosspost - et dans la vue en pages, on va directement en écriture, sans passer par là. Donc il était activé et ne faisait rien : l'écran montrait le seul groupe que vous lisiez, jusqu'à ce que vous ouvriez le sélecteur à la main. Maintenant la réponse à un article crossposté naît avec tous les groupes de l'original, jusqu'à la limite prévue. Si l'auteur a demandé de continuer ailleurs, sa demande continue de gagner. (signalé par Dave Royal)

UN ARTICLE PEUT ÊTRE SAUVEGARDÉ EN FICHIER .eml, qu'un client de messagerie ouvre comme un message au lieu de se le retrouver collé dedans. C'est l'autre moitié d'une demande de la 1.3.6 : alors la réponse fut le partage de l'article brut, qui sert à le lire ailleurs mais pas à le conserver. Le fichier est écrit avec les en-têtes d'encodage réécrits pour correspondre à ce qu'il contient vraiment : NeReO sauvegarde les corps déjà décodés, donc recopier le Content-Type original aurait fait mentir le fichier sur lui-même et chaque accent serait sorti faux. C'est une action de la barre comme les autres, donc on peut la déplacer dans le menu ou l'enlever d'Apparence -> Personnaliser les barres. (demandé par Adam H. Kerman)

L'INTRODUCTION À LA CITATION peut maintenant porter aussi l'OBJET du message cité, avec %objet% (ou %subject%). Elle s'ajoute aux espaces réservés qui y étaient déjà - auteur, e-mail, date, heure, groupe et Message-ID - et vaut aussi dans les réponses normales, pas seulement en citant d'un autre groupe. (demandé par Ammammata)

***  VERSION 1.4.4  ***

Cinq points : trois ferment des signalements de Roxy - deux desquels n'étaient pas "ça ne marche pas", c'étaient "ça n'y est pas" - et deux arrivent de Dave Royal.

Le bouton COLLER est maintenant présent aussi dans le panneau de rédaction de la vue en PANNEAUX, où il n'avait jamais été. Tout le travail fait sur ce bouton - demandé dans la 1.3.3, rendu toujours visible dans la 1.4.1, et appris dans la build 106 à lire le presse-papiers au clic plutôt que pendant que la barre se dessine - n'avait été fait que sur la vue en pages, car dans les panneaux ce bouton n'existait tout simplement pas et il n'y avait rien à corriger. (signalé plusieurs fois par Roxy, qui avait raison à chaque fois)

"RETIRER DU SERVEUR (cancel)" apparaît maintenant aussi dans le menu contextuel de la vue en panneaux. Dans la 1.4.2, il avait été laissé de côté à dessein : il emporte avec lui trois boîtes de dialogue - confirmation, attente, résultat par serveur - et les recopier aurait été exactement le défaut dont parle cette version. Ils ont été déplacés à un seul endroit et maintenant les deux vues exécutent le même code. Comme avant, il n'apparaît que sur vos messages, et comme avant il envoie un message de contrôle sur Usenet. (signalé par Roxy)

Avec un SEUL serveur configuré, l'icône de mise à jour ne demande plus quels serveurs vous voulez mettre à jour : elle met à jour et c'est tout. Le menu continue d'apparaître quand les serveurs sont plus d'un, où le choix veut dire quelque chose. Jusqu'à présent, il s'ouvrait quand même, proposant deux choix qui faisaient la même chose et une boîte de dialogue dans laquelle cocher l'unique serveur que vous possédez : pas un choix, une formalité. (signalé par Dave Royal)

LE MENU DES TROIS POINTS s'ouvre sous l'icône que vous avez touchée, même dans la liste des discussions de la vue en pages. Là, il apparaissait à l'extrême gauche de l'écran : sur une tablette, à une main de distance de l'icône. La cause : ces actions se trouvent dans une ligne à elles, large comme l'écran, et un menu sans rien autour s'accroche à celui qui le contient, donc il se mesurait sur la ligne entière. Dans les colonnes étroites de la vue en panneaux, ce même défaut est invisible, et c'est la raison pour laquelle personne ne l'avait jamais signalé. Un recensement a trouvé douze paires icône+menu et huit sans conteneur propre : maintenant elles en ont toutes un, pas seulement celle trouvée. (signalé par Dave Royal)

LES GROUPES HOMONYMES SUR DES SERVEURS DIFFÉRENTS se distinguent à nouveau. Dans la vue en PANNEAUX, la colonne des groupes était une liste unique qui ne disait jamais de quel serveur venait un groupe - et elle n'était même pas ordonnée par serveur, donc les groupes des deux serveurs sortaient intercalés et deux noms identiques pouvaient finir l'un à côté de l'autre. Maintenant elle a un en-tête par serveur, comme la vue en pages a toujours eu. Et dans LES DEUX vues, la barre au-dessus de la liste des discussions indique le serveur sous le nom du groupe : jusqu'à présent, une fois entré dans le groupe, il n'y avait plus rien pour vous dire lequel des deux vous lisiez. Avec un seul serveur configuré, rien ne change, car il n'y a rien à distinguer.

Au même passage, deux choses mineures. L'en-tête montre maintenant le NOM que vous avez donné au serveur et non son hôte : c'est le seul à être différent si vous avez deux comptes sur le même fournisseur. Et les groupes restés orphelins d'un serveur effacé ne disparaissent plus de la liste - ils se rassemblent en bas sous "?", où le nettoyage de la base de données peut les trouver. (signalé par Roxy)

***  VERSION 1.4.3  ***

Trois corrections, toutes nées d'un seul message d'un testeur qui avait activé le journal de diagnostic.

Les listes ne deviennent plus SOURDES. Il pouvait arriver - presque toujours sur un grand groupe, et juste après une mise à jour - que la liste ouverte cesse de montrer les nouveaux messages : la pastille du groupe les comptait, la base de données les contenait, mais la liste restait figée jusqu'à ce qu'on sorte du groupe et qu'on y rentre. En dessous, il y avait une lecture de la base de données qui échouait, et autour une défense mal écrite : au lieu de se remettre à l'écoute, la liste se fermait pour toujours. Maintenant, elle se répare d'elle-même en un instant. Sans le journal de diagnostic allumé et le log envoyé par celui qui s'y cognait, ce bug serait encore une suspicion au lieu d'une correction. (signalé et diagnostiqué par BIG Umberto)

Le bouton Annuler du diagnostic LISTGROUP fonctionne. Il était désactivé pendant tout le temps de l'opération, et sur un groupe de cent soixante-dix mille articles, cette attente dépasse la minute et demie : la seule façon d'en sortir était de forcer la fermeture de l'application. Maintenant, il interrompt vraiment, et ce qui était déjà enregistré reste dans le journal. (signalé par BIG Umberto)

Les lignes restées orphelines d'un serveur effacé avec la 1.4.1 ou avant peuvent enfin être supprimées. La 1.4.2 se limitait à les compter, en bas du rapport du Diagnostic de la base de données : la mesure est arrivée - trois groupes et 99 906 lignes de catalogue sans serveur, sur une base de données de 36 Mo - et avec un vrai chiffre devant, le nettoyage a cessé d'être une idée à moi. Le bouton se trouve juste en dessous du rapport, et avant de supprimer, il vous dit combien de lignes partiront, ligne par ligne. (mesuré par BIG Umberto)

***  VERSION 1.4.2  ***

Sept corrections. Aucune nouvelle fonction.

"Recharger le message" recharge vraiment. Dans la 1.4 et la 1.4.1, ce bouton de la barre de lecture SUPPRIMAIT l'article au lieu de le retélécharger : il disparaissait de la liste avec l'état lu, le "suivi", le score des filtres et le marqueur des réponses à vous-même, et ne revenait plus même en mettant à jour le groupe, car la mise à jour repart du dernier article déjà téléchargé. La cause était le nom d'une fonction : eliminaCopiaLocale sonnait comme "enlève la copie en cache" et au lieu de ça faisait DELETE FROM articles. Maintenant, le bouton remet à zéro SEUL le texte du message et le redemande au serveur, et cette fonction s'appelle eliminaArticoloLocale, ce qu'elle fait. (signalé sur le groupe, avec analyse technique jointe)

Vos messages redeviennent "à lire" comme n'importe quel autre message. La 1.4.1 les faisait naître déjà lus : cela semblait pratique et en fait ça ne l'est pas, car après avoir publié on veut RETROUVER son propre message, pour voir comment il est rendu et où il s'est accroché dans la discussion, et avec le filtre "seulement non lus" activé il n'était simplement pas là. Aucun autre lecteur ne se comporte ainsi : ni PAN, ni Thunderbird, ni Piao Hong, ni MesNews. De plus, la règle était plus large qu'elle n'en avait l'air : à la première synchronisation d'un groupe, elle marquait comme lu TOUT votre historique là-dedans. (signalé par Dave Royal)

On n'appuie plus sur "Tout marquer comme lu" par erreur. Une action rare et avec des conséquences se trouvait à côté d'une fréquente et inoffensive, le filtre des non lus. Sur le TÉLÉPHONE, elle échange sa place avec "Mettre à jour" ; dans la VUE EN PANNEAUX, où la barre est plus dense et la même action était déjà dans le menu des trois points, l'icône dans la barre a été ENLEVÉE : c'était un doublon, et ainsi le contact par erreur devient impossible au lieu de moins probable. Dans les deux dispositions, les icônes ont maintenant le même ordre. (signalé par Dave Royal)

Le crosspost fonctionne aussi en ouvrant une NOUVELLE discussion depuis la vue en panneaux. Le sélecteur des groupes n'y était qu'en répondant à un message déjà crossposté : en commençant une discussion de zéro il n'apparaissait pas, alors que dans la vue en pages il y est toujours. C'est la même forme de défaut que la 1.4 avait corrigé ici pour les RÉPONSES, restée découverte sur l'autre branche du même "if". (signalé par Dave Royal)

"Remplacer (supersede)" apparaît maintenant aussi dans le menu contextuel de la vue en PANNEAUX, où il n'avait jamais été : il n'existait que dans la vue en pages. Comme avant, il n'apparaît que sur vos messages. "Retirer du serveur (cancel)" reste pour le moment dans la seule vue en pages : cette partie doit d'abord être réorganisée pour ne pas en avoir deux copies, et elle envoie des messages de contrôle sur le réseau, donc elle mérite son propre test. (signalé par Roxy)

Supprimer un serveur emporte vraiment ce qui lui appartient, et vous le demande d'abord, en disant combien de groupes et combien de messages partiront. Jusqu'à la 1.4.1, il suffisait d'un appui sur la poubelle, sans questions, et la seule ligne du serveur disparaissait : ses groupes et tous ses messages restaient dans la base de données POUR TOUJOURS, invisibles et inatteignables, pour occuper de l'espace et gonfler les nombres du diagnostic. Si ça vous est déjà arrivé, le Diagnostic de base de données dans Entretien vous le dit maintenant : en bas du rapport apparaît un bloc "ORPHELINS" avec les décomptes. Pour le moment, il se contente de les COMPTER : les enlever sera l'étape suivante, et je veux d'abord savoir de quels nombres nous parlons.

En supprimant une discussion entière, la pastille des non lus du groupe descend tout de suite. Avant, elle restait figée au nombre d'avant jusqu'à la mise à jour suivante : c'était la seule suppression qui oubliait de le recalculer.

***  VERSION 1.4.1  ***

Version uniquement de CORRECTIONS après la 1.4 publique. Aucune nouvelle fonction.

Le "lu" qui suit le Message-ID fonctionne maintenant vraiment. Il était annoncé dans la 1.4 et ne fonctionnait pour personne : la règle n'était reliée qu'aux éléments de menu "marquer comme lu", pas au fait de lire un message. Maintenant, chaque point passe par un seul endroit. Cela vaut aussi dans l'autre sens : "tout marquer comme à lire" propage à son tour, et peut ramener à non lus des messages dans d'autres groupes. (Dave Royal)

Vos messages ne redeviennent plus "à lire" après l'envoi. Lors du même passage, le critère "c'est à moi ?" a été renforcé dans les trois points restés en arrière par rapport à l'audit de la 1.2.12 : l'adresse est extraite de la ligne From et est comparée, au lieu de la chercher dans la ligne. (BIG Umberto)

Répondre ne fait plus perdre le repère : vous répondiez au 23ème message et retourniez au premier post. (BIG Umberto)

L'arborescence contextuelle suit "afficher les filtrés" comme la liste du groupe. Corrige aussi le faux "impossible de reconstruire la discussion" et le précédent/suivant qui sur un post filtré ne faisait rien. (Dave Royal)

Dans une discussion ouverte, le premier message affiche son propre état au lieu de celui de la discussion entière : fini les deux lignes rouges quand le non lu n'est qu'un seul. (Dave Royal)

Le décompte après une mise à jour dit combien des nouveaux ont été filtrés, au lieu d'annoncer des arrivées que vous ne verrez jamais. (Dave Royal)

Le bouton Coller en rédaction est toujours là, et maintenant il fonctionne là où avant non. Il n'apparaissait qu'avec le presse-papiers déjà plein, et NeReO les lisait pendant qu'il DESSINAIT l'écran au lieu de lors de l'appui : depuis Android 10, lire le presse-papiers nécessite le focus d'entrée, pas les écrire. Sur le téléphone il ne se voyait pas, car l'apparition du clavier fait redessiner la barre et la deuxième lecture réussit ; sans clavier à l'écran cette deuxième lecture ne se produit jamais. Maintenant il lit à la pression, et de la manière tolérante qu'utilisent les autres applications, ainsi arrive aussi le texte copié comme HTML ou comme lien. (diagnostic à partir de deux mesures de Roxy)

Les en-têtes personnalisés que NeReO se réserve sont annoncés AVANT d'envoyer. Certains ne sont pas inscriptibles : ceux vitaux (From, Subject...) et ceux de contrôle (Followup-To, Control, Sender, Expires...), qui existent pour qu'un texte collé d'une source externe ne devienne pas par distraction un message de contrôle. Jusqu'ici ils étaient jetés EN SILENCE - on écrivait une ligne valide, on envoyait, et cette ligne n'y était pas - et la note sous le champ donnait justement Followup-To comme exemple. Maintenant l'éditeur les liste pendant que vous écrivez, et la même règle alimente l'avertissement et l'envoi, ainsi les deux ne peuvent pas diverger. (signalé par Dave Royal)

"Tout marquer comme lu" et "Mettre à jour" échangent leur place dans la barre de la liste des messages. Une action rare et avec conséquences se trouvait à côté d'une fréquente et inoffensive - le filtre des non lus - et on appuyait dessus par erreur. La déplacer dans le menu aurait coûté un appui par jour à qui l'utilise chaque soir ; les échanger ne coûte rien à personne. (signalé par Dave Royal)

***  VERSION 1.4  ***

Publiée le 17 septembre 2026.

STARTTLS respecte maintenant les deux moitiés de la RFC 4642, au lieu d'une seule. Si un serveur REFUSE le STARTTLS avant le handshake, NeReO peut continuer en clair comme le permet la norme, et ça ne change pas. Mais si le serveur ACCEPTE (382) et puis le handshake échoue, la RFC 4642 (par. 2.2) demande aux deux parties de FERMER - SHOULD, c'est-à-dire une recommandation forte et non une obligation absolue - tandis que jusqu'à la 1.3.7 nous continuions en clair. Ce repli ne fonctionnait probablement même pas, car le ClientHello TLS avait déjà été écrit sur le socket ; mais le point est qu'il ne devait pas être tenté. Une telle connexion échoue maintenant avec un message explicite au lieu de continuer en silence sans chiffrement.

La base de données a été mise à jour (schéma version 12). Serveurs, groupes, articles, filtres, identités et brouillons sont migrés sur place et rien n'est effacé : et si jamais NeReO trouvait une version qu'il ne sait pas migrer, il en fait d'abord une copie physique et vous le dit. Les nouvelles structures ne sont pas encore utilisées par rien : elles y sont pour le travail sur la confiance et les certificats qui arrive dans cette même version. Comme toujours avant une version qui touche la base de données, une sauvegarde depuis Entretien ne coûte rien.

Une restauration pouvait assigner à un serveur un nom d'utilisateur qu'il n'avait jamais eu. Les serveurs sans compte finissaient correctement dans la sauvegarde, comme valeur vide, mais étaient relus comme le mot "null" : quatre caractères, que NeReO utilisait ensuite consciencieusement pour s'authentifier sur des serveurs qui ne veulent aucune authentification. Le serveur répondait "381 Enter password", NeReO envoyait un mot de passe vide, et le serveur répliquait avec une erreur de syntaxe qui ne disait rien à personne. C'était un seul contrôle manquant sur sept champs du même type, et c'était là au moins depuis la 1.2.8. Maintenant la restauration est correcte, et les serveurs qui portent encore ce faux nom d'utilisateur sont réparés à la première restauration d'une sauvegarde : le message dit combien.

NeReO n'envoie plus un mot de passe vide quand un serveur le demande. Il dit clairement que pour ce serveur il n'y en a aucun enregistré, et suggère de supprimer le nom d'utilisateur si le serveur ne demande pas de compte. Le contrôle est là où le serveur a vraiment demandé le mot de passe (réponse 381) : un serveur qui se contente du seul nom d'utilisateur répond 281 et continue de fonctionner, comme le veut la RFC 4643.

Dans Entretien il y a un nouveau panneau "Diagnostic de base de données". Il compte les lignes de chaque table et montre la version du schéma et l'espace occupé, avec un bouton Copier. Il existe parce qu'une migration de la base de données qui S'OUVRE n'est pas la même chose qu'une migration qui a FONCTIONNÉ : elle peut être formellement correcte et avoir quand même vidé une table, et l'application s'ouvrirait très bien - ce serait l'archive qui ne serait plus là. Prenez les nombres avant une mise à jour et à nouveau après : s'ils coïncident, on n'a rien perdu. C'est aussi la chose la plus rapide à joindre à un signalement. Rien n'est calculé jusqu'à ce que vous appuyiez sur le bouton.

En répondant à un crosspost on voit maintenant où le message ira vraiment. Jusqu'à présent l'écran d'écriture ne montrait que le groupe que vous lisiez, même quand la réponse était configurée pour sortir sur trois. Avec cela, deux autres défauts sont tombés : rouvrir le sélecteur des groupes pour CONTRÔLER le choix le ramenait en silence à la valeur par défaut (le pire cas possible, car le geste de contrôle détruisait ce qu'il contrôlait), et dans la vue en panneaux le bouton du crosspost n'existait pas du tout, donc pour changer les groupes il fallait abandonner la réponse et recommencer. Tous trois signalés par Dave Royal.

"Tous les messages" fonctionne maintenant aussi sur les serveurs à haute rétention. Demander l'historique complet d'un très grand groupe faisait demander tout l'intervalle en une seule fois, et la réponse pouvait dépasser le plafond de 64 Mo qui protège l'application de l'épuisement de la mémoire : erreur, et aucun message téléchargé. Maintenant la liste se demande par tranches, et chaque tranche est sauvegardée avant de demander la suivante. Le plafond reste où il était (le monter aurait déplacé le mur, pas enlevé) et en plus on gagne deux choses : une synchronisation interrompue à la moitié reprend au lieu de recommencer, et la mémoire utilisée ne dépend plus de la taille du groupe. Signalé par Bingo3331.

La confiance TLS s'accorde maintenant, elle ne se subit pas. Le chemin normal ne change pas. Ce qui change c'est ce qui se passe quand IL ÉCHOUE : au lieu d'une impasse, NeReO note l'identité que le serveur a présentée et la marque VUE, NON FIABLE, et la connexion échoue quand même. Vous l'approuvez après, avec calme, depuis Serveurs -> Identités TLS, jamais dans une boîte de dialogue au milieu d'une mise à jour : un avertissement de sécurité qui vous interrompt est ce qu'on clique sans lire. On note le SHA-256 de la clé publique de la feuille, jamais d'un intermédiaire. À partir de là, cette clé se compare à CHAQUE connexion, et si elle change NeReO s'arrête et vous les montre les deux, sans aucun bouton "accepter la nouvelle". La friction est la fonction. Il y a aussi une exception "certificat expiré" par ligne de serveur, qui relâche les dates et rien d'autre, et qui se désamorce d'elle-même le jour du renouvellement.

Avertissement "le certificat expire dans N jours". Le TrustManager voit le certificat à chaque connexion : la date d'expiration est gratuite, et ne pas l'utiliser aurait été gaspiller une donnée déjà en main. Naît d'un cas réel : un administrateur qui renouvelle À LA MAIN, et un certificat Let's Encrypt qui vit 90 jours. Cette situation n'est pas fermée, elle est cyclique.

Les accents ne se cassent plus dans les articles sans déclaration de charset ou avec une déclaration erronée. Sur sept cas mesurés, cinq perdaient le texte ; le pire était la déclaration ERRONÉE, qui ne produisait même pas un caractère d'erreur mais des déchets plausibles, que NeReO republiait en UTF-8 impeccable. Règle en trois étapes, dix cas vérifiés à chaque compilation. Et comme NeReO sauvegarde le texte DÉJÀ DÉCODÉ, il y a une nouvelle action "Recharger le message" pour la barre de l'article : sans cela, la correction serait invisible sur tout ce qui est déjà dans les archives.

Le "lu" suit le Message-ID. Un crosspost lu dans un groupe résulte lu aussi dans les autres : c'est un seul message, et c'est ce que fait MesNews. Discussions suivies et filtres raisonnaient déjà ainsi, seul le lu faisait exception. Défaut activé.

Followup-To lu et respecté. C'était le seul critère GNKSA que NeReO ne respectait pas. Si l'auteur demande que les réponses se poursuivent ailleurs, la réponse va LÀ, et l'écran d'écriture le DIT : détourner sans prévenir remplacerait une impolitesse par une surprise. Si vous ouvrez le sélecteur des groupes et choisissez vous-même, votre choix gagne. Le cas "poster" (expéditeur direct) ne bloque pas la publication mais avertit et propose l'adresse de l'auteur.

Introduction à la citation par groupe (demande de Henry The Mole). REMPLACE celle de l'identité au lieu d'entrer dans le tirage au sort : il faut un choix ciblé, typiquement la langue du groupe.

***  VERSION 1.3.7  ***

Deux signalements de Dave Royal sur la restauration de la sauvegarde. L'un était juste, et le défaut était plus étendu qu'il ne l'avait vu.

La restauration ne duplique plus les filtres et identités. Il avait remarqué que les filtres étaient AJOUTÉS au lieu d'être remplacés ; en regardant le code, il est apparu que les IDENTITÉS avaient le même défaut. La restauration est maintenant IDEMPOTENTE : restaurer deux fois le même fichier laisse exactement le même état. Les filtres se reconnaissent au nom, champ, type de comparaison, modèle et étendue des groupes ; les identités par le pseudonyme, que le format de sauvegarde utilisait déjà comme clé pour relier les serveurs.

La restauration demande maintenant comment procéder : Fusionner ou Remplacer. Le vrai défaut n'était pas "ajoute au lieu de remplacer" : c'était que dans la même opération l'application faisait TROIS choses différentes (déduplication sur serveurs et abonnements, insertion aveugle sur filtres et identités, écrasement sur les paramètres). Aucune des deux options ne touche les serveurs, abonnements ou articles téléchargés : effacer un serveur emporterait avec lui ses groupes et ses messages.

"Mot de passe erroné" ne couvre plus les erreurs d'importation. La boîte de dialogue déchiffrait ET importait en un seul bloc protégé : une panne dans la deuxième étape était annoncée comme un problème de la première, envoyant chercher la mauvaise cause.

Le passage à la ligne à l'envoi ne laisse plus d'espace à la fin des lignes qu'il casse. Un espace en fin de ligne est le marqueur de rupture faible du Format=Flowed (RFC 3676) : un lecteur qui l'interprète aurait réuni cette ligne avec la suivante, défaisant justement le retour à la ligne tout juste mis. Cela n'arrivait que quand la rupture tombait sur un double espace, loin d'être rare après un point. Les doubles espaces À L'INTÉRIEUR de la ligne restent comme vous les avez écrits, et le délimiteur de la signature conserve l'espace que la convention requiert. Demande de Adam H. Kerman.

"Fichier illisible" dit maintenant aussi pourquoi. Quand une sauvegarde ne s'ouvrait pas, le message était ces deux mots et rien d'autre : un fichier manquant, une permission refusée et un flux vide semblaient la même chose. Maintenant le détail technique apparaît dans une boîte de dialogue avec un bouton pour le copier, car c'est ce qui sert à un signalement. C'est Dave Royal qui l'a fait émerger, ayant répété la même épreuve six fois sur deux versions de l'application sans pouvoir nous dire rien de plus que "fichier illisible" - qui était tout ce qu'on lui montrait.

***  VERSION 1.3.6  ***

Deux défauts de conformité et une fonction qui manquait, tous nés des questions d'Adam H. Kerman sur un groupe anglophone. Ils sortent avant la 1.4 car ils concernent ce que NeReO met SUR LE RÉSEAU : ce qui finit dans les archives des autres ne se corrige pas après coup.

Objets longs avec caractères accentués ou non latins : encodage correct. La RFC 2047 admet au maximum 75 caractères par mot encodé ; NeReO en produisait UN SEUL de n'importe quelle longueur - mesuré, un objet italien normal sortait à 140 caractères. Maintenant l'encodage se coupe et se plie comme le veut la norme, en coupant seulement entre caractères entiers pour que les emojis et symboles multi-octets restent intacts.

us-ascii déclaré quand le corps est vraiment us-ascii (avec 7bit), au lieu de toujours déclarer UTF-8. Le contrôle a lieu APRÈS que la signature est entrée dans le corps : le décider une étape avant signifierait déclarer ASCII pur et ensuite envoyer une signature avec un accent dedans.

Nouvelle action de barre : Exporter / partager. Remet l'article - en-têtes complets inclus - à une autre application, ainsi vous pouvez l'archiver ou répondre à l'auteur par e-mail, chose que NeReO ne sait pas faire de lui-même. Remplace un partage disparu en sourdine avec la 1.3.4 en même temps que l'ancien menu de sélection.

Deux nouvelles actions de barre : Copier le lien (usenet.id) et Copier Message-ID. La première construit une adresse web qui pointe vers l'article ouvert - https://usenet.id/ suivi de son Message-ID - pour que vous puissiez envoyer quelqu'un lire précisément ce message depuis un chat, un e-mail ou un forum. La deuxième met dans le presse-papiers le Message-ID lui-même, dans sa forme canonique, qu'on ne pouvait obtenir jusqu'ici qu'en ouvrant les en-têtes complets et en le sélectionnant à la main. NeReO NE CONTACTE JAMAIS usenet.id : il compose le texte et le copie - aucune connexion, aucune nouvelle permission. Demande d'ObiWan.

Préréglage corrigé : OVH revient sur le port 119 avec STARTTLS, au lieu de 563 avec TLS implicite. La 1.3.5 avait déplacé sur 563 tous les trois serveurs qui demandent un compte, mais OVH n'offre pas TLS sur ce port : le choisir de la liste intégrée produisait une configuration qui ne pouvait pas fonctionner. Son administrateur a activé STARTTLS sur le 119 le 14 septembre 2026, et cette combinaison a été vérifiée sur un appareil réel. Pour les serveurs déjà configurés, rien ne change.

***  VERSION 1.3.5  ***

Une seule chose, et ce n'est pas une fonction : les messages d'erreur du niveau réseau sont maintenant en ANGLAIS pour tous.

D'où ça vient : un utilisateur qui utilise NeReO avec l'interface en anglais s'est vu répondre EN ITALIEN quand son serveur a refusé la connexion ("483 Connessione non cifrata...") et s'est raisonnablement demandé ce qu'il avait raté. Il n'avait rien raté.

Pourquoi cela arrivait : NntpClient est volontairement sans dépendances Android - c'est le choix qui le rend testable en ligne de commande - et donc il n'a pas de Context et ne peut pas lire strings.xml. Ses messages sont écrits dans le code, et aucune langue système ne les touche. Ce n'était pas une traduction oubliée : les trois strings.xml ont une parité complète de clés.

Ce qui a été choisi, et ce que ça coûte : si une seule langue doit valoir pour tous, autant que ce soit celle de values/ (le défaut de l'application) et la lingua franca de Usenet. Ce n'est PAS de la localisation : c'est un repli déclaré, et le prix est maintenant payé par ceux qui utilisent l'app en italien [ou autre langue non anglophone], qui liront en anglais moins de dix messages sur une app par ailleurs traduite. La vraie solution - passer les textes depuis l'extérieur de ce niveau - se fera quand cette classe sera rouverte pour d'autres motifs.

Le nom d'utilisateur est nettoyé des espaces aux extrémités, comme déjà l'adresse du serveur. Un espace arrivé par un coller ou par une suggestion du clavier était envoyé au serveur, qui le comptait - et le seul retour était "authentification échouée". Le mot de passe NON, à dessein : il peut légitimement commencer ou finir par un espace, et l'application n'a pas le droit de le modifier en silence.

Champ mot de passe : bouton pour le révéler et décompte des caractères. Le décompte est là parce qu'il saisit ce que révéler le mot de passe ne peut pas : un espace final est invisible même en clair. Si vous savez que le vôtre a onze caractères et que le champ en indique douze, le problème est trouvé. Et s'il a vraiment des espaces aux bords, l'application vous le dit en rouge au lieu de vous corriger d'autorité.

Le journal de diagnostic NNTP note la FORME des identifiants - combien de caractères, espaces aux bords, présence de caractères non ASCII - et JAMAIS la valeur. C'est la différence entre diagnostiquer un accès refusé en lisant un log et jouer aux devinettes.

En choisissant un serveur dans la liste intégrée, les trois qui demandent un compte (Eternal September, Solani, OVH) naissent sur le port 563 chiffré. Jusqu'ici la liste proposait à tous le 119 en clair, y compris ces trois-là - et comme NeReO refuse de mettre un mot de passe sur une connexion non chiffrée, la première tentative échouait TOUJOURS avec un 483. Ce n'était pas un cas limite : c'était le chemin normal, et celui qui choisit dans une liste le fait justement pour ne pas avoir à décider du port. Les deux serveurs ouverts restent sur le 119. Pour les serveurs déjà configurés, rien ne change.

Corrigé un défaut qui se présentait de deux manières différentes. "Ouvrir le premier non lu" retournait parfois au post initial - DÉJÀ LU - bien qu'il y ait des messages à lire ; et marquer une discussion comme lue la laissait parfois visible. C'était le MÊME défaut : NeReO répondait à la question "quels messages sont dans cette discussion" de TROIS manières, et une seule des trois était le moteur de threading qui dessine vraiment l'écran. Les deux autres comparaient le texte de l'en-tête References, et divergeaient chaque fois que le post initial n'avait jamais été téléchargé ou qu'un message ne listait que le père direct. Maintenant toute l'application demande au moteur de threading.

Note pour ceux qui ont signalé : les réponses orphelines en étaient exactement la cause. Les changements d'objet non - le moteur de threading de NeReO ne regarde jamais l'objet, donc renommer un message ne peut pas influencer le regroupement.

La signature peut contenir des lignes vides entre les lignes de texte. Quatre lignes intercalées perdaient les deux dernières, parce que la limite comptait les lignes PHYSIQUES ; maintenant elle compte les lignes DE TEXTE, avec un plafond global pour qu'une signature ne devienne pas de l'espace vide. À savoir : la netiquette classique compte quatre lignes physiques, vides comprises - c'est une tolérance de NeReO, et un autre lecteur pourrait quand même la tronquer.

***  VERSION 1.3.4 - VERSION TECHNIQUE  ***

Pas une ligne du code de l'application n'a changé par rapport à la 1.3.3. Tout ce sur quoi NeReO repose a changé.

Chaîne de compilation migrée : Android Gradle plugin 8.7.3 -> 9.3.0, Gradle 8.9 -> 9.5.0, Kotlin 2.0.21 -> 2.2.10, KSP à la numérotation KSP2. Fait avec l'AGP Upgrade Assistant d'Android Studio, pas à la main : les versions ont été choisies par l'outil en fonction de ce que cette installation supporte vraiment.

Jetpack Compose de la 1.7.6 à la 1.12 - vingt mois d'un seul coup. compileSdk monte à 37 parce que Compose 1.12 l'exige ; targetSdk RESTE à 35, délibérément : l'augmenter ferait déclencher de nouvelles restrictions du système d'exploitation, et nous ne voulons pas mélanger CELLES-CI avec ce saut.

Pourquoi elle sort seule, sans rien d'autre dedans : vingt mois de Compose touchent le dessin du texte, le défilement, le clavier et les animations, c'est-à-dire toute l'application. Si après celle-ci quelque chose se comporte mal, il doit y avoir UN SEUL suspect possible. C'est la même règle avec laquelle est sortie la 1.2.14.

L'étape intermédiaire a été testée sur le terrain : nouvelle chaîne d'outils avec Compose encore ancienne, compilée et installée - APK vérifié, AGP 9.3.0 avec Compose 1.7.6, tout est normal. Donc AGP et Gradle sont déjà acquittés : si quelque chose casse maintenant, c'est Compose.

Ce que nous espérons, déclaré pour ce que c'est : dans les notes de Compose 1.12 il y a des travaux explicites sur la sélection à la souris et sur la barre de sélection. Sur un appareil sans écran tactile, cette barre n'est jamais apparue. C'est le seul moyen de savoir si le problème était déjà résolu en amont. C'est une PRÉVISION, pas une promesse : si ça ne se résout pas, nous saurons que cette route ne suffisait pas - et la dette de vingt mois sera enlevée de toute façon.

Une chose s'est perdue, et il est juste de le dire : en sélectionnant le texte d'un message, "Rechercher sur le web" et "Partager" n'apparaissent plus. Elles avaient été ajoutées dans la 1.2.8 à la demande de Dave Royal, en remplaçant le menu de sélection par un des nôtres. Compose 1.12 ne passe plus par ce mécanisme : le menu est maintenant celui de la plateforme, et le nôtre était ignoré en silence.

Pourquoi on ne peut pas les reconstruire : la nouvelle API permet d'AJOUTER des éléments à ce menu, mais ne dit pas quel texte est sélectionné - vérifié dans le code source de androidx, pas déduit. Sans le texte, "Rechercher" et "Partager" ne savent pas quoi chercher ni quoi partager. Copier et Tout sélectionner ne sont pas touchés, et l'action "Copier le message" dans la barre de lecture reste.

SelectionToolbar.kt a été supprimé, pas laissé là inerte : son commentaire continuait d'affirmer qu'il fonctionnait, et un fichier qui ment est pire qu'un fichier manquant.

Si vous remarquez quelque chose de différent de la 1.3.3, signalez-le même si ça semble être une bêtise. C'est exactement à ça que sert cette version.

***  VERSION 1.3.3  ***

Nouveautés de cette version :

Quand une connexion chiffrée échoue, NeReO dit maintenant LEQUEL des deux problèmes possibles c'est : les remèdes sont différents, et jusqu'à la 1.3.2 nous les traitions comme un seul.

CHAÎNE DE SIGNATURE : si le certificat n'est pas signé par une autorité qu'Android reconnaît (auto-signé, CA privée) ou s'il est expiré, le message le dit et avertit explicitement que l'option de sécurité NE PEUT PAS SERVIR. Jusqu'à la 1.3.2 nous la suggérions dans tous les cas, envoyant l'utilisateur chercher une issue inexistante.

NOM D'HÔTE : si au contraire le certificat est valide mais émis pour une autre adresse, l'option sert vraiment et le message nous y dirige.

INDÉTERMINÉ : si l'erreur ne correspond à aucun des deux, NeReO le dit au lieu de s'inventer un diagnostic, et montre le texte brut de l'exception - la donnée qui sert à celui qui signale.

L'option a été renommée avec ce qu'elle fait vraiment : "Ne pas vérifier le nom d'hôte sur le certificat". L'ancien nom, "Autoriser les certificats non vérifiables", promettait plus qu'il ne tenait : il n'a JAMAIS fait accepter à NeReO un certificat auto-signé, d'autorité inconnue ou expiré - ça, c'est le TrustManager d'Android qui le refuse, que cet interrupteur n'effleure pas. Maintenant la note sous l'option le dit explicitement.

Aucun changement de comportement : ce qui se connectait avant se connecte encore, ce qui était refusé est refusé encore. Seul ce que DIT NeReO change.

Signalé par Bingo3331, qui sur Paganini a fait exactement ce que le message d'erreur lui disait de faire, a vu que ça ne marchait pas et en a déduit - raisonnablement - que l'option était cassée. Elle n'est pas cassée : elle fait autre chose. C'est nous qui avions construit l'impasse.

Nouvelle action "Copier le message" dans la barre de lecture, aussi bien sur le téléphone que dans le panneau des panneaux : UN CLIC copie l'entier message dans le presse-papiers, sans avoir besoin de rien sélectionner d'abord. Elle est personnalisable comme toutes les autres actions (on peut la déplacer dans le menu à trois points ou l'éteindre). En même temps, un bouton "Coller" dans la barre de l'écran de rédaction.

Pourquoi elle servait, et ce n'était pas ce qu'on pensait : la vidéo de test de Roxy clôt la question. Sur sa TV Box, la SÉLECTION de texte FONCTIONNE - le bloc se met en évidence et s'étend par glissement - mais la barre flottante Copier/Rechercher/Partager N'APPARAÎT JAMAIS, pas même pour une image en 22 secondes avec la sélection immobile au centre de l'écran. Ce n'est pas un problème de positionnement de la fenêtre pop-up, comme nous l'avions supposé : la barre n'est tout simplement pas appelée.

L'indice du mécanisme : il manque aussi les POIGNÉES de sélection, que Compose dessine sur le chemin tactile - alors que sur le téléphone, avec le doigt, les poignées et la barre y sont les deux. Deux appareils, deux chemins d'entrée, des signaux cohérents dans des directions opposées : c'est le chemin SOURIS qui ne soulève pas la barre. Et sur Android, le clic droit est RETOUR (BACK), donc il n'y a même pas de menu contextuel sur lequel se replier.

Si vous avez un clavier connecté, Ctrl+C et Ctrl+V fonctionnent déjà, même là où la barre flottante n'apparaît pas - confirmé par Roxy sur sa TV Box. Pour copier UNE PARTIE d'un message, cela reste le moyen le plus rapide, et il vaut la peine de le savoir : la fonction existait déjà, l'information manquait.

Le remède contourne le problème au lieu de le combattre : des actions qui ne passent pas par la sélection. Un clic, identiques au toucher et au pointeur.

Limite déclarée de l'action : elle copie le message ENTIER. Pour en copier un morceau, il faut encore le sélectionner - avec le doigt et la barre, ou avec Ctrl+C.

***  VERSION 1.3.2  ***

Nouveautés de cette version :

Même sur le TÉLÉPHONE on peut aller directement au premier message non lu. Nouvelle option facultative dans Apparence -> "Toucher la discussion pour ouvrir le premier non lu" : en touchant la tête d'une discussion, on ouvre le premier message pas encore lu, au lieu du premier post (s'ils sont déjà tous lus, le premier post s'ouvre). Valable dans la vue EN ARBORESCENCE et dans celle GROUPÉE.

Désactivée par défaut : qui ne l'active pas ne remarque aucune différence. Dans la vue groupée, si "Toucher le message pour étendre le thread" est aussi activée, ÉTENDRE A LA PRIORITÉ - là, le thread s'ouvre sur place et rester dans la liste est la chose sensée.

Pourquoi une nouvelle option et pas la même que la 1.3.1 : celle-là agit seulement là où une ligne représente l'entière discussion repliable (vue groupée), et dans la vue en arborescence - précisément celle pour laquelle la fonction a été demandée - cette prémisse n'existe pas. Réutiliser l'interrupteur existant n'aurait pas pu fonctionner : ce sont deux comportements différents dans deux vues différentes, donc deux interrupteurs.

User-Agent dans la forme prévue par la RFC 5536 (note technique de Dave Royal) : maintenant NeReO/1.3.2 (Android), avec la version rattachée par une BARRE OBLIQUE (/). Jusqu'à la 1.3.1 c'était "NeReO 1.3.1 (Android)", avec un espace.

Pourquoi ce n'était pas que de l'esthétique : la RFC 5536 par. 3.2.13 définit product = [CFWS] token [ [CFWS] "/" product-version ], c'est-à-dire que la version s'attache au produit avec la barre oblique. Avec l'espace, l'en-tête n'était pas ILLÉGAL - la grammaire admet plusieurs produits et "1.3.1" est un jeton (token) valide - mais disait autre chose : DEUX PRODUITS, l'un nommé "NeReO" et l'autre nommé "1.3.1". Quiconque collecte des statistiques sur les newsreaders lisait un logiciel inexistant. Le commentaire "(Android)" est un CFWS et était déjà correct.

Les identités déjà sauvegardées sont réalignées une seule fois, et SEULEMENT si elles contiennent encore exactement l'un des paramètres par défaut expédiés par nous. Un User-Agent écrit par l'utilisateur n'est jamais touché - y compris le champ vide, qui depuis la 1.2.9 signifie "ne rien déclarer" et est un choix, pas un oubli.

Corrigé : si le dernier article d'un groupe était supprimé, ce groupe ne se mettait plus à jour. Chaque tentative répondait "423 No articles in NNNN-NNNN" et ne se débloquait d'elle-même que lorsque quelqu'un postait à nouveau.

Pourquoi cela arrivait : une fois le dernier article supprimé, le numéro haut déclaré par le groupe NE descend PAS (RFC 3977 par. 6.1.1 : tous les numéros entre le water mark bas et le haut ne correspondent pas à des articles existants), donc NeReO demandait l'intervalle de son propre marque-page jusqu'à ce numéro - désormais vide. Le 423 que le serveur restitue N'EST PAS une erreur : la RFC 3977 par. 8.3 le définit comme "aucun article dans cet intervalle", et c'est la bonne réponse. NeReO le traitait comme fatal.

Ce que ça coûtait vraiment, outre l'erreur à l'écran : la connexion partagée était jetée et rouverte pour les groupes restants (socket + TLS + authentification depuis le début, à chaque mise à jour), le groupe était sauté par la garde anti-boucle, et le marque-page n'avançait jamais - donc la mise à jour d'après redemandait le même intervalle mort. Maintenant le 423 produit une liste vide, le marque-page avance et l'intervalle mort se consomme une seule fois.

Ce n'était pas un cas de laboratoire : le trou entre le numéro bas et le numéro haut est prévu par le protocole, et les cancels de spam sur le dernier article d'un groupe peu trafiqué sont monnaie courante. Le test délibéré de BIG Umberto sur alt.test.a, reproduit trois fois sur trois serveurs différents (Solani, eternal-september, Paganini), n'a pas inventé un scénario impossible : il a rendu reproductible une chose qui arrive toute seule et qui jusqu'ici pouvait être archivée comme "erreur de connexion".

Sous le capot : avant d'ajouter le CINQUIÈME point du code qui calcule "premier non lu de la discussion", la logique a été extraite une seule fois et les cinq points la partagent maintenant. La 1.3 est sortie avec un défaut précisément parce qu'une correction avait été appliquée à deux copies sur quatre d'un bloc dupliqué.

***  VERSION 1.3.1  ***

Nouveautés de cette version :

Dans la disposition en panneaux, toucher une discussion maintenant l'étend ET y va : avec l'option "Toucher l'OP pour étendre" active, le toucher ouvre dans le panneau de lecture le premier message NON LU de cette discussion (ou le premier post, s'ils sont déjà tous lus). En fermant la discussion la sélection retourne sur l'OP, la seule ligne restée visible.

Pourquoi c'était un problème sérieux et pas un désagrément : avant le toucher ne faisait qu'étendre, et le premier post d'une discussion avec réponses ne pouvait pas du tout s'ouvrir. Dans la 1.2.9 nous avions indiqué comme échappatoire le menu à appui prolongé, mais sur une TV Box avec télécommande ce geste N'EXISTE PAS : pour qui lit de là-bas ce message était inatteignable. Signalé par Roxy, qui avait lui-même demandé cette option dans la 1.2.9.

Chaque ligne de la liste des discussions a maintenant un bouton "..." (trois points verticaux) qui ouvre son menu contextuel - ouvrir, plonk, remplacer, retirer, conserver, sélection multiple - avec UN SEUL CLIC. L'appui prolongé continue à fonctionner exactement comme avant : c'est un moyen en plus, pas un remplacement. Naît du même signalement : sur un appareil guidé par un pointeur (mini clavier avec trackpad) maintenir une touche appuyée est techniquement possible, mais si lent que - mots de Roxy - "l'envie de l'utiliser passerait".

La barre du panneau de lecture dans les panneaux est maintenant personnalisable, comme déjà celle du téléphone (Apparence -> Personnaliser la barre de lecture). Avec une différence voulue : ici "éteint" ne veut pas dire CACHÉ mais DÉPLACÉ DANS LE MENU trois points - rien ne devient inatteignable. Elle naît déjà avec ROT13 et En-têtes dans le menu, car sur une tablette tenue à la verticale le panneau est étroit et cinq boutons ne laissaient presque pas d'espace à l'objet du message. Suggestion de Dave Royal, déjà demandée précédemment par Roxy.

Seulement dans la disposition en panneaux, à dessein : sur le téléphone le toucher navigue ailleurs, donc "étendre et aller" serait une contradiction, et là l'appui prolongé est un geste naturel, pas un obstacle.

***  VERSION 1.3  ***

Nouveautés de cette version :

Corrigé une lacune de la 1.2.17 : "Envoyer le mot de passe seulement quand le serveur le demande" N'était PAS appliqué à l'envoi d'un message ni au téléchargement de la liste des groupes, qui continuaient à s'authentifier tout de suite. Cause : le même bloc de code existait en quatre copies et deux avaient été mises à jour. Les quatre points qui ouvraient une connexion sont maintenant unifiés en un seul, ainsi la règle s'applique partout par construction.

Pour le reste la 1.3 est la 1.2.17 testée sur le terrain : c'est la version stable qui recueille toute la ligne 1.2.12-1.2.17 - le cycle de renforcement né de l'audit de sécurité, la vérification du nom d'hôte TLS, retrait et remplacement avec résultat serveur par serveur, et tout ce qui a émergé de deux jours de tests réels sur trois fournisseurs.

La 1.3 est la PREMIÈRE VERSION PUBLIQUE après la 1.2.11 : ceux qui mettent à jour depuis là-bas reçoivent d'un coup aussi tout le contenu des versions 1.2.12-1.2.17, qui n'ont jamais été distribuées (voir la note juste sous l'historique).

======================================================================
ATTENTION - LES VERSIONS DE 1.2.12 À 1.2.17 N'ONT JAMAIS ÉTÉ
PUBLIÉES
La dernière version distribuée publiquement sur SourceForge a été la 1.2.11.

Tout ce qui suit ci-dessous, de la 1.2.12 à la 1.2.17, a été compilé et testé SEULEMENT à l'intérieur du projet : sur trois serveurs réels (Astraweb, usenet.ovh, eternal-september) et sur un seul appareil. Aucune de ces versions n'est jamais arrivée aux utilisateurs, et il n'existe aucun APK public qui les contienne.

Elles sont listées parce que leur contenu est TOUT à l'intérieur de la 1.3 : elles servent à comprendre ce qui a changé et pourquoi, pas à chercher une mise à jour qui n'a jamais existé. Ceux qui viennent de la 1.2.11 passent directement à la 1.3 et reçoivent tout d'un coup.

***  VERSION 1.2.17  ***

Nouveautés de cette version :

Nouvelle option "Envoyer le mot de passe seulement quand le serveur le demande" (Entretien -> Sécurité), DÉSACTIVÉE par défaut. En temps normal, NeReO s'authentifie dès qu'il se connecte, à chaque connexion ; avec ceci activé il attend que ce soit le serveur à le demander, s'authentifie à ce moment-là et répète la commande. Sur un serveur qui n'arrive pas à chiffrer la connexion cela change beaucoup : les serveurs INN laissent lire sans identifiants et les exigent seulement pour publier, donc le mot de passe traverse le réseau quelques fois par mois au lieu de dizaines de fois par jour. Éteinte par défaut car cela touche la façon dont on contacte CHAQUE serveur : si l'un cesse de fonctionner, on l'éteint et on revient exactement à avant.

Dans la liste des serveurs apparaît l'avertissement "dernière connexion NON chiffrée" sous les serveurs qui parlent en clair. Il se base sur ce qui s'est réellement passé, pas sur les paramètres : un serveur peut être configuré pour chiffrer, le déclarer, et ne pas y arriver quand même. C'est un signe permanent et non un avertissement récurrent, car cet état ne change pas tout seul et un avertissement qui revient à chaque fois s'apprend à être ignoré.

Le journal de diagnostic ne compte plus comme erreurs les réponses attendues : un retrait réussi clôturait la session avec "1 erreurs", parce que le 430 "no such article" - qui est exactement la réponse cherchée - était compté comme panne.

***  VERSION 1.2.16  ***

Nouveautés de cette version :

Le retrait (cancel) dit maintenant comment ça s'est passé sur CHAQUE serveur configuré, pas seulement sur celui depuis lequel vous avez publié. À la fin apparaît une liste - retiré, encore présent, non vérifiable - avec le serveur auquel la demande a été effectivement envoyée marqué comme tel. C'est la réponse qu'on ne pouvait jusqu'ici obtenir qu'en contrôlant à la main, un serveur à la fois, depuis un autre programme.

La boîte de dialogue de confirmation dit d'abord ce que l'on découvrait après. Quatre tests sur le terrain entre le 6 et le 7 septembre ont établi que le retrait agit, en pratique, seulement sur le serveur sur lequel vous avez publié : sur les autres le message reste, et de nombreux fournisseurs ignorent complètement les demandes de retrait. Il en découle un conseil, maintenant écrit dans la confirmation : si une correction doit être vue par tous, le bon outil est "Remplacer (supersede)" et non "Retirer" - le supersede est un article normal et se propage toujours, le retrait est un message de contrôle et presque personne ne le retransmet.

Aucun travail en arrière-plan : les serveurs sont interrogés seulement à ce moment-là, une connexion chacun, et un injoignable devient "non vérifiable" sans faire échouer le reste.

***  VERSION 1.2.15  ***

Nouveautés de cette version :

"Retirer du serveur (cancel)" dit maintenant SI ÇA A FONCTIONNÉ. Avant, l'application disait seulement "demande envoyée, le serveur pourrait l'ignorer" : qui retirait un message sur un serveur qui ignore les messages de contrôle restait convaincu de l'avoir enlevé. Maintenant, juste après l'envoi, NeReO demande au serveur si cet article y est encore et rapporte la réponse : retiré, encore présent, ou bien non vérifiable. Trois résultats et non deux, car sur le terrain nous les avons vus tous les trois.

Un message qui sur le serveur n'existe plus (expiré, ou retiré par son auteur) est dit pour ce qu'il est, au lieu d'apparaître comme une erreur de réseau quelconque. À côté de l'explication, il y a le bouton "Supprimer la copie locale", pour enlever la ligne de résumé restée orpheline. Autant dans la vue en pages que dans le panneau de lecture.

Supersedes géré en lecture : lorsqu'un message en remplace un autre, le remplacé est CACHÉ (non éliminé : "Afficher les cachés" le ramène), et seulement si les deux viennent de la même adresse. Sans ce contrôle n'importe qui pourrait faire disparaître le message d'un autre de la vue de qui lit.

Corrections techniques invisibles : une réponse normale "article non trouvé" n'est plus prise pour une connexion tombée (défaut de la 1.2.13, coûtait une reconnexion inutile) ; un serveur indiqué avec l'adresse IP ne produit plus une erreur incompréhensible ; en changeant les interrupteurs de sécurité, la connexion ouverte avec les paramètres précédents est maintenant fermée tout de suite.

***  VERSION 1.2.14  ***

Nouveautés de cette version :

VÉRIFICATION DU NOM D'HÔTE DANS LES CONNEXIONS CHIFFRÉES (TLS). Jusqu'à la 1.2.13 le trafic vers le serveur était chiffré, mais personne ne contrôlait que le certificat présenté appartenait vraiment à l'adresse demandée : un réseau hostile pouvait se mettre au milieu et tout lire, identifiants inclus, sans que l'application ne s'en aperçoive. Maintenant le contrôle y est, sur les deux voies (TLS implicite et STARTTLS), comme le fait un navigateur.

Si un serveur a le certificat mal configuré, la connexion échoue avec un message explicite qui dit ce qui s'est passé et où intervenir, non avec une erreur de réseau générique.

Nouvel interrupteur "Autoriser les certificats non vérifiables" (Entretien -> Sécurité), ÉTEINT par défaut et avec avertissement en rouge : qui sait que son fournisseur a le certificat mal mis peut se connecter quand même. Il est global, non par-serveur, sur le modèle déjà utilisé par la 1.2.6 pour l'authentification non chiffrée.

Cette version sort SEULE, à dessein : c'est la seule correction de l'audit de sécurité qui peut faire cesser de fonctionner une configuration qui aujourd'hui fonctionne. Si après la mise à jour un serveur ne se connecte plus, la cause est sans ambiguïté celle-ci et le remède est l'interrupteur ci-dessus.

***  VERSION 1.2.13  ***

Nouveautés de cette version :

Plus d'erreur de réseau à la place du corps d'un message en passant vite de l'un à l'autre. La connexion tenue ouverte pour la lecture était laissée tomber par le serveur pendant qu'on lisait, et la première commande après la pause finissait dans le vide : maintenant, si une erreur de réseau arrive sur une connexion RÉUTILISÉE, celle-ci est jetée et le message relu une fois avec une connexion neuve, en silence. Diagnostiqué avec le log du Diagnostic NNTP.

User-Agent prérempli dans les NOUVELLES identités (demande du Mérovingien) : le champ naissait vide sur une application fraîchement installée, donc qui ne le remplissait pas postait anonyme sans l'avoir choisi. Maintenant il contient %nereo%, qui à l'envoi devient nom et version utilisés (et reste mis à jour tout seul à chaque version). Qui veut rester anonyme le vide : les identités DÉJÀ EXISTANTES ne sont pas touchées.

La vérification du nom d'hôte dans les connexions chiffrées glisse à la 1.2.14 : elle reste la seule correction qui sort toute seule.

***  VERSION 1.2.12  ***

Nouveautés de cette version :

Cycle de renforcement général, né d'un audit de sécurité sur tout le code. NeReO encaisse sans se décomposer des articles et réponses du serveur malformés ou construits exprès : messages hors normes, en-têtes avec retours à la ligne glissés dedans, chaînes References anormales, images Face anomales, restaurations à partir de fichiers de sauvegarde altérés. Dans l'utilisation normale, rien ne change - ce sont des défenses qui ne se remarquent que lorsque quelque chose essaie d'en profiter.

"Remplacer (supersede)..." apparaît maintenant seulement sur vos PROPRES messages, et à côté il y a le nouveau "Retirer du serveur (cancel)..." : envoie un message de contrôle qui demande au serveur de retirer un article que vous avez publié. De nombreux serveurs ignorent ces demandes et la propagation aux autres serveurs n'est jamais garantie : la boîte de dialogue de confirmation le dit clairement, au lieu de vous le laisser découvrir après.

NON incluse ici : la vérification du nom d'hôte dans les connexions chiffrées. C'est la seule correction qui peut faire cesser de fonctionner une configuration qui aujourd'hui fonctionne (il arrive qu'un fournisseur présente un certificat avec un nom qui ne correspond pas), donc elle sort seule dans la version suivante - ainsi, si quelque chose ne se connecte plus, on sait tout de suite à qui la faute.

***  VERSION 1.2.11  ***

Nouveautés de cette version :

Mise à jour de la bibliothèque de la base de données interne (Room) de 2.6.1 à 2.8.4 : tentative de correction en amont du rare crash CursorWindow du Point 5 (voir version 1.2.10 ci-dessous). La 2.8.4 inclut deux corrections officielles sur les courses entre les Flow de lecture et les écritures concurrentes, la même catégorie que ce crash - ce n'est pas une confirmation de la cause exacte, ça reste une hypothèse vérifiée seulement en partie, et les défenses déjà introduites dans la 1.2.10 restent actives quand même, comme filet supplémentaire. Aucun changement visible pour l'utilisateur.

Extension de la même défense (liste précédente au lieu du crash) également à la recherche textuelle, qui partageait le même risque mais n'était pas encore protégée.

***  VERSION 1.2.10  ***

Nouveautés de cette version :

Liste des articles plus robuste : une rare erreur de lecture de la base de données (IllegalStateException du CursorWindow de Room), qui pouvait arriver en sortant d'un groupe pendant qu'une synchronisation écrivait encore de nouveaux messages, ne fait plus planter l'application - maintenant, si cela arrive, elle montre la dernière liste valide au lieu de se fermer. Première version réelle à porter cette défense : elle était prête depuis le 5 septembre, en attente d'un tour de livraison.

Nouvel interrupteur "Mettre à jour automatiquement en ouvrant un groupe" (Entretien, signalement d'un testeur via Marco) : entrer dans un groupe (même seulement en revenant d'un article à peine lu) le synchronisait toujours, sans moyen de le désactiver - qui se distrayait en lisant se retrouvait avec des discussions laissées à moitié mélangées à de nouvelles. Maintenant on peut l'éteindre, gardant tout figé jusqu'à ce qu'on lance la synchronisation à la main (bouton/tirer vers le bas pour mettre à jour, toujours disponibles). Activé par défaut : aucun changement pour qui n'en a pas besoin.

***  VERSION 1.2.9.c  ***

Nouveautés de cette version :

Synchronisation plus robuste : éliminée une éventuelle course contre la montre entre le contrôle automatique des nouveaux messages et d'autres synchronisations en cours (manuelles ou du minuteur), qui pouvait contribuer à un arrêt anormal occasionnel - signalement du test public encore en cours d'investigation (voir la 1.2.9.b ci-dessous pour le journal de diagnostic). Ce n'est pas une correction CONFIRMÉE du crash spécifique (il manque encore une preuve directe), mais un défaut de concurrence réel et démontré dans le code.

***  VERSION 1.2.9.b  ***

Trois corrections ayant émergé du test public de la 1.2.9, plus un nouvel outil de diagnostic : première vraie nouvelle version à compiler et à distribuer aux utilisateurs (les corrections précédentes étaient restées "à version inchangée" car la 1.2.9 n'avait pas encore été publiée).

Nouveautés de cette version :

Recherche : la liste des résultats ne se remet plus à zéro en rouvrant un résultat (signalement de Marco) : en revenant d'un message ouvert par la recherche, la requête et les résultats restent ceux d'avant au lieu de repartir à vide - avant, il fallait refaire la même recherche à chaque fois pour atteindre le résultat suivant.

"Afficher les filtrés" est maintenant un audit complet (signalement de Marco : "s'ils sont filtrés pourquoi devrions-nous les voir ?" -> "pour moi c'est un audit cassé") : en appliquant les filtres aux messages déjà téléchargés, même ceux qui correspondent à une règle "Ignorer" (pas seulement "Éliminer complètement") sont maintenant marqués et affichés en gris - avant, seuls les seconds étaient éliminés, les premiers restaient invisibles à l'audit.

User-Agent vide = post vraiment anonyme (signalement de Marco) : en laissant vide le champ User-Agent dans l'identité, maintenant le message envoyé ne porte plus aucun en-tête User-Agent, comme le texte d'aide l'a toujours promis - avant, il était quand même remplacé en silence par la valeur par défaut de l'application.

Nouveau : journal du dernier arrêt anormal (Entretien) : si l'application devait se fermer de manière imprévue, le dernier crash est enregistré tout seul sur l'appareil - rien ne part automatiquement. Si cela arrive, copiez-le ou exportez-le depuis la nouvelle section "Dernier arrêt anormal" et envoyez-le à ceux qui développent : sans cela, un crash signalé sans autres détails n'est pas diagnosticable.

***  VERSION 1.2.9  ***

Multi-identité réelle (demande de Marco : "un multi utilisateur réel, comme dans MesNews et/o NR MOD17 de Piao Hong") plus le reste de la feuille de route NNTP restante (point E), dans la même version - plan complet choisi explicitement par Marco plutôt que par étapes. Ferme la porte vers la 1.3 définitive, en attente seulement du test sur appareil.

Nouveautés de cette version :

Multi-identité : créez autant d'identités que vous voulez (nom, e-mail, signature, User-Agent, X-No-Archive, en-têtes personnalisés, introduction à la citation, Face) et assignez chacune à un serveur précis - écrivez comme "MCSM" sur un serveur et comme "TAF" sur un autre, chacune avec sa propre adresse et sa propre Face. Nouveaux écrans Identités (liste + éditeur) ; l'assignation à un serveur se fait en modifiant le serveur. Ceux qui avaient déjà une identité générale ou un contournement (override) par serveur dans les versions précédentes se les retrouvent migrés automatiquement, sans devoir rien refaire.

STARTTLS (RFC 4642) : les serveurs restés sur un port en clair (généralement 119) peuvent maintenant passer à une connexion chiffrée tout de suite après la connexion, en alternative au TLS implicite.

CAPABILITIES : lu et enregistré dans le diagnostic à la connexion.

Lecture plus rapide : en lisant un thread, le corps des articles réutilise la même connexion au lieu de se reconnecter à chaque article individuel.

Dérogation (override) par serveur de l'authentification non chiffrée : pour un serveur unique, vous pouvez la forcer toujours permise ou toujours bloquée, indépendamment du paramètre global (Entretien -> Sécurité).

La sauvegarde et la restauration portent maintenant aussi les identités et les nouvelles options par serveur ; les anciennes sauvegardes restent lisibles comme avant.

Citations très imbriquées - seuil numérique (Apparence, demande de Marco) : au-delà d'une certaine profondeur (défaut 8, configurable entre 6/8/10/12), les ">" répétés deviennent le numéro du niveau ("9", "10", "11"...) au lieu de s'allonger à l'infini - seulement dans le rendu à l'écran, avec le style de citations "chevron"/"les deux" : le texte qui arrive et celui qui part reste toujours avec les ">" standards, donc la profondeur se lit correctement partout, même dans un autre client. Actif par défaut.

Toucher le message pour étendre le thread (Apparence, en option, demande de Roxy) : en alternative au bouton +/- (agrandi à 48dp minimum pour qui vise de loin, ex. TV box avec télécommande), le toucher sur le texte du premier message d'une discussion avec réponses peut l'étendre/la comprimer au lieu de l'ouvrir ; le message reste quand même accessible depuis le menu (toucher prolongé) -> "Ouvrir". Désactivé par défaut.

Brouillons déplacés à l'intérieur du groupe (demande de Marco) : "Brouillons" n'est plus dans le menu principal - il s'ouvre depuis le menu du groupe auquel on écrit, déjà filtré sur les brouillons de ce groupe (chaque brouillon sait déjà à quel groupe il appartient). Un interrupteur "Ce groupe"/"Tous les groupes" dans l'écran permet de voir quand même tous les brouillons ensemble, quand c'est nécessaire.

Glissement (swipe) vertical : maintenant il passe aussi d'une discussion à l'autre (signalement de Marco) : dans la lecture, avec "seulement non lus" éteint, le swipe en fin/début de message s'arrêtait muet au dernier/premier post de la discussion au lieu de poursuivre - différent de quand "seulement non lus" est activé, qui sautait déjà à la discussion suivante. Maintenant le swipe vertical se comporte de la même manière dans les deux cas (les flèches simples < > restent bloquées à l'intérieur de la discussion courante, comme toujours - pour cela il y a les doubles flèches).

***  VERSION 1.2.8  ***

Étape intermédiaire entre la 1.2.7 et la future 1.3 "complète" : recueille tout ce qui avait été préparé comme 1.3/1.3.1/1.3.2 pendant le test en solitaire, renuméroté avant toute diffusion plus large - aucune de ces versions n'est jamais sortie officiellement (ces numéros ont ensuite été réutilisés par les versions réelles décrites plus haut : ce sont des homonymes, pas les mêmes builds). La vraie 1.3 n'arrivera que lorsque le reste de la feuille de route NNTP (CAPABILITIES, STARTTLS, pool de connexions, override auth-non-chiffrée par-serveur) sera complété ou écarté.

Nouveautés de cette version :

Barre de lecture personnalisable : dans Apparence -> "Personnaliser la barre de lecture", choisissez quelles icônes afficher dans la barre basse de la lecture et dans quel ordre (interrupteur montrer/cacher + flèches haut/bas, pratiques aussi avec la télécommande des TV box).

Mise à jour sélective : l'icône de mise à jour ouvre maintenant un menu - "Tout mettre à jour" (comme avant) ou "Choisir quels serveurs mettre à jour". Sur l'en-tête de chaque serveur, un menu similaire permet de mettre à jour seulement ce serveur ou seulement certains de ses groupes, au lieu de toujours tout télécharger. Sur tablette/panneaux, le niveau serveur est disponible.

Lecture plein écran dans les panneaux (tablette) : pendant que vous lisez, une nouvelle icône cache temporairement les colonnes de groupes et de discussions pour vous concentrer sur le texte ; elle se referme toute seule en quittant l'article.

Rechercher/Partager sur le texte sélectionné : en sélectionnant du texte dans un article, outre Copier et Tout sélectionner, apparaissent aussi Rechercher (recherche web) et Partager. "Traduire" a été écarté DÉFINITIVEMENT (dépendrait d'un gestionnaire non garanti sur chaque appareil).

Contrôle des mises à jour à quatre modes : dans Entretien -> Mises à jour, choisissez entre Jamais / Une fois par jour / Toutes les 4 heures / À chaque ouverture. Sous le sélecteur, il apparaît toujours "Dernier contrôle : ..." ainsi on voit que le contrôle fonctionne même quand il ne trouve rien de nouveau (aucun avertissement dans ce cas, seulement ce texte).

Corrigé un bug : l'ancienne limite "une fois par jour" était par jour calendaire, non par temps écoulé - ceux qui ouvraient l'application même une seule fois avant qu'une nouvelle version ne soit publiée restaient "bloqués" jusqu'au lendemain, quel que soit le nombre de fois qu'ils rouvraient l'application entre-temps.

Diagnostic NNTP amélioré (Bingo3331 + HAL) : avec le journal NNTP activé (Entretien -> Diagnostic), on enregistre maintenant aussi un résumé de chaque réponse OVER/XOVER (lignes reçues, numéros manquants, doublons), les temps de chaque commande, et un en-tête/pied pour chaque session NNTP - pensé pour comprendre les trous dans le backfill de fournisseurs comme Astraweb.

Nouveau diagnostic manuel "Diagnostic LISTGROUP..." : demande au serveur la vraie liste des articles d'un groupe et la compare avec le dernier résumé overview ; sur les numéros absents, vérifie avec STAT s'ils existent vraiment - distingue un trou dans la base de données overview du serveur d'un article vraiment expiré ou supprimé.

***  VERSION 1.2.7  ***

Nouveautés de cette version :

Connexion non sécurisée : si un serveur refuse le mot de passe sur une connexion non chiffrée, apparaît maintenant un avertissement clair avec deux choix - "Passer à SSL" (conseillé) ou "Autoriser non chiffrée" (déconseillé). Fini l'échec muet pour ceux qui débutent. L'avertissement n'apparaît pas pendant le contrôle automatique à intervalles, seulement quand vous mettez à jour vous-même.

Réponse aux crossposts : nouvelle option dans Entretien pour répondre par défaut à TOUS les groupes de l'original au lieu du seul groupe courant (désactivée par défaut ; vous pouvez toujours choisir au cas par cas).

Vue continue : un séparateur plus épais marque maintenant le début de chaque nouvelle sous-discussion, ainsi elles se distinguent d'un coup d'œil.

***  VERSION 1.2.6  ***

Nouveautés de cette version :

Sécurité : le mot de passe n'est plus envoyé sur une connexion non chiffrée (il voyagerait en clair - la RFC 4643 l'interdit). Si vous avez vraiment un ancien serveur qui le demande, vous pouvez l'autoriser consciemment dans Entretien -> Sécurité. Préférez toujours le port SSL (généralement 563).

"Vérifier les mises à jour" est maintenant aussi dans le menu principal, à côté d'À propos et Nouveautés - plus de cheminement long dans Entretien.

Vue en panneaux : menu à trois points sur chaque post, avec le plonk de l'auteur. Avant il fallait passer à la vue en plein écran.

Vue en panneaux : on peut réordonner les groupes ici aussi (icône "réordonner" en haut de la colonne des groupes), et plus seulement en plein écran.

La sauvegarde enregistre maintenant l'ordre des groupes : à la restauration tout ne revient plus par ordre alphabétique.

Vue En-têtes plus propre : le long code base64 de Face/X-Face n'est plus affiché (le visage reste à côté de l'auteur).

Rendre la citation muette : un nouveau bouton cache le texte cité d'un simple toucher, ainsi seul le nouveau message reste en évidence (un appui sur l'espace réservé le remontre). Icône "guillemets" dans la barre de lecture et dans les en-têtes des panneaux.

Vue en panneaux : dans le panneau de lecture le swipe haut/bas saute au post non lu suivant/précédent de la discussion (s'il n'y en a pas, au post suivant/précédent) - comme sur le téléphone.

***  VERSION 1.2.5 (corrections)  ***

Nouveautés et corrections de cette version :

Vue continue : en ouvrant un long thread les messages suivant celui sélectionné ne sont plus marqués comme lus en bloc. Maintenant n'est marqué comme lu que ce qui apparaît vraiment sous les yeux : le message choisi à l'ouverture, et ensuite chaque message à mesure que vous l'amenez en haut en faisant défiler.

Vue continue : les groupes du crosspost sont maintenant affichés (en rouge) sur chaque post, pas seulement dans la vue à article unique.

Avec le filtre "seulement non lus" actif, s'il n'y a rien à lire la liste dit maintenant "Aucun message à lire" au lieu de "Aucun message".

Contrôle des mises à jour : NeReO peut avertir quand il y a une version plus récente sur SourceForge (automatique une fois par jour, désactivable dans Entretien, plus un bouton manuel). Seulement avertissement et lien : l'application ne télécharge ni n'installe rien d'elle-même.

***  VERSION 1.2.4 (corrections)  ***

Nouveautés et corrections de cette version :

Refonte (reflow) du texte : réunit maintenant aussi les lignes "orphelines" d'un ou deux mots restées au milieu d'un paragraphe. Avant, même avec la refonte active, quelques lignes courtes (un "e" ou un "o" tout seul) restaient isolées parce que l'heuristique précédente les prenait pour un retour à la ligne voulu. Maintenant seules les étiquettes, salutations et phrases courtes qui finissent par une ponctuation (. , : ; ! ? ...) restent sur leur propre ligne. Désactivé par défaut ; seulement en lecture.

***  VERSION 1.2.3 (corrections)  ***

Nouveautés et corrections de cette version :

Vue en arborescence : les messages qui ouvrent une discussion n'apparaissent plus comme "déjà lus" quand ils ne le sont pas. La couleur suit maintenant l'état réel du post, en accord avec "aller au prochain non lu".

Vue continue : en répondant à un message crossposté l'avertissement pour choisir dans quels groupes répondre apparaît maintenant, comme dans la vue à article unique. Avant, la réponse finissait seulement dans le premier groupe, sans avertissement.

Vue continue : en ouvrant plusieurs messages non lus, "Error: connection reset" n'apparaît plus à la place du texte. Les corps se téléchargent une requête à la fois, au lieu d'ouvrir plein de connexions ensemble.

Vue en panneaux : la mise à jour utilise par défaut l'icône en haut de la colonne ; le glissement "tirer vers le bas" ne vole plus le premier défilement vers le haut. Le glissement reste réactivable dans Apparence. Sur le téléphone rien ne change.

Refonte (reflow) du texte : agit maintenant aussi sur les posts qui vont à la ligne serré (autour de 38-40 colonnes), qui avant étaient ignorés complètement et restaient avec des mots orphelins. Toujours désactivé par défaut ; seulement en lecture.

Vue continue : en ouvrant un thread sur un message au milieu, les messages en tête qui sont seulement traversés à l'ouverture ne sont plus marqués comme "lus". Le marquage vaut depuis le message choisi vers le bas, pas sur ce qui défile à l'ouverture.

***  VERSION 1.2.2 (corrections)  ***

Nouveautés et corrections de cette version :

Nouvelle option "Refonte (reflow) du texte" (Apparence) : recompose les paragraphes coupés par le retour à la ligne classique à 72 colonnes, ainsi les posts se lisent ordonnés sur écran étroit au lieu de montrer des lignes orphelines d'un
ou deux mots. Désactivée par défaut ; seulement en lecture.

Mise à jour beaucoup plus rapide : une seule connexion par serveur au lieu d'une pour chaque groupe (avec de nombreux groupes, la différence se fait sentir). La bande avec le nombre de nouveaux messages apparaît maintenant aussi en appuyant sur l'icône de mise à jour, pas seulement avec le "tirer vers le bas".

Vue en panneaux (tablette) : dans la vue continue, la troisième colonne suit maintenant le même ordre-arborescence de la colonne centrale (plus par date). En écrivant un post, l'éditeur s'ouvre en plein écran, ainsi le clavier ne couvre plus la zone d'écriture en mode horizontal.

Vue en panneaux (tablette/horizontale) : en sélectionnant un message de l'arborescence, il se positionne maintenant à nouveau correctement en haut et y reste (dans la 1.2 il pouvait atterrir à un point aléatoire).

Le petit cercle "mettre à jour" ne reste plus suspendu en haut de l'arbre : le défilement redevient immédiat.

Connexions SSL/TLS : ajout du délai d'attente (timeout) de lecture qui manquait, ainsi une connexion chiffrée qui se bloque ne laisse pas l'application dans une attente infinie.

***  VERSION 1.2  ***

Nouveautés de cette version :

Compatibilité serveur : certains fournisseurs (Astraweb parmi eux) refusent la commande OVER utilisée pour télécharger les en-têtes et la synchronisation s'arrêtait. Maintenant NeReO se replie automatiquement sur XOVER, ainsi ces serveurs fonctionnent à nouveau.

Plonk étendu aux réponses : une règle sur "de" peut cacher aussi les réponses au post filtré et la branche entière en dessous, peu importe qui les a écrites (réversible : enlevez la règle et ils reviennent).

Parité sur tablette : dans la vue en panneaux il y a maintenant les en-têtes complets et le ROT13, et on atteint aussi les Brouillons. La vue continue s'ouvre déjà positionnée sur le message choisi.

Groupes : maintenez appuyé un groupe pour télécharger d'un seul coup les corps manquants des 300 derniers messages. "Suivre la discussion" s'applique maintenant au thread entier ; vos posts sont reconnus même avec les identités par-serveur.

Après l'envoi d'un post : confirmation "Message envoyé" et rafraîchissement automatique du groupe, ainsi son propre message apparaît sans recharger à la main (dès que le serveur le rend disponible).

Sauvegarde complète (identités par-serveur, tous les paramètres et les discussions suivies ; restauration compatible avec les anciennes sauvegardes).

Diagnostic NNTP dans Entretien : enregistre le dialogue avec le serveur (mot de passe toujours masqué) pour résoudre plus vite les cas étranges. Désactivé par défaut.

Corrections : barre d'icônes en bas sur deux lignes sur les écrans étroits ; disparition d'une fausse erreur qui apparaissait en défilant vite.

Pour le reste, les fonctions complètes de la 1.1 restent. L'interface est disponible en italien et en anglais.

======================================================================

=============
QU'EST-CE QUE NeReO
NeReO est un lecteur de newsgroups Usenet pour Android, dédié aux discussions textuelles.

Pas de groupes binaires, pas de pièces jointes, pas de publicité, pas de télémétrie : seulement des messages et des conversations, comme Usenet l'a toujours été.

C'est un logiciel libre, publié sous licence GNU GPL v3 ou ultérieure. Le nom "NeReO" et le logo sont réservés à l'auteur : les versions modifiées doivent utiliser un autre nom (voir TRADEMARK.txt). Code source également sur GitHub : https://github.com/mcsm64/NeReO

===================
LANGUE DE L'INTERFACE
NeReO suit automatiquement la langue du téléphone :

téléphone en italien   -> interface en italien

n'importe quelle autre langue -> interface en anglais

Si vous préférez une langue différente de celle du téléphone, vous pouvez la paramétrer seulement pour NeReO, sans toucher au reste du système, de deux manières :

dans l'application : Apparence -> Langue

depuis le système : Paramètres -> Applications -> NeReO -> Langue (seulement Android 13 et ultérieurs)

Sur Android 13 et versions ultérieures, les deux voies commandent le même choix. Sur Android 12 et précédents, l'option système n'existe pas : dans ce cas on change la langue par l'interrupteur dans l'application.

Seules ces deux langues sont prévues.

==============
CE QU'IL SAIT FAIRE
LECTURE

Trois façons de voir les discussions : en arborescence, séquentielle, ou bien groupées par thread.

Vue continue : tous les messages d'une discussion l'un sous l'autre, avec saut direct au message choisi dans l'arbre.

Citations en couleurs par niveau, avec possibilité de les raccourcir.

Face (l'avatar historique d'Usenet) affichée à côté de l'auteur.

ROT13, en-têtes complets, arbre de la conversation.

Navigation par gestes : glissez pour passer au message ou à la discussion suivante.

Indication des réponses à vos messages, avec saut direct à la première non lue.

ÉCRITURE

Réponses avec citation en quatre modes, y compris la citation réduite et l'affinage ligne par ligne.

Crosspost : réponses et nouveaux threads sur plusieurs groupes.

Signature avec variantes en rotation et espaces réservés automatiques.

Identité différente pour chaque serveur, si besoin.

Brouillons sauvegardés localement.

ORGANISATION

Filtres avec actions claires (Ignorer, Éliminer, Mettre en évidence, Suivre le fil) ou bien à score pour ceux qui veulent un contrôle fin. Applicables aussi aux messages déjà téléchargés.

Plonk auteur d'un seul toucher.

Recherche par message ou par discussion.

Conservation automatique : par temps ou par nombre de discussions, paramétrable groupe par groupe.

Sauvegarde et restauration de la configuration, avec chiffrement optionnel.

APPARENCE

Thème clair, sombre ou automatique ; couleurs dynamiques Material You.

Dix teintes pour l'interface, six tailles de texte.

Disposition en panneaux pour tablettes et écrans larges, avec proportions ajustables.

CONNEXION

NNTP avec authentification, SSL/TLS sur port dédié.

Repli (fallback) automatique IPv6/IPv4.

======================
PREMIÈRE CONFIGURATION
Ouvrez le menu (trois points en haut à droite) et choisissez "Serveurs", puis le "+" en bas. Vous pouvez partir d'un serveur connu ou en insérer un à la main. Exemple gratuit : news.eternal-september.org, port 563, SSL (nécessite une inscription gratuite sur leur site).

Toujours depuis le menu, ouvrez "Identités et signature" et insérez le nom et l'e-mail avec lesquels vous voulez apparaître dans les groupes.

Retournez à la liste et touchez le "+" en bas pour parcourir les groupes disponibles sur le serveur et vous abonner à ceux qui vous intéressent.

Touchez un groupe pour télécharger ses messages.

======================
NOTES D'INSTALLATION
Si vous mettez à jour depuis une version précédente, installez l'APK par-dessus celui existant : NE DÉSINSTALLEZ PAS d'abord, ou vous perdrez les groupes, les lectures et les paramètres.

Si le téléphone bloque l'installation, autorisez les sources inconnues pour l'application depuis laquelle vous ouvrez le fichier.

Prérequis : Android 8.0 ou ultérieur.

==============
SIGNALEMENTS
Pour un signalement utile, trois choses suffisent :

ce que vous faisiez quand c'est arrivé ;

une capture d'écran, si le problème se voit ;

le bloc provenant de À propos -> Données techniques (il y a un bouton Copier).

================
HISTORIQUE DES VERSIONS
Les versions décrites de façon détaillée ci-dessus ne sont pas répétées : ceci est la queue, c'est-à-dire tout ce qui vient avant la 1.2.

1.1        Changement de langue dans l'application (Apparence -> Langue). Filet de sécurité de la base de données : copie automatique et avertissement avant une éventuelle remise à zéro pour schéma incompatible.
1.0        Première version stable. Interface en italien et en anglais.
1.0-RC01   Release candidate : traduction complète de l'interface.
beta20     Résolus trois crashs (groupes très grands, écran vide avec la touche retour, fenêtres de confirmation). Filtres applicables aux messages déjà téléchargés ; supprimer discussion ; saut aux réponses à ses propres posts ; fermer l'application ; corrections à vue continue, clavier et citation réduite.
beta19     Citation réduite corrigée ; majuscule automatique ; liste des groupes téléchargée en entier ; conservation par nombre de discussions.
beta18     Réponses à ses propres posts mises en évidence ; vue continue du thread ; filtres avec actions au lieu des scores ; crosspost sur nouveau thread.
beta17     Réponse aux crossposts en choisissant les groupes ; fallback IPv6/IPv4.
beta16     Corrections à la disposition horizontale et à l'interface.
beta14-15  En-têtes complets ; Face fiables ; affiner citation.
beta11-13  Recherche ; réordonner groupes ; crosspost ; splashscreen.
beta7-10   De MyNewsReader à NeReO ; brouillons ; identités multiples.
beta1      Première sortie.

===============
REMERCIEMENTS
NeReO est arrivé jusqu'ici grâce à ceux qui l'ont testé patiemment version après version, signalant les défauts, proposant des fonctions et insistant quand quelque chose n'était pas convaincant.

Un grand merci à Roxy, Bingo3331, Big Umberto, rpoggi, Paul, Dave Royal, Alessandro, John (n2n), Macchia, le Mérovingien, Adam H. Kerman, ObiWan, Ammammata, Henry The Mole, SiMcarD, cercaparole et Gabx : beaucoup de choses qui fonctionnent bien aujourd'hui existent parce que quelqu'un a pris la peine d'expliquer avec précision ce qui n'allait pas.

UN REMERCIEMENT PARTICULIER à ALLEN, pour un support toujours utile et TRANSVERSAL, en coulisses. Il n'apparaît pas ci-dessus à côté d'une fonction, et la raison est justement celle-là : sa contribution n'a pas été un signalement sur un point, ça a été d'être présent là où il le fallait, version après version. C'est le genre d'aide qui ne laisse pas de trace dans un changelog et sans laquelle un changelog comme celui-ci n'existerait pas.

Merci à IVO GANDOLFO, administrateur de paganini.bofh.team, qui a renouvelé et redistribué le certificat du serveur en fermant un cas que nous avions ouvert : une intervention sur un serveur qui n'est pas le nôtre, au bénéfice de tous les clients qui s'y connectent.

Le projet naît du MOD 17 de Newsreader de Piao Hong, dont il reprend la disposition de l'interface.

Usenet Still Lives!
MCSM (alias Marco)

Traduire un autre fichier ?

Oui




Gemini est une IA et peut se tromper, y compris sur des personnes. Votre confidentialité et GeminiS'ouvre dans une nouvelle fenêtre

Translating software documentation

