package LesActions;

import exceptions.*;
import modele.*;

import java.util.List;

public interface IAction {
    void traiterMaladie(Partie partie,Partie1Joueur partie1Joueur, CouleursMaladie couleurMaladieStr);
    void construireStationRecherche(Partie partie,Partie1Joueur partie1Joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
    void deplacerStationRecherche(Partie1Joueur partie1Joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException;
    void decouvrirRemede(Partie partie,Partie1Joueur partie1Joueur) throws CentreRechercheInexistantException;
    void piocherCarte(Partie1Joueur partie1Joueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException;
    void echangerCarte(Partie1Joueur partie1Joueur, Partie1Joueur joueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;


    void retireCubeAutomatiquement(Partie1Joueur partie1Joueur, Ville ville, CouleursMaladie couleursMaladie) throws ActionNotAutorizedException;
    // Cartes évènements

    void subventionPublique(Partie partie,Partie1Joueur partie1Joueur, Ville ville) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException;

    void parUneNuitTranquille(Plateau plateau);
    void populationResiliente(Carte choix, Plateau plateau) throws  ActionNotAutorizedException;

    void deplacerUnPionQuelqconque(Partie1Joueur joueurDeplace,Partie1Joueur joueurADeplacer, Ville ville) throws ActionNotAutorizedException;
    void prevention(Plateau plateau);

}