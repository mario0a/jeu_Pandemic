package facade;

import exceptions.PartieNonRepriseException;
import exceptions.PartiePleineException;
import modele.Partie;

import java.util.Collection;

//contient les différentes actions qu'on peut effectuer sur le plateau
public interface IFacadePandemicOnline {
 //contient les différentes actions qu'on peut effectuer sur le plateau
 public boolean partieInitialisee(String idPartie);

 public  void inscription(String nomJoueur, String mdp);
 public  boolean seConnecter(String nomJoueur, String mdp);
 public  void creerPartie(String id, String nomJoueur) throws PartiePleineException;
 public  Collection<Partie> getLesParties();
 public  String getEtatPartie(String id);
 public  Collection<Partie> getLesPartiesSuspendues();
 public  boolean suspendreLaPartie(String idPartie, String nomJoueur)throws PartieNonRepriseException;
 public  boolean quitterLaPartie(String idPartie, String nomJoueur);
}
