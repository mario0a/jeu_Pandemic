package LesActions;

import exceptions.PasCentreRechercheException;
import modele.Carte;
import modele.Partie;
import modele.Partie1Joueur;
import modele.Ville;

import java.util.stream.Collectors;

/* permet de se déplacer vers une ville dont on a joué la carte*/
public class DeplacementVolDirect implements IDeplacements {


    @Override
    public void operationDeplacement(Partie partie, Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        if (partie1Joueur.getCartes_en_main().stream().anyMatch(carte -> carte.getInformation().equals(choix.getNomVille()))) {
            Carte uneCarte = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.getInformation().equals(choix.getNomVille())).collect(Collectors.toList()).get(0);
            partie1Joueur.getCartes_en_main().remove(uneCarte);
            partie.getPlateau() .getDefausse_cartesJoueur().add(uneCarte);
        }
    }
}

