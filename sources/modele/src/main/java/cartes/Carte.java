package cartes;

import modele.CarteEpidemie;
import modele.CarteVille;
import modele.CouleursMaladie;
import modele.TypeCarte;

import java.io.Serializable;


public class Carte implements Serializable {
    public String getNomCarte() {
        return nomCarte;
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    public String nomCarte;
    public CarteVille carteVille;
    public CarteEpidemie carteEpidemie;
    public TypeCarte typeCarte;
    public CouleursMaladie couleursMaladie;

    public Carte(String nomCarte, TypeCarte typeCarte, CouleursMaladie couleursMaladie) {
        this.nomCarte = nomCarte;
        this.typeCarte = typeCarte;
        this.couleursMaladie = couleursMaladie;
    }

    public Carte(String nomCarte, TypeCarte typeCarte) {
        this.nomCarte = nomCarte;
        this.typeCarte = typeCarte;
    }

    public CarteVille getCarteVille() {
        return carteVille;
    }

    public void setCarteVille(CarteVille carteVille) {
        this.carteVille = carteVille;
    }

    public CarteEpidemie getCarteEpidemie() {
        return carteEpidemie;
    }

    public void setCarteEpidemie(CarteEpidemie carteEpidemie) {
        this.carteEpidemie = carteEpidemie;
    }

    public TypeCarte getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(TypeCarte typeCarte) {
        this.typeCarte = typeCarte;
    }

    public CouleursMaladie getCouleursMaladie() {
        return couleursMaladie;
    }

    public void setCouleursMaladie(CouleursMaladie couleursMaladie) {
        this.couleursMaladie = couleursMaladie;
    }
}
