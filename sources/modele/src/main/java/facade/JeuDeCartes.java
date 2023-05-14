package facade;

import lombok.Data;
import modele.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static facade.JeuProperties.*;

@Data
public class JeuDeCartes {
    private Plateau plateau;
    private final Map<String, byte[]> imageCarte;

    public JeuDeCartes(Plateau plateau) {
        this.plateau = plateau;
        this.imageCarte = image();
        this.plateau.setLesVilles(lesVilles());
        this.plateau.setCartesJoueur(chargerCartes());
        this.plateau.setCartesPropagation(chargerCartesPropagation());

    }

    public List<Carte> chargerCartes(){

        /* CARTES EVENEMENT,this.imageCarte.get("pont_aerien")*/
        List<Carte> cartes = new ArrayList<>(List.of(
                new Carte("Pont aérien", TypeCarte.EVENEMENT, "pont_aerien"),
                new Carte("Subvention publique", TypeCarte.EVENEMENT, "subvention_publique"),
                new Carte("Prévision", TypeCarte.EVENEMENT, "prevision"),
                new Carte("Par une nuit tranquille", TypeCarte.EVENEMENT, "par_une_nuit_tranquille"),
                new Carte("Population résiliente", TypeCarte.EVENEMENT, "population_resiliente")
        ));

        /* CARTES EPIDEMIE*/
        for(int i = 1 ; i <= 6 ; i++) {
            cartes.add(new Carte("Épidémie", TypeCarte.EPIDEMIE, "epidemie"));
        }


        for (Ville v: plateau.getLesVilles()){
            cartes.add(new Carte(v.getNomVille(),TypeCarte.VILLE, CARTE_VILLE_MAP.get(v.getNomVille())));
        }
        return cartes;
    }

    private List<Carte> chargerCartesPropagation(){
        List<Carte> cartes = new ArrayList<>();
        for (Ville v: plateau.getLesVilles()){
            cartes.add(new Carte(v.getNomVille(),TypeCarte.PROPAGATION, CARTE_VILLE_MAP.get(v.getNomVille())));
        }
        return cartes;
    }
    public static List<Ville> lesVilles(){

        /*LES VILLES COLOREES EN ROUGE*/
        Ville bangkok = new Ville("Bangkok","Thaïlande",CouleursMaladie.ROUGE,71510000,3200);
        Ville hoChiMinhVille = new Ville("Hô-Chi-Minh-Ville","Viêt Nam",CouleursMaladie.ROUGE,8314000,9900);
        Ville hongKong = new Ville("Hong Kong","Chine",CouleursMaladie.ROUGE,71060000,25900);
        Ville jakarta = new Ville("Jakarta","Indonésie",CouleursMaladie.ROUGE,26063000,9400);
        Ville manille = new Ville("Manille","Philippines",CouleursMaladie.ROUGE,20767000,14400);
        Ville osaka = new Ville("Osaka","Japon",CouleursMaladie.ROUGE,2871000,13000);
        Ville pekin = new Ville("Pékin","Chine",CouleursMaladie.ROUGE,1731000,5000);
        Ville seoul = new Ville("Séoul","Corée Du Sud",CouleursMaladie.ROUGE,22547000,10400);
        Ville shanghai = new Ville("Shanghai","Chine",CouleursMaladie.ROUGE,13482000,2200);
        Ville sydney = new Ville("Sydney","Australie",CouleursMaladie.ROUGE,37850000,2100);
        Ville taipei = new Ville("Taipei","Taïwan",CouleursMaladie.ROUGE,83330000,7300);
        Ville tokyo = new Ville("Tokyo","Japon",CouleursMaladie.ROUGE,13189000,6030);


        /*LES VILLES COLOREES EN JAUNE*/
        Ville bogota = new Ville("Bogota","Colombie",CouleursMaladie.JAUNE,8702000,2000);
        Ville buenosAires = new Ville("Buenos Aires", "Argentine",CouleursMaladie.JAUNE, 13639000,5200);
        Ville johannesburg = new Ville("Johannesburg","Afrique Du Sud",CouleursMaladie.JAUNE,3888000,2400);
        Ville khartoum = new Ville("Khartoum", "Soudan",CouleursMaladie.JAUNE, 4887000,4500);
        Ville kinshasa = new Ville("Kinshasa","Congo",CouleursMaladie.JAUNE,9046000,15000);
        Ville lagos = new Ville("Lagos","Nigéria",CouleursMaladie.JAUNE,547000, 12700);
        Ville lima = new Ville("Lima","Pérou",CouleursMaladie.JAUNE,912000,14100);
        Ville losAngeles = new Ville("Los Angeles", "Etats Unis",CouleursMaladie.JAUNE,14900000,2400);
        Ville mexico = new Ville("Mexico","Mexique",CouleursMaladie.JAUNE,19463000,9500);
        Ville miami = new Ville("Miami","États-Unis",CouleursMaladie.JAUNE,5582000,1700);
        Ville santiago = new Ville("Santiago","Chili",CouleursMaladie.JAUNE,6015000,6500);
        Ville saoPaulo = new Ville("São Paulo", "Brésil",CouleursMaladie.JAUNE,20186000,6400);


        /*LES VILLES COLOREES EN NOIR*/
        Ville alger = new Ville("Alger","Algérie",CouleursMaladie.NOIR,2946000,6500);
        Ville bagdad = new Ville("Bagdad","Irak",CouleursMaladie.NOIR,6204000,10400);
        Ville calcutta = new Ville("Calcutta","Inde",CouleursMaladie.NOIR,14374000,1900);
        Ville chennai = new Ville("Chennai","Algérie",CouleursMaladie.NOIR,8865000,14600);
        Ville delhi = new Ville("Delhi","Inde",CouleursMaladie.NOIR,22242000,1500);
        Ville istanbul = new Ville("Istanbul","Turquie",CouleursMaladie.NOIR,13576000,9700);
        Ville karachi = new Ville("Karachi","Pakistan",CouleursMaladie.NOIR,28710000,25800);
        Ville leCaire = new Ville("Le Caire","Égypte",CouleursMaladie.NOIR,14780000,8900);
        Ville moscou = new Ville("Moscou","Russie",CouleursMaladie.NOIR,15512000,3500);
        Ville mumbai = new Ville("Mumbai","Inde",CouleursMaladie.NOIR,16910000,38900);
        Ville riyad = new Ville("Riyad","Arabie Saoudite ",CouleursMaladie.NOIR,5037000,3400);
        Ville teheran = new Ville("Téhéran","Iran ",CouleursMaladie.NOIR,7419000,9500);


        /*LES VILLES COLOREES EN BLEU*/
        Ville atlanta = new Ville("Atlanta", "États-Unis",CouleursMaladie.BLEU,475000,700);
        Ville chicago = new Ville("Chicago", "États-Unis",CouleursMaladie.BLEU,475000,700);
        Ville essen = new Ville("Essen", "Allemagne",CouleursMaladie.BLEU,475000,700);
        Ville londres = new Ville("Londres", "Royaume-Uni",CouleursMaladie.BLEU,475000,700);
        Ville madrid = new Ville("Madrid", "Espagne",CouleursMaladie.BLEU,475000,700);
        Ville milan = new Ville("Milan", "Italie",CouleursMaladie.BLEU,475000,700);
        Ville montreal = new Ville("Montreal", "Canada",CouleursMaladie.BLEU,475000,700);
        Ville paris = new Ville("Paris", "France",CouleursMaladie.BLEU,475000,700);
        Ville saintPetersbourg = new Ville("Saint-Pétersbourg", "Russie",CouleursMaladie.BLEU,475000,700);
        Ville sanFrancisco = new Ville("San Francisco", "États-Unis",CouleursMaladie.BLEU,475000,700);
        Ville newYork = new Ville("New York", "Etats-Unis",CouleursMaladie.BLEU,475000,700);
        Ville washington = new Ville("Washington", "Etats-Unis",CouleursMaladie.BLEU,475000,700);

        /*VILLES LIEES*/
        //Bleu
        sanFrancisco.ajouterVillesLiees(List.of(chicago,losAngeles,manille,tokyo));
        chicago.ajouterVillesLiees(List.of(sanFrancisco,losAngeles,mexico,atlanta,montreal));
        atlanta.ajouterVillesLiees(List.of(chicago,miami,washington));
        montreal.ajouterVillesLiees(List.of(chicago,washington,newYork));
        washington.ajouterVillesLiees(List.of(atlanta,montreal,newYork,miami));
        newYork.ajouterVillesLiees(List.of(montreal,washington,londres,madrid));
        londres.ajouterVillesLiees(List.of(newYork,madrid,paris,essen));
        madrid.ajouterVillesLiees(List.of(newYork,saoPaulo,londres,paris,alger));
        paris.ajouterVillesLiees(List.of(madrid,londres,essen,milan,alger));
        essen.ajouterVillesLiees(List.of(londres,paris,milan,saintPetersbourg));
        milan.ajouterVillesLiees(List.of(paris,essen,istanbul));
        saintPetersbourg.ajouterVillesLiees(List.of(essen,istanbul,moscou));
        //Jaune
        losAngeles.ajouterVillesLiees(List.of(sanFrancisco,chicago,mexico,sydney));
        mexico.ajouterVillesLiees(List.of(losAngeles,chicago,miami,bogota));
        miami.ajouterVillesLiees(List.of(mexico,atlanta,washington,bogota));
        bogota.ajouterVillesLiees(List.of(mexico,miami,lima,saoPaulo,buenosAires));
        lima.ajouterVillesLiees(List.of(mexico,bogota,santiago));
        santiago.ajouterVillesLiees(List.of(lima));
        buenosAires.ajouterVillesLiees(List.of(bogota,saoPaulo));
        saoPaulo.ajouterVillesLiees(List.of(buenosAires,bogota,madrid,lagos));
        lagos.ajouterVillesLiees(List.of(saoPaulo,kinshasa,khartoum));
        khartoum.ajouterVillesLiees(List.of(lagos,leCaire));
        kinshasa.ajouterVillesLiees(List.of(lagos,khartoum,johannesburg));
        johannesburg.ajouterVillesLiees(List.of(kinshasa,khartoum));
        //Noir
        alger.ajouterVillesLiees(List.of(madrid,paris,istanbul,leCaire));
        istanbul.ajouterVillesLiees(List.of(alger,paris,milan,saintPetersbourg,moscou,bagdad,leCaire));
        leCaire.ajouterVillesLiees(List.of(alger,istanbul,bagdad,riyad));
        bagdad.ajouterVillesLiees(List.of(leCaire,istanbul,moscou,teheran,karachi,riyad));
        moscou.ajouterVillesLiees(List.of(saintPetersbourg,istanbul,teheran));
        teheran.ajouterVillesLiees(List.of(moscou,bagdad,karachi,delhi));
        riyad.ajouterVillesLiees(List.of(leCaire,bagdad,karachi));
        karachi.ajouterVillesLiees(List.of(riyad,bagdad,teheran,delhi,mumbai));
        mumbai.ajouterVillesLiees(List.of(karachi,delhi,chennai));
        delhi.ajouterVillesLiees(List.of(teheran,karachi,mumbai,chennai,calcutta));
        chennai.ajouterVillesLiees(List.of(mumbai,delhi,calcutta,bangkok,jakarta));
        calcutta.ajouterVillesLiees(List.of(delhi,chennai,bangkok,hongKong));
        //Rouge
        bangkok.ajouterVillesLiees(List.of(chennai,calcutta,hongKong,hoChiMinhVille,jakarta));
        jakarta.ajouterVillesLiees(List.of(chennai,bangkok,hoChiMinhVille,sydney));
        hoChiMinhVille.ajouterVillesLiees(List.of(jakarta,bangkok,manille,hongKong));
        hongKong.ajouterVillesLiees(List.of(calcutta,shanghai,taipei,manille,hoChiMinhVille,bangkok));
        manille.ajouterVillesLiees(List.of(taipei,hongKong,hoChiMinhVille,sydney,sanFrancisco));
        sydney.ajouterVillesLiees(List.of(jakarta,manille,losAngeles));
        taipei.ajouterVillesLiees(List.of(manille,hongKong,shanghai,osaka));
        osaka.ajouterVillesLiees(List.of(taipei,tokyo));
        shanghai.ajouterVillesLiees(List.of(pekin,seoul,tokyo,taipei,hongKong));
        pekin.ajouterVillesLiees(List.of(shanghai,seoul));
        seoul.ajouterVillesLiees(List.of(pekin,shanghai,tokyo));
        tokyo.ajouterVillesLiees(List.of(seoul,shanghai,osaka,sanFrancisco));

        return new ArrayList<>(
                List.of(//BLEU
                        sanFrancisco,chicago, atlanta, montreal, washington, newYork,
                        londres, madrid, paris, essen, milan, saintPetersbourg,
                        //Jaune
                        losAngeles, mexico, miami, bogota, lima, santiago, buenosAires,
                        saoPaulo, lagos, khartoum, kinshasa, johannesburg,
                        //Noir
                        alger, istanbul, leCaire, bagdad, moscou, teheran, riyad, karachi,
                        mumbai, delhi, chennai, calcutta,
                        //Rouge
                        bangkok, jakarta, hoChiMinhVille, hongKong, manille, sydney,
                        taipei, osaka, shanghai, pekin, seoul, tokyo
                )
        );
    }

    public static Map<String,byte[]> image(){
        Map<String,byte[]> lesImagesCartes = new HashMap<>();
        String imagePath = ROOT_PATH + CARTES;
        File files = new File(imagePath);
        for (File file : Objects.requireNonNull(files.listFiles())) {
            if(file.isFile() && file.getName().endsWith(EXTENSION)) {
                try {
                    lesImagesCartes.put(
                            file.getName().replace(EXTENSION,NONE),
                            Files.readAllBytes(file.toPath())
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return lesImagesCartes;
    }
}
