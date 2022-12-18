package facade;

import LesActions.Actions;
import exceptions.*;
import modele.*;
import modele.interfaces.ICartes;

import java.util.Collection;
import java.util.List;

//contient les différentes actions qu'on peut effectuer sur le plateau
public interface IFacadePandemicOnline {
 //contient les différentes actions qu'on peut effectuer sur le plateau
 public boolean partieInitialisee(String idPartie);

 public void inscription(String nomJoueur, String mdp);

 public boolean seConnecter(String nomJoueur, String mdp);

 public void creerPartie(String id, String nomJoueur) throws PartiePleineException;

 public Collection<Partie> getLesParties();

 public String getEtatPartie(String id);

 public Collection<Partie> getLesPartiesSuspendues();

 public boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException;

 public boolean quitterLaPartie(String idPartie, String nomJoueur);

 public boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException;

 public boolean peutQuitterLaPartie(String idPartie);

 public boolean createurPartie(String idPartie, String nomJoueur);

 public void traiterMaladie(String idPartie, String nomJoueur, CouleursMaladie couleurMaladie, Actions actions);
 public void construireStationRecherche(String idPartie,String nomJoueur, Actions actions) throws CentreRechercheDejaExistantException,
         NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;

 public void deplacerStationRecherche(String idPartie,String nomJoueur, Actions actions, Ville ville) throws CentreRechercheDejaExistantException,
         CentreRechercheInexistantException, VilleIdentiqueException;

 public void decouvrirRemede(String idPartie,String nomJoueur,Actions actions) throws CentreRechercheInexistantException;
 public void piocherCarte(String idPartie,String nomJoueur,Actions actions, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes,
         NombreCarteDepasseException;

 public void echangerCarte(String idPartie,String nomJoueurDonneur, String nomJoueurReceveur, CartesJoueur carte,Actions actions) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;
}