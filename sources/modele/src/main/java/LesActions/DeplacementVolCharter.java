package LesActions;

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Ville;
import modele.interfaces.ICartes;

import java.util.stream.Collectors;

//Permet de se déplacer n'importe où sur le plateau
public class DeplacementVolCharter implements IDeplacements{

    @Override
    public void operationDeplacement(Joueur joueur, Ville choix) throws PasCentreRechercheException {
        if (joueur.getCartes_en_main().stream().anyMatch(carte -> carte.informations().equals(joueur.getPosition().getNomVille()))) {
            ICartes uneCarte = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(joueur.getPosition().getNomVille())).collect(Collectors.toList()).get(0);
            joueur.getCartes_en_main().remove(uneCarte);
            joueur.getPartie().getPartie().getDefausse_cartesJoueur().add(uneCarte);
            joueur.setPosition(choix);
        }
    }
}
