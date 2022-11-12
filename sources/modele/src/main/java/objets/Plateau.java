package objets;

import cartes.CartePropagation;
import cartes.CartesJoueurs;
import cartes.DefausseJoueur;
import cartes.DefaussePropagation;
import eclosions.PisteDeclosion;

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

    public Plateau(List<Ville> lesVilles, List<CartesJoueurs> cartesJoueurs, List<CartePropagation> cartesPropagation, List<DefausseJoueur> cartesDeLaDefausseJoueur, List<DefaussePropagation> cartesDeLaDefaussePropagation, int[] pisteDeclosion, int[] pisteDePropagation) {
        LesVilles = new ArrayList<>();
        this.cartesJoueurs = new ArrayList<>();
        this.cartesPropagation = new ArrayList<>();
        this.cartesDeLaDefausseJoueur = new ArrayList<>();
        this.cartesDeLaDefaussePropagation = new ArrayList<>();
        this.pisteDeclosion = this.pisteDeclosion;
        //this.pisteDeclosion = new int  [8];
        this.pisteDePropagation = pisteDePropagation;
    }
}
