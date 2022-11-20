package facade;

import cartes.Carte;

import java.util.ArrayList;
import java.util.List;

public class Partie1Joueur {
    private Joueur joueur;
    private Plateau partie;
    private List<JeuDeCartes> cartesVille;
    private List<JeuDeCartes> cartEpidemie;
    private List<JeuDeCartes> carteEvenement;
    private List<JeuDeCartes> cartePropagation;


    private int nombre_action =4;


    private List<CartesJoueur> cartes_en_main;
    private int nombreCarteJoueur;





    public int getNombre_action() {
        return nombre_action;
    }

     public void setNombre_action(int nombre_action) {this.nombre_action = nombre_action;}






    //retire une carte des cartes que le joueur a en main
    public void diminuer_carte_en_main (CartesJoueur carte){

        cartes_en_main.removeIf(cartesJoueur -> cartesJoueur == carte);
    }

    /* regarde la carte qu'on a en main par rapport à la ville dans laquelle on se trouve*/
    //mettre dans la classe PartieJoueur
    public boolean carte_en_main_par_rapport_a_position(Ville ville){
        for (CartesJoueur carteSuivante:cartes_en_main) {
            if (carteSuivante.getNomVille().equals(ville.getNomVille())){
                return true;
            }
        }
        return false;
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    //à mettre dans la classe partieJoueur
    public boolean carte_en_main(CartesJoueur carte){
        for (CartesJoueur carteSuivante:cartes_en_main) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }
}
