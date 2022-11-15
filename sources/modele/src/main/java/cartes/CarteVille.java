package cartes;

import objets.CouleursMaladie;

public class CarteVille {
    private String nomVille;
    private String nomPays;
    private int population;
    private String superficie;
    private CouleursMaladie couleursMaladie;

    public CarteVille(String nomVille, String nomPays, int population, String superficie, CouleursMaladie couleursMaladie) {
        this.nomVille = nomVille;
        this.nomPays = nomPays;
        this.population = population;
        this.superficie = superficie;
        this.couleursMaladie = couleursMaladie;
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public CouleursMaladie getCouleursMaladie() {
        return couleursMaladie;
    }

    public void setCouleursMaladie(CouleursMaladie couleursMaladie) {
        this.couleursMaladie = couleursMaladie;
    }
}
