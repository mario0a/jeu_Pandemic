package modele;

import exceptions.ActionNotAutorizedException;
import exceptions.PartiePleineException;
import facade.JeuDeCartes;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Data
public class Partie{
    @BsonId
    private ObjectId id;
    private EtatPartie etatPartie = EtatPartie.DEBUT;
    private final LocalDateTime dateCreation = LocalDateTime.now();
    private boolean initialisation = false;
    private Plateau plateau = new Plateau();
    private List<Carte> carteDefausse = new ArrayList<>();
    private List<Partie1Joueur> partieJoueur = new ArrayList<>();
    private TourJoueur tourJoueur;

    public Partie(ObjectId id, Partie1Joueur partie1Joueur) {
        this.id = id;
        this.partieJoueur.add(partie1Joueur);
    }

    public Partie(ObjectId id, EtatPartie etatPartie, Plateau plateau, List<Carte> carteDefausse, List<Partie1Joueur> partieJoueur) {
        this.id = id;
        this.etatPartie = etatPartie;
        this.plateau=plateau;
        this.carteDefausse = carteDefausse;
        this.partieJoueur = partieJoueur;
    }



    @Override
    public String toString() {
        return "Partie{" +
                "id=" + id +
                ", etatPartie=" + etatPartie +
                ", plateau=" + plateau +
                ", dateCreation=" + dateCreation +
                ", carteDefausse=" + carteDefausse +
                ", partieJoueur=" + partieJoueur.toString() +
                '}';
    }






    public Partie setPlateau(Plateau plateau) {
        this.plateau = plateau;
        return this;
    }




    public Partie setCarteDefausse(List<Carte> carteDefausse) {
        this.carteDefausse = carteDefausse;
        return this;
    }

    public Partie setPartieJoueur(List<Partie1Joueur> partieJoueur) {
        this.partieJoueur = partieJoueur;
        return this;
    }

    public void  ajouterPartie1Joueur(Partie1Joueur partie1Joueur) throws PartiePleineException{
        if (this.partieJoueur.size()<4){
            this.partieJoueur.add(partie1Joueur);
        }else {
            throw  new PartiePleineException();
        }
    }

    public Map<String,byte[]> partieInitialisee() throws ActionNotAutorizedException {
        if(this.getEtatPartie() != EtatPartie.DEBUT)throw new ActionNotAutorizedException("");
        if(this.partieJoueur.size() < 2) throw new ActionNotAutorizedException("");
        if(this.getTourJoueur() == null) {
            this.attribuerRoleAuxJoueurs();
            JeuDeCartes jeuDeCartes = new JeuDeCartes(this.plateau);
            tourJoueur = new TourJoueur(this.partieJoueur);
            distributionInitialeEpidemie();
            distributionInitialeJoueur();
            this.setInitialisation(true);
            return jeuDeCartes.getImageCarte();
        }
        return Collections.emptyMap();
    }

    public void ajouterJoueur(String nomJoueur) {
        this.partieJoueur.add(new Partie1Joueur(nomJoueur));
    }

    public Partie1Joueur getPartieJoueurByNomJoueur(String nomJoueur) {
        return this.partieJoueur.stream().filter
                        (partieJoueur -> partieJoueur.getNom().equals(nomJoueur))
                .toList().get(0);
    }

    public Carte getCarteByNomCarte(String nomCarte){
        return this.getCarteDefausse().stream().filter(carte -> carte.getNomCarte().equals(nomCarte)).toList().get(0);
    }

    private void attribuerRoleAuxJoueurs(){
        List<TypeRole> roles=new ArrayList<>();
        // Ajouter les autres rôles nécessaires à la liste "roles"
        roles.add(TypeRole.PLANIFICATEUR_DURGENCE);
        roles.add(TypeRole.PLANIFICATEUR_DURGENCE);
        roles.add(TypeRole.SPECIALISTE_EN_MISE_EN_QUARANTAINE);
        roles.add(TypeRole.SCIENTIFIQUE);
        roles.add(TypeRole.CHERCHEUSE);
        roles.add(TypeRole.REPARTITEUR);
        roles.add(TypeRole.MEDECIN);
        roles.add(TypeRole.EXPERT_AUX_OPERATIONS);
        // Mélanger la liste des rôles de manière aléatoire
        Collections.shuffle(roles);

        // Parcourir la liste des joueurs et attribuer un rôle aléatoire à chaque joueur
        for (Partie1Joueur partie1Joueur : this.getPartieJoueur()) {
            int indexRole = (int) (Math.random() * roles.size());
            partie1Joueur.setTypeRole(roles.get(indexRole));
            roles.remove(indexRole);
        }
    }

    private void distributionInitialeEpidemie(){
        int cube = 3;
        Collections.shuffle(this.plateau.getCarteEpidemie());
        for(int i = 0 ; i <= 9 ; i++) {
            if(i % 3 == 0) cube--;
            Carte carte = this.plateau.getCartesPropagation().remove(0);
            this.plateau.getDefausse_carteDePropagation().add(carte);
            Ville ville = this.plateau.getVilleByNom(carte.getNomCarte());
            CouleursMaladie couleursMaladie = ville.getMaladie();
            ville.ajouterCube(couleursMaladie, cube);
        }
    }

    private void distributionInitialeJoueur(){
        int nbCartesParJoueur = switch (this.partieJoueur.size()) {
            case 2 -> 4;
            case 3 -> 3;
            case 4 -> 2;
            default -> 0; // En cas de nombre de joueurs invalide, ne rien faire
        };
        if (nbCartesParJoueur > 0) {
            Collections.shuffle(this.plateau.getCartesJoueur());
            for (int i = 0; i < nbCartesParJoueur; i++) {
                for (Partie1Joueur partieJoueur : this.partieJoueur) {
                    partieJoueur.getCartesEnMain().add(this.plateau.getCartesJoueur().remove(0));
                }
            }
        }
    }
}
