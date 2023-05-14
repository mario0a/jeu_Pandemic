package LesActions;

import exceptions.AbsenceCarteJoueurException;
import modele.Carte;
import modele.Partie;
import modele.Partie1Joueur;
import modele.Ville;

/* permet de se déplacer vers une ville dont on a joué la carte*/
public class DeplacementVolDirect implements IDeplacements {

    @Override
    public void operationDeplacement(Partie partie, Partie1Joueur partie1Joueur, Ville choix) throws AbsenceCarteJoueurException {
        if (partie1Joueur.getCartesEnMain().stream().noneMatch(carte -> carte.getNomCarte().equals(choix.getNomVille()))) {
            throw new AbsenceCarteJoueurException();
        }
        Carte uneCarte = partie1Joueur.getCartesEnMain().stream().filter(carte -> carte.getNomCarte().equals(choix.getNomVille())).toList().get(0);
        partie1Joueur.getCartesEnMain().remove(uneCarte);
        partie.getPlateau() .getDefausse_cartesJoueur().add(uneCarte);
    }

}
