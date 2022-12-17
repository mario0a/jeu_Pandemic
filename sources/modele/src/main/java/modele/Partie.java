package modele;

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
}
