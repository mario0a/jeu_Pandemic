package modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Carte {

    private String nomCarte;
    private TypeCarte typeCarte;
    private String description;
    private String reference;

    public Carte(String nomCarte, TypeCarte typeCarte, String reference) {
        this.nomCarte = nomCarte;
        this.typeCarte = typeCarte;
        this.reference = reference;
    }
}
