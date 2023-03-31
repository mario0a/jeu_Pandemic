package modele;

import exceptions.PartiePleineException;
import modele.interfaces.ICartes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.configuration.CodecConfigurationException;

public class Partie {
    private Long id;
    private EtatPartie etatPartie=EtatPartie.DEBUT;
    private final LocalDateTime dateCreation=LocalDateTime.now();
    private List<ICartes> carteDefausse= new ArrayList<>();
    private List<Partie1Joueur> partieJoueur= new ArrayList<>();

    public Partie() {}

    public Partie(Long id, Partie1Joueur partie1Joueur) {
        this.id = id;
        this.partieJoueur.add(partie1Joueur);
    }

    public Partie(Long id, EtatPartie etatPartie, List<ICartes> carteDefausse, List<Partie1Joueur> partieJoueur) {
        this.id = id;
        this.etatPartie=etatPartie;
        this.carteDefausse = carteDefausse;
        this.partieJoueur= partieJoueur;
    }

    @Override
    public String toString() {
        return "Partie{" +
                "id=" + id +
                ", etatPartie=" + etatPartie +
                ", dateCreation=" + dateCreation +
                ", carteDefausse=" + carteDefausse +
                ", partieJoueur=" + partieJoueur +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(EtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public List<ICartes> getCarteDefausse() {
        return carteDefausse;
    }

    public void setCarteDefausse(List<ICartes> carteDefausse) {
        this.carteDefausse = carteDefausse;
    }

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

    public boolean partieInitialisee(){
        return switch (this.partieJoueur.size()){
            case 2,3,4 -> true;
            default -> false;
        };
    }

    public void ajouterUneCarteALaDefausse(ICartes carte){
        this.carteDefausse.add(carte);
    }

    public Partie1Joueur getPartieJoueurByNomJoueur(String nomJoueur) {
        return this.partieJoueur.stream().filter
                        (partieJoueur -> partieJoueur.getNom().equals(nomJoueur))
                .collect(Collectors.toList()).get(0);
    }


    /*public Joueur getJoueurByName(String nomJoueur){
        return this.partieJoueur.stream().filter(partie1Joueur ->partie1Joueur.getJoueur().getNomJoueur().equals(nomJoueur)).collect(Collectors.toList()).get(0).getJoueur();
    }*/
}
