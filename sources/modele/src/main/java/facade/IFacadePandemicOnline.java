package facade;

import LesActions.Actions;
import exceptions.*;
import modele.*;

import java.util.Collection;
import java.util.List;

//contient les différentes actions qu'on peut effectuer sur le plateau

public interface IFacadePandemicOnline {
 //contient les différentes actions qu'on peut effectuer sur le plateau

 public boolean inscription(String nomJoueur, String mdp);
 public Joueur findJoueurByName(String nomJoueur);

 public boolean seConnecter(String nomJoueur, String mdp);

 public String creerPartie(String nomJoueur) throws PartiePleineException,ActionNotAutorizedException;

 public boolean partieInitialisee(String idPartie) throws ActionNotAutorizedException;

 public Partie rejoindrePartie(String idPartie,String nomJoueur);
 public boolean supprimerLesParties();
 public Collection<Partie> getLesParties();
 public Collection<Joueur> getLesJoueurs();
 public boolean supprimerLesJoueurs();

 public String getEtatPartie(String idPartie);

 public Collection<Partie> getLesPartiesSuspendues();
 public Collection<Partie> getMesPartiesSuspendues(String nomJoueur);

 public boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException;

 public boolean quitterLaPartie(String idPartie, String nomJoueur);

 public boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException;

 public boolean peutQuitterLaPartie(String idPartie);

 public boolean createurPartie(String idPartie, String nomJoueur);
 //modification le 02/04/2023
 public void traiterMaladie(String idPartie, String nomJoueur, String couleurMaladieStr);
 //modification le 02/04/2023
 public void construireStationRecherche(String idPartie,String nomJoueur) throws CentreRechercheDejaExistantException,
         NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
 //modification le 02/04/2023
 public void deplacerStationRecherche(String idPartie,String nomJoueur, String villeStr) throws CentreRechercheDejaExistantException,
         CentreRechercheInexistantException, VilleIdentiqueException;

 public void decouvrirRemede(String idPartie,String nomJoueur) throws CentreRechercheInexistantException;
 public void piocherCarte(String idPartie,String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes,
         NombreCarteDepasseException;

 public void echangerCarte(String idPartie,String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;


}