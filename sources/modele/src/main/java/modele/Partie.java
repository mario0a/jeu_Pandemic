package modele;

import exceptions.ActionNotAutorizedException;
import exceptions.PartiePleineException;
import facade.JeuDeCartes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Partie {
    private Long id;
    private EtatPartie etatPartie = EtatPartie.DEBUT;
    private final LocalDateTime dateCreation = LocalDateTime.now();
    private Plateau plateau = new Plateau();
    private List<Carte> carteDefausse = new ArrayList<>();
    private List<Partie1Joueur> partieJoueur = new ArrayList<>();

    public Partie() {}

    public Partie(Long id, Partie1Joueur partie1Joueur) {
        this.id = id;
        this.partieJoueur.add(partie1Joueur);
    }

    public Partie(Long id, EtatPartie etatPartie, Plateau plateau, List<Carte> carteDefausse, List<Partie1Joueur> partieJoueur) {
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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id;}

    public EtatPartie getEtatPartie() { return etatPartie;}

    public void setEtatPartie(EtatPartie etatPartie) {this.etatPartie = etatPartie;}

    public Plateau getPlateau() {
        return plateau;
    }

    public Partie setPlateau(Plateau plateau) {
        this.plateau = plateau;
        return this;
    }

    public LocalDateTime getDateCreation() { return dateCreation; }

    public List<Carte> getCarteDefausse() { return carteDefausse;}

    //@JsonProperty("partieJoueur")
    public List<Partie1Joueur> getPartieJoueur() {
        return partieJoueur;
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

    public boolean partieInitialisee() throws ActionNotAutorizedException {
        if(this.getEtatPartie()==EtatPartie.DEBUT){
            this.attribuerRoleAuxJoueurs();
            new JeuDeCartes(this.plateau);
            return switch (this.partieJoueur.size()){
                case 2,3,4 -> true;
                default -> false;
            };
        }else{
            throw new ActionNotAutorizedException();
        }
    }

    public void ajouterJoueur(String nomJoueur) {
        this.partieJoueur.add(new Partie1Joueur(nomJoueur));
    }


    public void ajouterUneCarteALaDefausse(Carte carte){
        this.carteDefausse.add(carte);
    }

    public Partie1Joueur getPartieJoueurByNomJoueur(String nomJoueur) {
        return this.partieJoueur.stream().filter
                        (partieJoueur -> partieJoueur.getNom().equals(nomJoueur))
                .collect(Collectors.toList()).get(0);
    }

    public void attribuerRoleAuxJoueurs(){
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


   /* public Joueur getJoueurByName(String nomJoueur){
        return this.partieJoueur.stream().filter(partie1Joueur -> partie1Joueur.getJoueur().equals(nomJoueur));
        //return this.partieJoueur.stream().filter(partie1Joueur ->partie1Joueur.getJoueur().equals(nomJoueur)).collect(Collectors.toList()).get(0).getJoueur();
    }*/
}
