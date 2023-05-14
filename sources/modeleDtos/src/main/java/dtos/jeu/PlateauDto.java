package dtos.jeu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlateauDto {
    private String idPartie;
    private String nomJoueur;
    private List<String> lesJoueurs =  new ArrayList<>();
    private String etatPartie;
    private List<String> lesVilles=new ArrayList<>();
    private List<String> carteEpidemie =new ArrayList<>();
    private List<String> cartesPropagation=new ArrayList<>();
    private List<String> cartesJoueur=new ArrayList<>();
    private List<String> defausse_cartesJoueur=new ArrayList<>();
    private List<String> defausse_carteDePropagation=new ArrayList<>();
    private List<String> cartes_en_main = new ArrayList<>();
}
