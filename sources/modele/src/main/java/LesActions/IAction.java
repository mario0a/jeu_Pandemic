package LesActions;

import exceptions.*;
import modele.CartesJoueur;
import modele.CouleursMaladie;
import modele.Joueur;
import modele.Ville;

import java.util.List;

public interface IAction {

    void traiterMaladie(Joueur joueur, CouleursMaladie couleurMaladie);
    void construireStationRecherche(Joueur joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
    void deplacerStationRecherche(Joueur joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException;
    void decouvrirRemede(Joueur joueur) throws CentreRechercheInexistantException;
    void piocherCarte(Joueur joueur, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException;
    void echangerCarte(Joueur joueur, Joueur joueurReceveur, CartesJoueur carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions;

}
