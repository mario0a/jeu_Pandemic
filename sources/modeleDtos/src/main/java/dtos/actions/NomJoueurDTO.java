package dtos.actions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NomJoueurDTO {
    private String idPartie;
    private String nomJoueur;
}
