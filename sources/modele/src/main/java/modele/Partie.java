package modele;

import exceptions.PartiePleineException;
import modele.interfaces.ICartes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.configuration.CodecConfigurationException;

public class Partie {
    @BsonProperty("_id")
    private String id;
    private EtatPartie etatPartie;
    private LocalDate dateCreation;
    private List<ICartes> carteDefausse;
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

    public List<ICartes> getCarteDefausse() { return carteDefausse;}

    public void setCarteDefausse(List<ICartes> carteDefausse) { this.carteDefausse = carteDefausse; }

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

    @Override
    public String toString() {
        return "Partie{" +
                "id='" + id + '\'' +
                ", etatPartie=" + etatPartie +
                ", dateCreation=" + dateCreation +
                ", carteDefausse=" + carteDefausse +
                ", partieJoueur=" + partieJoueur +
                '}';
    }

    public Partie1Joueur getPartieJoueurByNomJoueur(String nomJoueur) {
        return this.partieJoueur.stream().filter(partieJoueur -> partieJoueur.getJoueur().equals(nomJoueur)).collect(Collectors.toList()).get(0);
    }

    public Joueur getJoueurByName(String nomJoueur){
        return this.partieJoueur.stream().filter(partie1Joueur ->partie1Joueur.getJoueur().getNomJoueur().equals(nomJoueur)).collect(Collectors.toList()).get(0).getJoueur();
    }
}
