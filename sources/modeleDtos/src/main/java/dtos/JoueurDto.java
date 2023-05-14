package dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoueurDto {
    private String nomJoueur ;
    private String mdp ;

    public JoueurDto(String nomJoueur, String mdp) {
        this.nomJoueur = nomJoueur;
        this.mdp = mdp;
    }

    public JoueurDto(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

}
