package interfaces;

import objets.Ville;

public class Joueur {
    private String id;
    private String mdp;
    private Ville position;

    //constructeur
    public Joueur() {}

    public Joueur(String id, String mdp) {
        this.id = id;
        this.mdp = mdp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getMdp() {return mdp;}

    public void setMdp(String mdp) {this.mdp = mdp;}
}
