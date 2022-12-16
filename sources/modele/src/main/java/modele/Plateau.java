package modele;

import LesActions.Actions;
import modele.interfaces.ICartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau {
    private final List<Integer> PistevitesseDePropagation =List.of(2,2,2,3,3,4,4);
    private List<ICartes> cartesJoueur;
    private List<ICartes>carte_epidemie;
    private List<Joueur> lesJoueurs;
    private int nombreEclosion;
    private int vitesseDePropagation;
    private List<ICartes> cartesPropagation;
    private List<ICartes> defausse_cartesJoueur;
    private List<ICartes> defausse_carteDePropagation;
    private List<Ville> lesVilles;
    private List<Ville> villes_ontEclosion;
    private List<Actions> stationDeRecherche;

    public Plateau() {
        this.nombreEclosion=0;
        this.vitesseDePropagation=0;
        this.cartesJoueur=new ArrayList<>();
        this.defausse_carteDePropagation = new ArrayList<>();
        this.lesVilles = JeuDeCartes.lesVilles();
        this.carte_epidemie = new ArrayList<>();
        this.lesJoueurs =new ArrayList<>();
        this.cartesPropagation=new ArrayList<>();
        this.defausse_cartesJoueur= new ArrayList<>();

        for (Ville v: lesVilles){
            cartesPropagation.add(new CarteEpidemie("propagation", TypeCarte.PROPAGATION));
        }
        Collections.shuffle(cartesPropagation);
    }

    public List<Joueur> getLesJoueurs() {
        return lesJoueurs;
    }

    public void setLesJoueurs(List<Joueur> lesJoueurs) {
        this.lesJoueurs = lesJoueurs;
    }

    public int getNombreEclosion() {
        return nombreEclosion;
    }

    public void setNombreEclosion(int nombreEclosion) {
        this.nombreEclosion = nombreEclosion;
    }

    public int getVitesseDePropagation() {
        return vitesseDePropagation;
    }

    public void setVitesseDePropagation(int vitesseDePropagation) {
        this.vitesseDePropagation = vitesseDePropagation;
    }

    public List<ICartes> getCartesJoueur() {
        return cartesJoueur;
    }

    public void setCartesJoueur(List<ICartes> cartesJoueur) {
        this.cartesJoueur = cartesJoueur;
    }

    public List<ICartes> getCartesPropagation() {
        return cartesPropagation;
    }

    public void setCartesPropagation(List<ICartes> cartesPropagation) {
        this.cartesPropagation = cartesPropagation;
    }

    public List<ICartes> getDefausse_cartesJoueur() {
        return defausse_cartesJoueur;
    }

    public void setDefausse_cartesJoueur(List<ICartes> defausse_cartesJoueur) {
        this.defausse_cartesJoueur = defausse_cartesJoueur;
    }

    public List<ICartes> getDefausse_carteDePropagation() {
        return defausse_carteDePropagation;
    }

    public void setDefausse_carteDePropagation(List<ICartes> defausse_carteDePropagation) {
        this.defausse_carteDePropagation = defausse_carteDePropagation;
    }

    public List<Actions> getStationDeRecherche(Ville ville) {
        return this.stationDeRecherche;
    }

    public List<Ville> getLesVilles() {
        return lesVilles;
    }

    public void setLesVilles(List<Ville> lesVilles) {
        this.lesVilles = lesVilles;
    }

    public List<Integer> getPistevitesseDePropagation() {
        return PistevitesseDePropagation;
    }
    public void rejoindrePartie(Joueur joueur){this.lesJoueurs.add(joueur);}
    public void abandonnerPartie(Joueur joueur){this.lesJoueurs.remove(joueur);}

    public void ajouterCartesJoueur(ICartes carte){this.cartesJoueur.add(carte);}
    public void ajouterCartePropagation(ICartes carte){this.cartesPropagation.add(carte);}
    public void ajouterCarteALaDefausse(ICartes carte){this.defausse_cartesJoueur.add(carte);}
    public void ajouterCarteALaDefaussePropagation(ICartes carte){this.defausse_carteDePropagation.add(carte);}
    public void diffusionEpidemie(){
        villes_ontEclosion.addAll(lesVilles);
        int nombreCube=0;
        getPistevitesseDePropagation();
        CarteVille cartePropagation = (CarteVille) cartesPropagation.remove(cartesPropagation.size()-1);
        Ville villeInfectee = cartePropagation.getVille();
        CouleursMaladie maladie = villeInfectee.getMaladie();
    }
}
