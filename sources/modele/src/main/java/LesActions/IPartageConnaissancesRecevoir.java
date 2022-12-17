package LesActions;

import exceptions.AbsenceCarteJoueurException;
import exceptions.NombreCarteDepasseException;
import exceptions.PositionJoueursDifferenteExceptions;
import modele.CartesJoueur;
import modele.Joueur;

public interface IPartageConnaissancesRecevoir {
    void recevoirCarte(Joueur joueur, Joueur joueurReceveur, CartesJoueur carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions;
}
