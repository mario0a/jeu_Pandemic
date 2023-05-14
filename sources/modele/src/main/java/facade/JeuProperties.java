package facade;

import java.util.HashMap;

public class JeuProperties {
    static final String ROOT_PATH = "sources/modele/src/main/resources/";
    static final String CITY = "city/";
    static final String CARTES = "carte/";
    static final String EXTENSION = ".png";
    static final String NONE = "";

    static final HashMap<String,String> CARTE_VILLE_MAP = mapping();

    private static HashMap<String, String> mapping(){
        HashMap<String,String> mapping = new HashMap<>();
        mapping.put("Bangkok", "bangkok");
        mapping.put("Chennai", "chennai");
        mapping.put("Chicago", "chicago");
        mapping.put("Delhi", "delhi");
        mapping.put("Istanbul", "istanbul");
        mapping.put("Karachi", "karachi");
        mapping.put("Lagos", "lagos");
        mapping.put("Los Angeles", "los_angeles");
        mapping.put("Manille", "manille");
        mapping.put("Mexico", "mexico");
        mapping.put("Moscou", "moscou");
        mapping.put("Mumbai", "mumbai");
        mapping.put("New York", "new_york");
        mapping.put("Osaka", "osaka");
        mapping.put("Paris", "paris");
        mapping.put("Pékin", "pekin");
        mapping.put("São Paulo", "sao_paulo");
        mapping.put("Séoul", "seoul");
        mapping.put("Shanghai", "shanghai");
        mapping.put("Sydney", "sydney");
        mapping.put("Taipei", "taipei");
        mapping.put("Tokyo", "tokyo");
        mapping.put("Hô-Chi-Minh-Ville","ho_chi_minh_ville");
        mapping.put("Jakarta", "jakarta");
        mapping.put("Kinshasa", "kinshasa");
        mapping.put("Bogota", "bogota");
        mapping.put("Buenos Aires", "buenos_aires");
        mapping.put("Johannesburg", "johannesburg");
        mapping.put("Khartoum", "khartoum");
        mapping.put("Lima", "lima");
        mapping.put("Miami", "miami");
        mapping.put("Santiago", "santiago");
        mapping.put("Atlanta","atlanta");
        mapping.put("Essen","essen");
        mapping.put("Londres","londres");
        mapping.put("Madrid","madrid");
        mapping.put ("Milan","milan");
        mapping.put("Montreal","montreal");
        mapping.put("Saint-Pétersbourg","saint-petersbourg");
        mapping.put("San Francisco","san_francisco");
        mapping.put("Washington","washington");
        mapping.put("Alger","alger");
        mapping.put("Hong Kong","hong_kong");
        mapping.put("Bagdad","bagdad");
        mapping.put("Calcutta","calcutta");
        mapping.put("Le Caire","le_caire");
        mapping.put("Riyad","riyad");
        mapping.put("Téhéran","teheran");
        return mapping;
    }
}
