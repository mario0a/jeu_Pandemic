package modele;

import LesActions.*;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import facade.JeuDeCartes;

import java.util.ArrayList;
import java.util.List;

public class Partie1Joueur {
    private String nom;
    private IDeplacements deplacement;
    private List<Carte> cartesVille = new ArrayList<>();
    private List<Carte> cartEpidemie = new ArrayList<>();
    private List<Carte> carteEvenement = new ArrayList<>();
    private List<Carte> cartePropagation = new ArrayList<>();
    private int nombre_action =4;
    private List<Ville> villesPartie = new ArrayList<>();
    private int nombreCarteJoueur = 0;
    private boolean createur =  false;
    private List<Carte> cartes_en_main = new ArrayList<>();
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
    public Partie1Joueur() {
    }

    public Partie1Joueur(String nom, IDeplacements deplacement, List<Carte> cartesVille, List<Carte> cartEpidemie, List<Carte> carteEvenement, List<Carte> cartePropagation, int nombre_action, List<Ville> villesPartie, int nombreCarteJoueur, boolean createur, List<Carte> cartes_en_main, Ville position, TypeRole typeRole, boolean autorisationDeplacementPion) {
        this.nom = nom;
        this.deplacement = deplacement;
        this.cartesVille = cartesVille;
        this.cartEpidemie = cartEpidemie;
        this.carteEvenement = carteEvenement;
        this.cartePropagation = cartePropagation;
        this.nombre_action = nombre_action;
        this.villesPartie = villesPartie;
        this.nombreCarteJoueur = nombreCarteJoueur;
        this.createur = createur;
        this.cartes_en_main = cartes_en_main;
        this.position = position;
        this.typeRole = typeRole;
        this.autorisationDeplacementPion = autorisationDeplacementPion;
    }

    @Override
    public String toString() {
        return "Partie1Joueur{" +
                "joueur='" + nom + '\'' +
                ", deplacement=" + deplacement +
                ", cartesVille=" + cartesVille +
                ", cartEpidemie=" + cartEpidemie +
                ", carteEvenement=" + carteEvenement +
                ", cartePropagation=" + cartePropagation +
                ", nombre_action=" + nombre_action +
                ", villesPartie=" + villesPartie +
                ", nombreCarteJoueur=" + nombreCarteJoueur +
                ", createur=" + createur +
                ", cartes_en_main=" + cartes_en_main +
                ", position=" + position +
                ", typeRole=" + typeRole +
                ", autorisationDeplacementPion=" + autorisationDeplacementPion +
                '}';
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

    public void joueurSeDeplacerVoiture(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        this.deplacement= new DeplacementAvecVoiture();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public String getNom() {
        return nom;
    }

    public IDeplacements getDeplacement() {
        return deplacement;
    }


    public List<Carte> getCartesVille() {
        return cartesVille;
    }

    public List<Carte> getCartEpidemie() {
        return cartEpidemie;
    }

    public List<Carte> getCarteEvenement() {
        return carteEvenement;
    }

    public List<Carte> getCartePropagation() {
        return cartePropagation;
    }
    public int getNombreCarteJoueur() {
        return nombreCarteJoueur;
    }

    public boolean isCreateur() {
        return createur;
    }

    public void joueurSeDeplacerNavette(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementNavette();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public void joueurSeDeplacerVolCharter(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementVolCharter();
        this.deplacement.operationDeplacement(partie,partie1Joueur, choix);
    }

    public void joueurSeDeplacerVolDirect(Partie partie,Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement = new DeplacementVolDirect();
        this.deplacement.operationDeplacement(partie, partie1Joueur,choix);
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    public boolean carte_en_main(Carte carte){
        for (Carte carteSuivante:this.getCartes_en_main()) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }

    public void addCarteDeffaussee(Carte uneCarte) {
    }

    public List<Carte> getCartes_en_main() {
        return this.cartes_en_main;
    }

    public void setCartes_en_main(List<Carte> cartesEnMain) {
        this.cartes_en_main = cartesEnMain;
    }

    public Ville getPosition() {
        return this.position;
    }

    public TypeRole getTypeRole() {
        return this.typeRole;
    }

    public boolean getAutorisationDeplacementPion() {
        return this.autorisationDeplacementPion;
    }

    public void setPosition(Ville ville) {
        this.position = ville;
    }

    public void setAutorisationDeplacementPion(boolean b) {
        this.autorisationDeplacementPion = b;
    }


    public Partie1Joueur setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public Partie1Joueur setDeplacement(IDeplacements deplacement) {
        this.deplacement = deplacement;
        return this;
    }


    public Partie1Joueur setCartesVille(List<Carte> cartesVille) {
        this.cartesVille = cartesVille;
        return this;
    }

    public Partie1Joueur setCartEpidemie(List<Carte> cartEpidemie) {
        this.cartEpidemie = cartEpidemie;
        return this;
    }

    public Partie1Joueur setCarteEvenement(List<Carte> carteEvenement) {
        this.carteEvenement = carteEvenement;
        return this;
    }

    public Partie1Joueur setCartePropagation(List<Carte> cartePropagation) {
        this.cartePropagation = cartePropagation;
        return this;
    }

    public Partie1Joueur setNombreCarteJoueur(int nombreCarteJoueur) {
        this.nombreCarteJoueur = nombreCarteJoueur;
        return this;
    }

    public Partie1Joueur setCreateur(boolean createur) {
        this.createur = createur;
        return this;
    }

    public Partie1Joueur setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
        return this;
    }

    public boolean isAutorisationDeplacementPion() {
        return autorisationDeplacementPion;
    }
}