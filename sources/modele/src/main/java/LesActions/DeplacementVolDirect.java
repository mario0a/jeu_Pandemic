package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.interfaces.ICartes;
import modele.Joueur;
import modele.Ville;

// permet de se déplacer vers une ville dont on a joué la carte
public class DeplacementVolDirect implements IDeplacements {
    private Ville villeArrivee;

    @Override
    public void faireAction(Joueur joueur) throws Exception, CarteArriveeInexistanteException {
        if (joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(villeArrivee.getNomVille()))) {
            ICartes carte = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(villeArrivee.getNomVille())).collect(collectors.toList()).get(0);
            joueur.removeCarte(carte);
            joueur.getPartie().addCarteDeffaussee(carte);
        }else {
            throw new CarteArriveeInexistanteException();
        }

    }


}
