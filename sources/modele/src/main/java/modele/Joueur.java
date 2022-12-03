package modele;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String nomJoueur;
    private String id;
    private String mdp;
    private Ville position;
    private TypeRole typeRole;
    //private List<CartesJoueur> cartes_en_main;
    //private int nombreCarteJoueur;
    private Partie1Joueur partie;
    private ArrayList<CartesJoueur> carte_a_partager = new ArrayList();

    //constructeur
    public Joueur() {}

    public Joueur(String id, String mdp) {
        this.id = id;
        this.mdp = mdp;
    }

    public Joueur(String nomJoueur, Ville position, TypeRole typeRole) {
        this.nomJoueur = nomJoueur;
        this.position = position;
        this.typeRole = typeRole;

    }

    public Partie1Joueur getPartie() {
        return partie;
    }

    public void setPartie(Partie1Joueur partie) {
        this.partie = partie;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
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

    public List<CartesJoueur> getCartes_en_main() return cartes_en_main;}

    //public void setCartes_en_main(List<CartesJoueur> cartes_en_main) {this.cartes_en_main = cartes_en_main;}

   // public int getNombreCarteJoueur() {return nombreCarteJoueur;}

    //public void setNombreCarteJoueur(int nombreCarteJoueur) {this.nombreCarteJoueur = nombreCarteJoueur;}

    public ArrayList<CartesJoueur> getCarte_a_partager() {
        return carte_a_partager;
    }

    public void setCarte_a_partager(ArrayList<CartesJoueur> carte_a_partager) {
        this.carte_a_partager = carte_a_partager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getMdp() {return mdp;}

    public void setMdp(String mdp) {this.mdp = mdp;}
}
