package facade;

import LesActions.Actions;
import exceptions.*;
import modele.*;
import modele.interfaces.ICartes;

import java.util.Collection;
import java.util.List;

//contient les différentes actions qu'on peut effectuer sur le plateau
public interface IFacadePandemicOnline {//contient les différentes actions qu'on peut effectuer sur le plateau
 public boolean partieInitialisee(String idPartie);

 public boolean inscription(String nomJoueur, String mdp);

 public boolean seConnecter(String nomJoueur, String mdp);
 public void creerPartie(Long id, String nomJoueur) throws PartiePleineException,ActionNotAutorizedException;

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

 public void traiterMaladie(Long idPartie, String nomJoueur, CouleursMaladie couleurMaladie, Actions actions);
 public void construireStationRecherche(Long idPartie,String nomJoueur, Actions actions) throws CentreRechercheDejaExistantException,
         NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException;

 public void deplacerStationRecherche(Long idPartie,String nomJoueur, Actions actions, Ville ville) throws CentreRechercheDejaExistantException,
         CentreRechercheInexistantException, VilleIdentiqueException;

 public void decouvrirRemede(Long idPartie,String nomJoueur,Actions actions) throws CentreRechercheInexistantException;
 public void piocherCarte(Long idPartie,String nomJoueur,Actions actions, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes,
         NombreCarteDepasseException;

 public void echangerCarte(Long idPartie,String nomJoueurDonneur, String nomJoueurReceveur, CartesJoueur carte,Actions actions) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur;
}