package dtos.actions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TraiterMaladieDto {
    private String idPartie;
    private String nomJoueur;
    private String couleurMaladie;
}
