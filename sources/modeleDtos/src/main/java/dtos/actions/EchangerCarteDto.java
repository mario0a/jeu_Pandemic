package dtos.actions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EchangerCarteDto {
    private String idPartie;
    private String nomJoueur;
    private String receveurNomJoueur;
    private String nomCarte;
}
