package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Joueur {

    private String nomJoueur = "" ;
    private String mdp = "";

    private Collection<String> roles=new ArrayList<>();
    public Joueur( String nomJoueur, String mdp) {
        this.nomJoueur = nomJoueur;
        this.mdp = mdp;
        this.roles.add("JOUEUR");
    }
    public Joueur(){}

    public Joueur(String joueur1) {
        this.nomJoueur=joueur1;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public String getMdp() {return mdp;}

    public void setMdp(String mdp) {this.mdp = mdp;}

    public Collection<String> getRoles() {
        return roles;
    }

    public Joueur setRoles(Collection<String> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nomJoueur='" + nomJoueur + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }
}