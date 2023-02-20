package LesActions;

import exceptions.*;
import modele.*;
import modele.interfaces.ICartes;

import java.util.List;

public interface IAction {

    void traiterMaladie(Joueur joueur, CouleursMaladie couleurMaladie);
    void construireStationRecherche(Joueur joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
    void deplacerStationRecherche(Joueur joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException;
    void decouvrirRemede(Joueur joueur) throws CentreRechercheInexistantException;
    void piocherCarte(Joueur joueur, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException;
    void echangerCarte(Joueur joueur, Joueur joueurReceveur, CartesJoueur carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;

    //Cartes évènement
    void subventionPublique(Joueur joueur, Ville ville)throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException;
    void parUneNuitTranquille(Plateau plateau);
    void populationResiliente(ICartes choix, Plateau plateau) throws CarteDejaExistanteException, ActionNotAutorizedException;

    void deplacerUnPionQuelconque(Joueur joueur,Ville ville) throws ActionNotAutorizedException;
    void prevention(Plateau plateau);

}
