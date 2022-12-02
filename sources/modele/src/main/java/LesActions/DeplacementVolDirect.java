package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.interfaces.ICartes;
import modele.Joueur;
import modele.Ville;

// permet de se déplacer vers une ville dont on a joué la carte
public class DeplacementVolDirect implements IDeplacements {
    private Ville villeArrivee;

    @Override
    public void faireAction(Joueur joueur) throws Exception {
        if (joueur.getCarteJoueur().stream().anyMatch(carte -> carte.information().equals(villeArrivee.getNomVille()))) {
            ICartes carte = joueur.getCartesJoueur().stream.filter(carte -> carte.information().equals(villeArrivee.getNomVille())).collect(collectors.toList()).get(0);
            joueur.removeCarte(carte);
            joueur.getPartie().addCarteDeffaussee(carte);
        }else {
            throw new CarteArriveeInexistanteException();
        }

    }


}
