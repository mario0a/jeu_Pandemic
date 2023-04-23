package facade;

import LesActions.Actions;
import exceptions.*;
import modele.*;

import java.util.Collection;
import java.util.List;

//contient les différentes actions qu'on peut effectuer sur le plateau
public interface IFacadePandemicOnline {//contient les différentes actions qu'on peut effectuer sur le plateau

 public boolean inscription(String nomJoueur, String mdp);
 public Joueur findJoueurByName(String nomJoueur);

 public boolean seConnecter(String nomJoueur, String mdp);

 public Partie creerPartie(Long id, String nomJoueur) throws PartiePleineException,ActionNotAutorizedException;

 public boolean partieInitialisee(Long id) throws ActionNotAutorizedException;

 public Partie rejoindrePartie(Long idPartie,String nomJoueur);
 public boolean supprimerLesParties();
 public Collection<Partie> getLesParties();
 public Collection<Joueur> getLesJoueurs();
 public boolean supprimerLesJoueurs();

 public String getEtatPartie(Long id);

 public Collection<Partie> getLesPartiesSuspendues();

 public boolean suspendreLaPartie(Long idPartie, String nomJoueur) throws PartieNonRepriseException;

 public boolean quitterLaPartie(Long idPartie, String nomJoueur);

 public boolean reprendreUnePartie(Long idPartie, String nomJoueur) throws PartieNonSuspenduException;

 public boolean peutQuitterLaPartie(Long idPartie);

 public boolean createurPartie(Long idPartie, String nomJoueur);
 //modification le 02/04/2023
 public void traiterMaladie(Long idPartie, String nomJoueur, String couleurMaladieStr);
 //modification le 02/04/2023
 public void construireStationRecherche(Long idPartie,String nomJoueur) throws CentreRechercheDejaExistantException,
         NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;
 //modification le 02/04/2023
 public void deplacerStationRecherche(Long idPartie,String nomJoueur, String villeStr) throws CentreRechercheDejaExistantException,
         CentreRechercheInexistantException, VilleIdentiqueException;

 public void decouvrirRemede(Long idPartie,String nomJoueur) throws CentreRechercheInexistantException;
 public void piocherCarte(Long idPartie,String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes,
         NombreCarteDepasseException;

 public void echangerCarte(Long idPartie,String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;


}