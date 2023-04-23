package LesActions;

import exceptions.PasCentreRechercheException;
import modele.Carte;
import modele.Partie;
import modele.Partie1Joueur;
import modele.Ville;

import java.util.stream.Collectors;

//Permet de se déplacer n'importe où sur le plateau
public class DeplacementVolCharter implements IDeplacements{


    @Override
    public void operationDeplacement(Partie partie, Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        if (partie1Joueur.getCartes_en_main().stream().anyMatch(carte -> carte.getInformation().equals(partie1Joueur.getPosition().getNomVille()))) {
            Carte uneCarte = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.getInformation().equals(partie1Joueur.getPosition().getNomVille())).collect(Collectors.toList()).get(0);
            partie1Joueur.getCartes_en_main().remove(uneCarte);
            partie.getPlateau().getDefausse_cartesJoueur().add(uneCarte);
            partie1Joueur.setPosition(choix);
        }
    }
}
