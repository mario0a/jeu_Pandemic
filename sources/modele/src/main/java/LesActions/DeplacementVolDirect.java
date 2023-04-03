package LesActions;

import exceptions.PasCentreRechercheException;
import modele.Partie1Joueur;
import modele.interfaces.ICartes;
import modele.Joueur;
import modele.Ville;

import java.util.stream.Collectors;

/* permet de se déplacer vers une ville dont on a joué la carte*/
public class DeplacementVolDirect implements IDeplacements {

    @Override
    public void operationDeplacement(Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        if (partie1Joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(choix.getNomVille()))) {
            ICartes uneCarte = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(choix.getNomVille())).collect(Collectors.toList()).get(0);
            partie1Joueur.getCartes_en_main().remove(uneCarte);
            partie1Joueur.getPlateau() .getDefausse_cartesJoueur().add(uneCarte);
        }
    }
}

