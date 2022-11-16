package modele;

import cartes.DefausseJoueur;
import cartes.DefaussePropagation;
import eclosions.PisteDeclosion;
import modele.EmplacementMaladies;
import modele.Ville;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
    private List<Ville> LesVilles;
    private List<CartesJoueurs> cartesJoueurs;
    private List<CartePropagation> cartesPropagation;
    private List<DefausseJoueur> cartesDeLaDefausseJoueur;
    private List<DefaussePropagation> cartesDeLaDefaussePropagation;
    private PisteDeclosion pisteDeclosion;
    //private int[] pisteDeclosion ;
    private int[] pisteDePropagation;
    private EmplacementMaladies emplacementMaladies;

    public Plateau(List<Ville> lesVilles, List<CartesJoueurs> cartesJoueurs, List<CartePropagation> cartesPropagation, List<DefausseJoueur> cartesDeLaDefausseJoueur, List<DefaussePropagation> cartesDeLaDefaussePropagation, int[] pisteDeclosion, int[] pisteDePropagation) {
        LesVilles = new ArrayList<>();
        this.cartesJoueurs = new ArrayList<>();
        this.cartesPropagation = new ArrayList<>();
        this.cartesDeLaDefausseJoueur = new ArrayList<>();
        this.cartesDeLaDefaussePropagation = new ArrayList<>();
        this.pisteDeclosion = this.pisteDeclosion;
        //this.pisteDeclosion = new int  [8];
        this.pisteDePropagation = pisteDePropagation;
        this.emplacementMaladies = new EmplacementMaladies();
    }

    public List<Ville> getLesVilles() {
        return LesVilles;
    }

    public void setLesVilles(List<Ville> lesVilles) {
        LesVilles = lesVilles;
    }

    public List<CartesJoueurs> getCartesJoueurs() {
        return cartesJoueurs;
    }

    public void setCartesJoueurs(List<CartesJoueurs> cartesJoueurs) {
        this.cartesJoueurs = cartesJoueurs;
    }

    public List<CartePropagation> getCartesPropagation() {
        return cartesPropagation;
    }

    public void setCartesPropagation(List<CartePropagation> cartesPropagation) {
        this.cartesPropagation = cartesPropagation;
    }

    public List<DefausseJoueur> getCartesDeLaDefausseJoueur() {
        return cartesDeLaDefausseJoueur;
    }

    public void setCartesDeLaDefausseJoueur(List<DefausseJoueur> cartesDeLaDefausseJoueur) {
        this.cartesDeLaDefausseJoueur = cartesDeLaDefausseJoueur;
    }

    public List<DefaussePropagation> getCartesDeLaDefaussePropagation() {
        return cartesDeLaDefaussePropagation;
    }

    public void setCartesDeLaDefaussePropagation(List<DefaussePropagation> cartesDeLaDefaussePropagation) {
        this.cartesDeLaDefaussePropagation = cartesDeLaDefaussePropagation;
    }

    public PisteDeclosion getPisteDeclosion() {
        return pisteDeclosion;
    }

    public void setPisteDeclosion(PisteDeclosion pisteDeclosion) {
        this.pisteDeclosion = pisteDeclosion;
    }

    public int[] getPisteDePropagation() {
        return pisteDePropagation;
    }

    public void setPisteDePropagation(int[] pisteDePropagation) {
        this.pisteDePropagation = pisteDePropagation;
    }

    public EmplacementMaladies getEmplacementMaladies() {
        return emplacementMaladies;
    }

    public void setEmplacementMaladies(EmplacementMaladies emplacementMaladies) {
        this.emplacementMaladies = emplacementMaladies;
    }
}
