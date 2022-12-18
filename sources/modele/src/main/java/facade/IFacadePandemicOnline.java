package facade;

import exceptions.PartieNonRepriseException;
import exceptions.PartiePleineException;
import modele.Partie;
import modele.Plateau;
import modele.Ville;
import modele.interfaces.ICartes;

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
 public  boolean peutQuitterLaPartie(String idPartie);
 public  boolean createurPartie(String idPartie, String nomJoueur);
 //permet au joueur de piocher des cartes dans mes différentes piles de cartes
 public void piocherCarte(ICartes carte);

 public void seDeplacer();
 public void contruireUneStationDeRcherche();
 //permet de traiter une maladie dans la ville où on se trouve
 public void traiterUneMaladie();
 //permet de partager des connaissances avec un joueur en lui donnant ou en prenant la carte ville dans laquelle on se trouve
 public void partagerDesConnaissances();
 public void trouvrerRemede();
 public boolean carte_en_main_par_rapport_a_position(Ville ville);
 public void diminuerActions();
 public boolean carte_en_main();
 public void diminuer_carte_en_main();
 public void distribution(String idPartie);
 void partieCommence(String idPartie);
 void accederPartie(String idPartie, String pseudo);
 void accederUnePartie(String idPartie, String pseudo);
 boolean connexion(String pseudo, String mdp);
 boolean reAccederAuJeu(String idPartie,String pseudo);
 Collection<Plateau> getLesPartiesSuspendues(String idPArtie, String pseudo);
}
