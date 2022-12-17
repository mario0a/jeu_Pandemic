package LesActions;

import exceptions.AbsenceCarteJoueurException;
import exceptions.NombreCarteDepasseException;
import exceptions.PositionJoueursDifferenteExceptions;
import modele.CartesJoueur;
import modele.Joueur;

public interface IPartageConnaissancesDonner {
    void donnerCarte(Joueur joueurDonneur, Joueur joueurReceveur, CartesJoueur carte) throws AbsenceCarteJoueurException, NombreCarteDepasseException, PositionJoueursDifferenteExceptions;
}
