package LesActions;

import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Partie1Joueur;
import modele.Ville;
import modele.interfaces.ICartes;

import java.util.stream.Collectors;

//Permet de se déplacer n'importe où sur le plateau
public class DeplacementVolCharter implements IDeplacements{

    @Override
    public void operationDeplacement(Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        if (partie1Joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(partie1Joueur.getPosition().getNomVille()))) {
            ICartes uneCarte = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(partie1Joueur.getPosition().getNomVille())).collect(Collectors.toList()).get(0);
            partie1Joueur.getCartes_en_main().remove(uneCarte);
            partie1Joueur.getPlateau().getDefausse_cartesJoueur().add(uneCarte);
            partie1Joueur.setPosition(choix);
        }
    }
}
