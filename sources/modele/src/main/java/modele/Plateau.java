package modele;

import facade.IFacadePandemicOnline;
import facade.JeuDeCartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@NoArgsConstructor
public class Plateau {
    private final List<Integer> PistevitesseDePropagation =List.of(2,2,2,3,3,4,4);
    private List<Carte> cartesJoueur=new ArrayList<>();
    private List<Carte> carteEpidemie =new ArrayList<>();
    private List<Joueur> lesJoueurs=new ArrayList<>();
    private int nombreEclosion = 0;
    private int vitesseDePropagation = 2;
    private List<Carte> cartesPropagation=new ArrayList<>();
    private List<Carte> defausse_cartesJoueur=new ArrayList<>();
    private List<Carte> defausse_carteDePropagation=new ArrayList<>();
    private List<Ville> lesVilles=new ArrayList<>();
    private List<Ville> villes_ontEclosion=new ArrayList<>();
    private List<Ville> stationDeRecherche=new ArrayList<>();//y'a un problème là
    private List<TypeRemede> lesRemedesActif = new ArrayList<>();
    private List<Ville> lesStationsDeRecherche=new ArrayList<>();
    private EtatEpidemie etatEpidemie;
    private Boolean parUneNuitTranquille=false; // tant que le boolean est à faux on peut faire la propagation sinon non


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

    public List<Carte> getCartesJoueur() {
        return cartesJoueur;
    }

    public void setCartesJoueur(List<Carte> cartesJoueur) {
        this.cartesJoueur = cartesJoueur;
    }

    public List<Carte> getCartesPropagation() {
        return cartesPropagation;
    }

    public void setCartesPropagation(List<Carte> cartesPropagation) {
        this.cartesPropagation = cartesPropagation;
    }

    public List<Carte> getDefausse_cartesJoueur() {
        return defausse_cartesJoueur;
    }

    public Boolean getParUneNuitTranquille() {
        return parUneNuitTranquille;
    }

    public void setParUneNuitTranquille(Boolean parUneNuitTranquille) {
        this.parUneNuitTranquille = parUneNuitTranquille;
    }

    public void setDefausse_cartesJoueur(List<Carte> defausse_cartesJoueur) {
        this.defausse_cartesJoueur = defausse_cartesJoueur;
    }

    public List<Carte> getDefausse_carteDePropagation() {
        return defausse_carteDePropagation;
    }

    public void setDefausse_carteDePropagation(List<Carte> defausse_carteDePropagation) {
        this.defausse_carteDePropagation = defausse_carteDePropagation;
    }

    public List<Ville> getStationDeRecherche(Ville ville) {
        return this.stationDeRecherche;
    }

    public List<Ville> getLesVilles() {
        return lesVilles;
    }

    public EtatEpidemie getEtatEpidemie() {
        return etatEpidemie;
    }

    public void setEtatEpidemie(EtatEpidemie etatEpidemie) {
        this.etatEpidemie = etatEpidemie;
    }

    public void setLesVilles(List<Ville> lesVilles) {
        this.lesVilles = lesVilles;
    }

    public List<Integer> getPistevitesseDePropagation() {
        return PistevitesseDePropagation;
    }
    public List<Ville> getLesStationsDeRecherche() {
        return lesStationsDeRecherche;
    }

    public List<TypeRemede> getLesRemedesActif() {
        return lesRemedesActif;
    }
    public void rejoindrePartie(Joueur joueur){this.lesJoueurs.add(joueur);}
    public void abandonnerPartie(Joueur joueur){this.lesJoueurs.remove(joueur);}

    public void ajouterCartesJoueur(Carte carte){this.cartesJoueur.add(carte);}
    public void ajouterCartePropagation(Carte carte){this.cartesPropagation.add(carte);}
    public void ajouterCarteALaDefausse(Carte carte){this.defausse_cartesJoueur.add(carte);}
    public void ajouterCarteALaDefaussePropagation(Carte carte){this.defausse_carteDePropagation.add(carte);}


    public void diffusionEpidemie(){
        // Si medeçin sur ville et que la maladie a un remède : bloquer les nouveaux cubes d'une maladie guérie
        villes_ontEclosion.addAll(lesVilles);
        int nombreCube=0;
        getPistevitesseDePropagation();
        Carte cartePropagation = (Carte) cartesPropagation.remove(cartesPropagation.size()-1);
        Ville villeInfectee = this.getVilleByNom(cartePropagation.getNomCarte());
        CouleursMaladie maladie = villeInfectee.getMaladie();
    }

    public List<Carte> getCarteEpidemie() {
        return carteEpidemie;
    }

    public Plateau setCarteEpidemie(List<Carte> carteEpidemie) {
        this.carteEpidemie = carteEpidemie;
        return this;
    }

    public List<Ville> getVilles_ontEclosion() {
        return villes_ontEclosion;
    }

    public Plateau setVilles_ontEclosion(List<Ville> villes_ontEclosion) {
        this.villes_ontEclosion = villes_ontEclosion;
        return this;
    }

    public List<Ville> getStationDeRecherche() {
        return stationDeRecherche;
    }

    public Plateau setStationDeRecherche(List<Ville> stationDeRecherche) {
        this.stationDeRecherche = stationDeRecherche;
        return this;
    }

    public Plateau setLesRemedesActif(List<TypeRemede> lesRemedesActif) {
        this.lesRemedesActif = lesRemedesActif;
        return this;
    }

    public Plateau setLesStationsDeRecherche(List<Ville> lesStationsDeRecherche) {
        this.lesStationsDeRecherche = lesStationsDeRecherche;
        return this;
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "PistevitesseDePropagation=" + PistevitesseDePropagation +
                //       ", cartesJoueur=" + cartesJoueur +
                ", carte_epidemie=" + carteEpidemie +
                ", lesJoueurs=" + lesJoueurs +
                ", nombreEclosion=" + nombreEclosion +
                ", vitesseDePropagation=" + vitesseDePropagation +
                //       ", cartesPropagation=" + cartesPropagation +
                ", defausse_cartesJoueur=" + defausse_cartesJoueur +
                ", defausse_carteDePropagation=" + defausse_carteDePropagation +
                //       ", lesVilles=" + lesVilles +
                ", villes_ontEclosion=" + villes_ontEclosion +
                ", stationDeRecherche=" + stationDeRecherche +
                ", lesRemedesActif=" + lesRemedesActif +
                ", lesStationsDeRecherche=" + lesStationsDeRecherche +
                ", etatEpidemie=" + etatEpidemie +
                ", parUneNuitTranquille=" + parUneNuitTranquille +
                '}';
    }

    public Ville getVilleByNom(String nomVille){
        for(Ville ville : this.lesVilles){
            if(ville.getNomVille().equals(nomVille)) {
                return ville;
            }
        }
        return null;
    }
}
