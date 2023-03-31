package modele;

import LesActions.*;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import facade.JeuDeCartes;
import modele.interfaces.ICartes;

import java.util.ArrayList;
import java.util.List;

public class Partie1Joueur {

    private String nom;
    private IDeplacements deplacement;
    private Plateau plateau;
    private List<JeuDeCartes> cartesVille = new ArrayList<>();
    private List<JeuDeCartes> cartEpidemie= new ArrayList<>();
    private List<JeuDeCartes> carteEvenement= new ArrayList<>();
    private List<JeuDeCartes> cartePropagation= new ArrayList<>();
    private int nombre_action =4;
    private List<Ville> villesPartie = new ArrayList<>();
    private int nombreCarteJoueur=0;
    private boolean createur=false;
    private Ville position;
    private TypeRole typeRole;
    private List<CartesJoueur> cartes_en_main= new ArrayList<>();
    private boolean autorisationDeplacementPion = false;

    public Partie1Joueur() {}

    public Partie1Joueur(String nomJoueur, boolean b) {
        this.nom = nomJoueur;
        this.createur = b;
    }

    public Partie1Joueur(String nom, IDeplacements deplacement, Plateau plateau, List<JeuDeCartes> cartesVille, List<JeuDeCartes> cartEpidemie, List<JeuDeCartes> carteEvenement, List<JeuDeCartes> cartePropagation, int nombre_action, List<Ville> villesPartie, int nombreCarteJoueur, boolean createur, List<CartesJoueur> cartes_en_main, Ville position, TypeRole typeRole, boolean autorisationDeplacementPion) {
        this.nom = nom;
        this.deplacement = deplacement;
        this.plateau = plateau;
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
                "nom='" + nom + '\'' +
                ", deplacement=" + deplacement +
                ", plateau=" + plateau +
                ", cartesVille=" + cartesVille +
                ", cartEpidemie=" + cartEpidemie +
                ", carteEvenement=" + carteEvenement +
                ", cartePropagation=" + cartePropagation +
                ", nombre_action=" + nombre_action +
                ", villesPartie=" + villesPartie +
                ", nombreCarteJoueur=" + nombreCarteJoueur +
                ", createur=" + createur +
                ", position=" + position +
                ", typeRole=" + typeRole +
                ", cartes_en_main=" + cartes_en_main +
                ", autorisationDeplacementPion=" + autorisationDeplacementPion +
                '}';
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public IDeplacements getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(IDeplacements deplacement) {
        this.deplacement = deplacement;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public List<JeuDeCartes> getCartesVille() {
        return cartesVille;
    }

    public void setCartesVille(List<JeuDeCartes> cartesVille) {
        this.cartesVille = cartesVille;
    }

    public List<JeuDeCartes> getCartEpidemie() {
        return cartEpidemie;
    }

    public void setCartEpidemie(List<JeuDeCartes> cartEpidemie) {
        this.cartEpidemie = cartEpidemie;
    }

    public List<JeuDeCartes> getCarteEvenement() {
        return carteEvenement;
    }

    public void setCarteEvenement(List<JeuDeCartes> carteEvenement) {
        this.carteEvenement = carteEvenement;
    }

    public List<JeuDeCartes> getCartePropagation() {
        return cartePropagation;
    }

    public void setCartePropagation(List<JeuDeCartes> cartePropagation) {
        this.cartePropagation = cartePropagation;
    }

    public int getNombre_action() {
        return nombre_action;
    }

    public void setNombre_action(int nombre_action) {
        this.nombre_action = nombre_action;
    }

    public List<Ville> getVillesPartie() {
        return villesPartie;
    }

    public void setVillesPartie(List<Ville> villesPartie) {
        this.villesPartie = villesPartie;
    }

    public int getNombreCarteJoueur() {
        return nombreCarteJoueur;
    }

    public void setNombreCarteJoueur(int nombreCarteJoueur) {
        this.nombreCarteJoueur = nombreCarteJoueur;
    }

    public boolean isCreateur() {
        return createur;
    }

    public void setCreateur(boolean createur) {
        this.createur = createur;
    }

    public Ville getPosition() {
        return position;
    }

    public void setPosition(Ville position) {
        this.position = position;
    }

    public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

    public List<CartesJoueur> getCartes_en_main() {
        return cartes_en_main;
    }

    public void setCartes_en_main(List<CartesJoueur> cartes_en_main) {
        this.cartes_en_main = cartes_en_main;
    }

    public boolean getAutorisationDeplacementPion() {
        return autorisationDeplacementPion;
    }

    public void setAutorisationDeplacementPion(boolean autorisationDeplacementPion) {
        this.autorisationDeplacementPion = autorisationDeplacementPion;
    }

    public void joueurSeDeplacerVoiture(Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        this.deplacement= new DeplacementAvecVoiture();
        this.deplacement.operationDeplacement(partie1Joueur, choix);
    }

    public void joueurSeDeplacerNavette(Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementNavette();
        this.deplacement.operationDeplacement(partie1Joueur, choix);
    }


    public void joueurSeDeplacerVolCharter(Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement= new DeplacementVolCharter();
        this.deplacement.operationDeplacement(partie1Joueur, choix);
    }

    public void joueurSeDeplacerVolDirect(Partie1Joueur partie1Joueur, Ville choix) throws CarteArriveeInexistanteException, PasCentreRechercheException {
        this.deplacement = new DeplacementVolDirect();
        this.deplacement.operationDeplacement( partie1Joueur,choix);
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    public boolean carte_en_main(CartesJoueur carte){
        for (CartesJoueur carteSuivante:this.getCartes_en_main()) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }

    public void addCarteDeffaussee(ICartes uneCarte) {
    }
}