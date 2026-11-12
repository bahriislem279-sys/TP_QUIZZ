Compte rendu — TP Android : Quiz Culture & Monuments en images

Étudiant(e) : Anouar Dario Aissaoui

Thème choisi : Les monuments et la culture générale

Technologies : Kotlin et XML

Activité : MainActivity

1. Présentation du projet

L'application est un quiz interactif de 5 questions sur les monuments et la culture générale. À chaque question, l'utilisateur observe une illustration et sélectionne la bonne réponse parmi trois propositions. L'application valide la réponse, met à jour le score, suit la progression, et affiche un écran de résultat final avec une option pour recommencer.

2. Interface et widgets utilisés

L'interface est définie dans le fichier `activity_main.xml` en utilisant une seule activité (sans Jetpack Compose) avec un design entièrement modernisé :

- Des conteneurs de type cartes (`LinearLayout` avec fonds blancs et espacements soignés) ;
- Des `TextView` pour les titres, les scores, les numéros de question et les textes d'aide ;
- Une `ImageView` pour afficher l'illustration de la question sans déformation (`fitCenter`) ;
- Une `ProgressBar` horizontale pour suivre l'avancement du quiz ;
- Trois `Button` stylisés pour les choix multiples ;
- Un bouton pour passer à la question suivante et un bouton **Rejouer** ;
- Un `ScrollView` pour assurer un affichage fluide sur tous les types d'écrans.

3. Représentation des questions

Une `data class Question` stocke l'énoncé, l'identifiant de la ressource image (`Int`), les 3 choix (`Array<String>`), l'indice de la bonne réponse et une description textuelle pour l'accessibilité.

Les 5 questions sont stockées dans un tableau Kotlin. Les illustrations sont des ressources vectorielles locales stockées dans `res/drawable`. Aucune base de données ni connexion réseau n'est requise.

4. Gestion des événements, du score et de la progression

Les boutons de choix utilisent un parcours `forEachIndexed`. Lors du clic, la méthode `checkAnswer(index)` évalue la réponse :

- Si la réponse est correcte, le score s'incrémente de 1.
- Si elle est incorrecte, le score reste inchangé.

Une variable d'état empêche les clics multiples et désactive les boutons après le choix. La progression avance à chaque réponse. Le bouton suivant reste inactif tant qu'aucune réponse n'est fournie. À la fin de la 5ème question, le récapitulatif du score s'affiche. Le bouton **Rejouer** réinitialise l'état complet du quiz.

5. Tests à réaliser et validation

| Test | Résultat attendu |
| :--- | :--- |
| Lancer une partie | Question 1 affichée, score 0/5, 3 choix actifs |
| Répondre juste | Score +1, progression +1 |
| Répondre faux | Score inchangé, progression +1 |
| Cliquer plusieurs fois | Boutons désactivés, aucun point supplémentaire |
| Question suivante | Nouvelle question affichée, score conservé |
| Fin du quiz | Score final affiché avec félicitations |
| Rejouer | Réinitialisation complète du quiz à zéro |
| Petit écran | Défilement vertical fluide via ScrollView |
