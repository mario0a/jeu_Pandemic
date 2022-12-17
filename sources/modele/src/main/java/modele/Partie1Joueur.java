package modele;

import LesActions.*;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.interfaces.ICartes;

import java.util.List;

public class Partie1Joueur {
    private Joueur joueur;
    private IDeplacements deplacement;
    private Plateau partie;
    private List<JeuDeCartes> cartesVille;
    private List<JeuDeCartes> cartEpidemie;
    private List<JeuDeCartes> carteEvenement;
    private List<JeuDeCartes> cartePropagation;
    private int nombre_action =4;
    private List<Ville> villesPartie;
    private int nombreCarteJoueur;


    public Partie1Joueur(Joueur joueur, IDeplacements deplacement, Plateau partie, List<JeuDeCartes> cartesVille, List<JeuDeCartes> cartEpidemie, List<JeuDeCartes> carteEvenement, List<JeuDeCartes> cartePropagation, int nombre_action, List<CartesJoueur> cartes_en_main, int nombreCarteJoueur, List<Ville> villesPartie) {
        this.joueur = joueur;
        this.deplacement = deplacement;
        this.partie = partie;
        this.cartesVille = cartesVille;
        this.cartEpidemie = cartEpidemie;
        this.carteEvenement = carteEvenement;
        this.cartePropagation = cartePropagation;
        this.nombre_action = nombre_action;
        this.villesPartie= villesPartie;
        this.nombreCarteJoueur = nombreCarteJoueur;
    }

    public int getNombre_action() {
        return nombre_action;
    }

    public void setNombre_action(int nombre_action) {this.nombre_action = nombre_action;}

    public List<Ville> getVillesPartie() {
        return villesPartie;
    }

    public void setVillesPartie(List<Ville> villesPartie) {
        this.villesPartie = villesPartie;
    }

    public void joueurSeDeplacerVoiture(Joueur joueur, Ville choix) throws PasCentreRechercheException {
        this.deplacement= new DeplacementAvecVoiture();
        this.deplacement.operationDeplacement(joueur, choix);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public IDeplacements getDeplacement() {
        return deplacement;
    }

    public Plateau getPartie() {
        return partie;
    }

    public List<JeuDeCartes> getCartesVille() {
        return cartesVille;
    }

    public List<JeuDeCartes> getCartEpidemie() {
        return cartEpidemie;
    }

    public List<JeuDeCartes> getCarteEvenement() {
        return carteEvenement;
    }

    public List<JeuDeCartes> getCartePropagation() {
        return cartePropagation;
    }
    public int getNombreCarteJoueur() {
        return nombreCarteJoueur;
    }

    public void joueurSeDeplacerNavette(Joueur joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementNavette();
        this.deplacement.operationDeplacement(joueur, choix);
    }

    public void joueurSeDeplacerVolCharter(Joueur joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementVolCharter();
        this.deplacement.operationDeplacement(joueur, choix);
    }

    public void joueurSeDeplacerVolDirect(Joueur joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement = new DeplacementVolDirect();
        this.deplacement.operationDeplacement( joueur,choix);
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    public boolean carte_en_main(CartesJoueur carte){
        for (CartesJoueur carteSuivante:this.joueur.getCartes_en_main()) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }

    public void addCarteDeffaussee(ICartes uneCarte) {
    }
}
