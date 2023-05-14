package modele;

import LesActions.*;

import exceptions.AbsenceCarteJoueurException;
import exceptions.PasCentreRechercheException;

import exceptions.VilleNonVoisineException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Partie1Joueur  {
    private String nom;
    private IDeplacements deplacement;
    private List<Carte> cartesVille = new ArrayList<>();
    private List<Carte> cartEpidemie = new ArrayList<>();
    private List<Carte> carteEvenement = new ArrayList<>();
    private List<Carte> cartePropagation = new ArrayList<>();
    private int nombreAction =4;
    private List<Ville> villesPartie = new ArrayList<>();
    private int nombreCarteJoueur = 0;
    private boolean createur =  false;
    private List<Carte> cartesEnMain = new ArrayList<>();
    private Ville position;
    private TypeRole typeRole;
    private boolean autorisationDeplacementPion = false;

    public Partie1Joueur(String nomJoueur, boolean b) {
        this.nom = nomJoueur;
        this.createur = b;
    }
    public Partie1Joueur(String nomJoueur) {
        this.nom = nomJoueur;
    }


    public void joueurSeDeplacerVoiture(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.deplacement= new DeplacementAvecVoiture();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public void joueurSeDeplacerNavette(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.deplacement= new DeplacementNavette();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public void joueurSeDeplacerVolCharter(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.deplacement= new DeplacementVolCharter();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public void joueurSeDeplacerVolDirect(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.deplacement = new DeplacementVolDirect();
        this.deplacement.operationDeplacement(partie, partie1Joueur,choix);
    }

    public void diminuerActions(){
        nombreAction--;}


    public boolean carteEnMain(Carte carte){
        for (Carte carteSuivante:this.getCartesEnMain()) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }
}