package modele;

import LesActions.*;
import exceptions.CarteArriveeInexistanteException;
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

    private int nombreCarteJoueur;


    public Partie1Joueur(Joueur joueur, IDeplacements deplacement, Plateau partie, List<JeuDeCartes> cartesVille, List<JeuDeCartes> cartEpidemie, List<JeuDeCartes> carteEvenement, List<JeuDeCartes> cartePropagation, int nombre_action, List<CartesJoueur> cartes_en_main, int nombreCarteJoueur) {
        this.joueur = joueur;
        this.deplacement = deplacement;
        this.partie = partie;
        this.cartesVille = cartesVille;
        this.cartEpidemie = cartEpidemie;
        this.carteEvenement = carteEvenement;
        this.cartePropagation = cartePropagation;
        this.nombre_action = nombre_action;
        this.nombreCarteJoueur = nombreCarteJoueur;
    }

    public int getNombre_action() {
        return nombre_action;
    }

     public void setNombre_action(int nombre_action) {this.nombre_action = nombre_action;}

    public void joueurSeDeplacerVoiture(Joueur joueur) throws CarteArriveeInexistanteException {
        this.deplacement= new DeplacementAvecVoiture();
        this.deplacement.operationDeplacement(joueur);
    }

    public void joueurSeDeplacerNavette(Joueur joueur) throws CarteArriveeInexistanteException {
        this.deplacement= new DeplacementNavette();
        this.deplacement.operationDeplacement(joueur);
    }

    public void joueurSeDeplacerVolCharter(Joueur joueur) throws CarteArriveeInexistanteException {
        this.deplacement= new DeplacementVolCharter();
        this.deplacement.operationDeplacement(joueur);
    }

    public void joueurSeDeplacerVolDirect(Joueur joueur) throws CarteArriveeInexistanteException {
        this.deplacement = new DeplacementVolDirect();
        this.deplacement.operationDeplacement( joueur);
    }
    //retire une carte des cartes que le joueur a en main
    public void diminuer_carte_en_main (CartesJoueur carte){
        this.joueur.getCartes_en_main().removeIf(cartesJoueur -> cartesJoueur == carte);
    }

    /* regarde la carte qu'on a en main par rapport à la ville dans laquelle on se trouve*/
    //mettre dans la classe PartieJoueur
    public boolean carte_en_main_par_rapport_a_position(Ville ville){
        for (CartesJoueur carteSuivante:this.joueur.getCartes_en_main()) {
            if (carteSuivante.informations().equals(ville.getNomVille())){
                return true;
            }
        }
        return false;
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    //à mettre dans la classe partieJoueur
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
