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

RELATION ENTRE LES DIFERENTES CLASSES
Nous avons utilisé la composition pour établir les liens entre les différents objets.

Tests Effectués
TestVille qui contient les methodes de test pour :
    Definir un centre de recherche
    Preciser si une ville est un centre de recherche
    Avoir le nom d’une ville
    Ajouter des cubes maladie
    Ajouter voisin d’une ville
TestCarte qui contient les methodes de test pour :
    Nom de la ville
    Couleur de la maladie


    TestVille qui contient les methodes de test pour :
        Definir un centre de recherche
        Preciser si une ville est un centre de recherche
        Avoir le nom d’une ville
        Ajouter des cubes maladie
        Ajouter voisin d’une ville
    TestCarte qui contient les methodes de test pour :
        Nom de la ville
        Couleur de la maladie
    Persistance de données:
    Grace a  la persistante de données en Mangodb on aura la  possibilité d’enregistrer l’etat des parties et pouvoir  reprendre une partie. On pourra ainsi avoir une historisation  des parties effectués , avec indication des différent scores.


Améliorations en vue
Nous envisageons d’utiliser le design pattern Décorateur afin d’améliorer et de réimplémenter les différentes actions (de la carte rôle) d’un joueur en fonction de son rôle.
