package LesActions;

import exceptions.CartesJoueurInsuffisantes;
import exceptions.NombreCarteDepasseException;
import modele.CartesJoueur;
import modele.Joueur;

import java.util.List;

public interface PiocherCartes {
    void piocherCarte(Joueur joueur, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException;
}
