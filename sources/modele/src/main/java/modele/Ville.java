package modele;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ville {
    private String nomVille;
    private String nomPays;
    private int nombreDeCube;
    public Map<CouleursMaladie, Integer> cube;
    private CouleursMaladie maladie;
    private int nombreHabitant;
    private Set<Joueur> joueurs;
    private double superficie;
    private List<Ville> villesLiees;
    //private int nombre_carte_propagation= 1; //chaque ville a exactement 1 carte propagation
    private Boolean aUnCentreDeRecherche;

    public Ville(String nomVille, String nomPays, int nombreDeCube, CouleursMaladie couleursMaladie, int nombreHabitant, double superficie, Boolean aUnCentreDeRecherche) {
        this.nomVille = nomVille;
        this.nomPays = nomPays;
        this.nombreDeCube = nombreDeCube;
        this.cube = cube;
        this.maladie = couleursMaladie;
        this.nombreHabitant = nombreHabitant;
        this.superficie = superficie;
        this.villesLiees = villesLiees;
        //this.nombre_carte_propagation = nombre_carte_propagation;
        this.aUnCentreDeRecherche = aUnCentreDeRecherche;
        joueurs = new HashSet<Joueur>();
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public int getNombreDeCube() {
        return nombreDeCube;
    }

    public void setNombreDeCube(int nombreDeCube) {
        this.nombreDeCube = nombreDeCube;
    }

    public Map<CouleursMaladie, Integer> getCube() {
        return cube;
    }

    public void setCube(Map<CouleursMaladie, Integer> cube) {
        this.cube = cube;
    }

    public CouleursMaladie getMaladie() {
        return maladie;
    }

    public void setMaladie(CouleursMaladie maladie) {
        this.maladie = maladie;
    }

    public int getNombreHabitant() {
        return nombreHabitant;
    }

    public void setNombreHabitant(int nombreHabitant) {
        this.nombreHabitant = nombreHabitant;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public List<Ville> getVillesLiees() {
        return villesLiees;
    }

    public void setVillesLiees(List<Ville> villesLiees) {
        this.villesLiees = villesLiees;
    }
    public Boolean getaUnCentreDeRecherche() {return aUnCentreDeRecherche;}

    public void setaUnCentreDeRecherche(Boolean aUnCentreDeRecherche) {
        this.aUnCentreDeRecherche = aUnCentreDeRecherche;
    }
    public boolean aUnCentreDeRecherche(){return aUnCentreDeRecherche;}

    /*public int getNombre_carte_propagation() {
        return nombre_carte_propagation;
    }

    public void setNombre_carte_propagation(int nombre_carte_propagation) {
        this.nombre_carte_propagation = nombre_carte_propagation;
    }
*/
    public void ajouterVillesLiees(Ville ville){ this.villesLiees.add(ville);}


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ville)
            return this.nomVille.equals(((Ville) obj).getNomVille());
        else
            return false;
    }

    public void ajouterCube(CouleursMaladie couleursMaladie){
        int nombre = cube.get(couleursMaladie);
        cube.put(couleursMaladie, nombre+1);

    }

    @Override
    public String toString() {
        return "Ville{" +
                "nomVille='" + nomVille + '\'' +
                ", nomPays='" + nomPays + '\'' +
                ", nombreDeCube=" + nombreDeCube +
                ", maladie=" + maladie +
                ", nombreHabitant=" + nombreHabitant +
                ", superficie=" + superficie +
                '}';
    }
}
