package objets;


import java.util.List;

public class Ville implements IVille{
    private String nom;
    private String nomPays;
    private List<Ville> villeVoisines;

    public Ville(String nom, String nomPays) {
        this.nom = nom;
        this.nomPays = nomPays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public List<Ville> getVilleVoisines() {
        return villeVoisines;
    }

    public void setVilleVoisines(List<Ville> villeVoisines) {
        this.villeVoisines = villeVoisines;
    }
}
