package modele;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Ville {
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "nomVille")
    private String nomVille;
    private String nomPays;
    private int nombreDeCube = 0;
    private Map<String, Integer> cube = new HashMap<>();
    private CouleursMaladie maladie;
    private int nombreHabitant;
    private Set<Joueur> joueurs = new HashSet<>();
    private double superficie;
    // @JsonIdentityReference(alwaysAsId = true)
    private List<String> villesLiees = new ArrayList<>();
    private Boolean aUnCentreDeRecherche = false;

    public Ville(String nomVille) {
        this.nomVille = nomVille;
    }

    public Ville(String nomVille, String nomPays,CouleursMaladie couleursMaladie, int nombreHabitant, double superficie) {
        this.nomVille = nomVille;
        this.nomPays = nomPays;
        this.maladie = couleursMaladie;
        this.nombreHabitant = nombreHabitant;
        this.superficie = superficie;
    }

    public void ajouterVillesLiees(List<Ville> lesVoisines) {
        this.villesLiees = lesVoisines.stream().map(Ville::getNomVille).toList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ville)
            return this.nomVille.equals(((Ville) obj).getNomVille());
        else
            return false;
    }

    public void ajouterCube(CouleursMaladie couleursMaladie, int nombreDeCube){
        String couleurMaladieStr = couleursMaladie.toString();
        cube.putIfAbsent(couleurMaladieStr, 0);
        cube.merge(couleurMaladieStr, nombreDeCube, Integer::sum);
    }

}
