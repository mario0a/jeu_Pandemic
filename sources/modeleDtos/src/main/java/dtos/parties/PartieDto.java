package dtos.parties;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class PartieDto {
    private String idPartie;
    private String nomJoueur;
    private List<String> lesJoueurs =  new ArrayList<>();
    private String etatPartie;
}