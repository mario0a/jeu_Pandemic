package LesActions;

import exceptions.*;
import modele.Joueur;
import modele.Ville;

public interface IStationDeRecherche {
    void construireStationRecherche(Joueur joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
    void deplacerStationRecherche(Joueur joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException;
    void decouvrirRemede(Joueur joueur) throws CentreRechercheInexistantException;
}
