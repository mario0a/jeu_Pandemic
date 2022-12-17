package LesActions;

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.interfaces.ICartes;
import modele.Joueur;
import modele.Ville;

import java.util.stream.Collectors;

/* permet de se déplacer vers une ville dont on a joué la carte*/
public class DeplacementVolDirect implements IDeplacements {

    @Override
    public void operationDeplacement(Joueur joueur, Ville choix) throws PasCentreRechercheException {
        if (joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(choix.getNomVille()))) {
            ICartes uneCarte = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(choix.getNomVille())).collect(Collectors.toList()).get(0);
            joueur.getCartes_en_main().remove(uneCarte);
            joueur.getPartie().getPartie() .getDefausse_cartesJoueur().add(uneCarte);
        }
    }
}

