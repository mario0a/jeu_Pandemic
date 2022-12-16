package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.interfaces.ICartes;
import modele.Joueur;
import modele.Ville;

import java.util.stream.Collectors;

// permet de se déplacer vers une ville dont on a joué la carte
public class DeplacementVolDirect implements IDeplacements {
    private Ville villeArrivee;

    @Override
    public void operationDeplacement(Joueur joueur) /*throws CarteArriveeInexistanteException*/ {
        if (joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(villeArrivee.getNomVille()))) {
            ICartes uneCarte = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(villeArrivee.getNomVille())).collect(Collectors.toList()).get(0);
            //joueur.removeCarte(uneCarte);
            joueur.getCartes_en_main().remove(uneCarte);
            joueur.getPartie().addCarteDeffaussee(uneCarte);
        }


}
}
