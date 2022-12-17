package modele;

import exceptions.PartiePleineException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Partie {
    private String id;
    private EtatPartie etatPartie;
    private LocalDate dateCreation;
    private List<CarteVille> carteDefausse;
    private List<Partie1Joueur> partieJoueur;

    public Partie() {}

    public Partie(String id) {
        this.id = id;
        this.dateCreation = LocalDate.now();
        this.partieJoueur= new ArrayList<>();
        this.carteDefausse = new ArrayList<>();
        this.etatPartie = EtatPartie.DEBUT;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id;}

    public EtatPartie getEtatPartie() { return etatPartie;}

    public void setEtatPartie(EtatPartie etatPartie) {this.etatPartie = etatPartie;}

    public LocalDate getDateCreation() { return dateCreation; }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<CarteVille> getCarteDefausse() { return carteDefausse;}

    public void setCarteDefausse(List<CarteVille> carteDefausse) { this.carteDefausse = carteDefausse; }

    public List<Partie1Joueur> getPartieJoueur() {
        return partieJoueur;
    }

    public void setPartieJoueur(List<Partie1Joueur> partieJoueur) {
        this.partieJoueur = partieJoueur;
    }

    public void  ajouterPartie1Joueur(Partie1Joueur partie1Joueur) throws PartiePleineException{
        if (this.partieJoueur.size()<4){
            this.partieJoueur.add(partie1Joueur);
        }else {
            throw  new PartiePleineException();
        }
    }

}
