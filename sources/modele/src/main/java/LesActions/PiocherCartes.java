package LesActions;

import exceptions.CartesJoueurInsuffisantes;
import exceptions.NombreCarteDepasseException;
import modele.CartesJoueur;
import modele.Joueur;

import java.util.List;

public class PiocherCartes implements IPiocherCartes{
    @Override
    public void piocherCarte(Joueur joueur, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        if(cartesJoueurList.size()<2) throw new CartesJoueurInsuffisantes(); // activer fin de partie
        if(joueur.getCartes_en_main().size()>5) throw new NombreCarteDepasseException();// ajouter l'option défosser carte
        joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
        joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
    }
}
