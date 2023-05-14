package dtos.jeu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PlateauInitialDto {
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
    private Map<String,byte[]> imageData = new HashMap<>();

    public PlateauInitialDto(PlateauDto plateauDto){
        this.idPartie = plateauDto.getIdPartie();
        this.nomJoueur = plateauDto.getNomJoueur();
        this.lesJoueurs = plateauDto.getLesJoueurs();
        this.etatPartie = plateauDto.getEtatPartie();
        this.lesVilles = plateauDto.getLesVilles();
        this.carteEpidemie = plateauDto.getCarteEpidemie();
        this.cartesPropagation = plateauDto.getCartesPropagation();
        this.defausse_cartesJoueur = plateauDto.getDefausse_cartesJoueur();
        this.defausse_carteDePropagation = plateauDto.getDefausse_carteDePropagation();
        this.cartes_en_main = plateauDto.getCartes_en_main();
    }
}
