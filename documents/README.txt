Deuxième semestre : Client léger Angular  et Spring boot

Le projet n'est pas en ligne donc pour pouvoir le lancer faut démarrer spring boot et angular



Pour le backend, il faut la JDK 17 et démarrer seulement le projet springboot.

Pour le frontend:
    notice de lancement du projet angular:

-récupérer le projet angular
-l'ouvrir avec un IDE
-installer la version 14.16.0 du node js
-ouvrir le terminal et taper la commande `npm install`
    -Si le npm pose problème: taper la commande `npm install -g @angular/cli`


Pour pouvoir lancer le jeu, il faut démarrer spring boot (qui va tourner sur le port 8081) et angular (sur le port 4200) en même temps.

Etant donné que nous avons deux clients (un client léger = Angular et un client lourd = JavaFx), nous avons deux backends qui sont quasiment similaire à quelques modifications près.

Donc il se peut que l'un des deux clients ne fonctionne pas avec le backend de l'autre client.

Ce backend fonctionne avec Angular.

Toute les fonctionnalités du jeu sont développées dans le backend qui contient 3 modules:

le modele : où se trouve toutes les entités et la façade ainsi que les interfaces

pour les méthodes métiers et le dao pour les requêtes à la base de données MongoDB.

ModeleDtos : qui contient toutes les dtos dont on a besoin pour transferer des données.

Spring boot : qui contient les api développées qui sont les web services rest

Anglar : c'est le client léger où on a développé les différents écrans du jeu.

La communication se fait bien entre le frontend et le backend.



Premier semestre : Le modèle du jeu Pandemic

Documents
CF : le diagramme de classe est dans "documents" sous forme d'image

LES DIFFERENTS OBJETS

La classe Plateau
Représente le plateau de jeu avec l’ensemble des éléments nécessaires au déroulement du jeu.

La classe Ville
est celle qui représente la structure d’une ville de base avec ce dont elle est constituée (ses caractéristiques).

La classe Partie1Joueur
Représente la partie d’un seul joueur dans le jeu. Elle contient tout ce dont a besoin un joueur pour faire sa partie. Elle est propre à chaque joueur d’une partie de jeu.
La classe Joueur est un prototype d’un joueur. Elle dit ce qui constitue un joueur dans son ensemble.

La clase Partie
Elle est le cœur du déroulement du jeu entre les différents joueurs. Elle représente la partie générale dans le jeu avec toutes les interactions nécessaires au déroulement d’un partie de jeu.

La classe JeuDeCartes
C’est l’ensemble de toutes les différentes cartes qu’on peut retrouver sur le plateau avec toutes les informations les concernant. On y trouve également tous les liens qu’il peut y avoir entre différents éléments du plateau (notamment les liens entre toutes les villes du plateau).

La classe CarteVille

Elle dit de quoi est constituée une carte ville.

La classe CarteEpidemie
Elle dit ce qui constitue une carte épidémie.

La classe CarteJoueur
Elle donne les éléments représentant une carte de type joueur.

La classe CarteEvenement
Elle dit de quoi est constituée une carte évènement.

RELATION ENTRE LES DIFFERENT CLASSES.

Nous avons utilisé la composition pour établir les liens entre les différents objets.

Tests Effectués
TestVille qui contient les méthodes de test pour :
    Définir un centre de recherche
    Preciser si une ville est un centre de recherche
    Avoir le nom d’une ville
    Ajouter des cubes maladie
    Ajouter voisin d’une ville
TestCarte qui contient les méthodes de test pour :
    Nom de la ville
    Couleur de la maladie


    TestVille qui contient les méthodes de test pour :
        Définir un centre de recherche
        Preciser si une ville est un centre de recherche
        Avoir le nom d’une ville
        Ajouter des cubes maladie
        Ajouter voisin d’une ville
    TestCarte qui contient les méthodes de test pour :
        Nom de la ville
        Couleur de la maladie
    Persistance de données en Mongodb :
    Grâce à la persistante de données en Mongodb on aura la possibilité d’enregistrer l’état des parties et pouvoir reprendre une partie.
    On pourra ainsi avoir une historisation des parties effectuées avec indication des différents scores.



Améliorations en vue
Nous envisageons d’utiliser le design pattern Décorateur afin d’améliorer et de réimplémenter les différentes actions (de la carte rôle) d’un joueur en fonction de son rôle.
