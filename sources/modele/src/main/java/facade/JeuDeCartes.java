package facade;

import modele.*;
import modele.interfaces.ICartes;

import java.util.ArrayList;
import java.util.List;

public class JeuDeCartes {
    Plateau partie;
    public static final List<ICartes> cartesJoueur = chargerCartes();
    public static final List<ICartes> cartesPropagation = chargerCartesPropagation();
    public static List<ICartes> chargerCartes(){
        List<ICartes> cartes = new ArrayList<>();


        /* CARTES EVENEMENT*/
        cartes.add(new CarteEvenement("Pont aérien", TypeCarte.JOUEUR));
        cartes.add(new CarteEvenement("Subventions publique", TypeCarte.JOUEUR));
        cartes.add(new CarteEvenement("Prévisions", TypeCarte.JOUEUR));
        cartes.add(new CarteEvenement("Par une nuit tranquille", TypeCarte.JOUEUR));
        cartes.add(new CarteEvenement("Population résiliente", TypeCarte.JOUEUR));



        /* CARTES EPIDEMIE*/
        cartes.add(new CarteEpidemie("carte épidémie n°1", TypeCarte.JOUEUR));
        cartes.add(new CarteEpidemie("carte épidémie n°2", TypeCarte.JOUEUR));
        cartes.add(new CarteEpidemie("carte épidémie n°3", TypeCarte.JOUEUR));
        cartes.add(new CarteEpidemie("carte épidémie n°4", TypeCarte.JOUEUR));
        cartes.add(new CarteEpidemie("carte épidémie n°5", TypeCarte.JOUEUR));
        cartes.add(new CarteEpidemie("carte épidémie n°6", TypeCarte.JOUEUR));

        /* CARTES VILLES*/
        for (Ville v: lesVilles()){
            cartes.add(new CarteVille(v,TypeCarte.JOUEUR));
        }


return cartes;
    }

    private static List<ICartes> chargerCartesPropagation(){
        List<ICartes> cartes = new ArrayList<>();
        for (Ville v: lesVilles()){
            cartes.add(new CarteVille(v,TypeCarte.PROPAGATION));
        }
        return cartes;
    }
    public static List<Ville> lesVilles(){
        List<Ville> lesVilles = new ArrayList<>();

        /*LES VILLES COLOREES EN ROUGE*/
        Ville bangkok = new Ville("Bangkok","Thaïlande",0,CouleursMaladie.ROUGE,71510000,3200,false);
        Ville ho_chi_minh_ville = new Ville("Hô-Chi-Minh-Ville","Viêt Nam",0,CouleursMaladie.ROUGE,8314000,9900,false);
        Ville hong_kong = new Ville("Hong Kong","République Populaire De Chine",0,CouleursMaladie.ROUGE,71060000,25900,false);
        Ville jakarta = new Ville("Jakarta","Indonésie",0,CouleursMaladie.ROUGE,26063000,9400,false);
        Ville manille = new Ville("Manille","Philippines",0,CouleursMaladie.ROUGE,20767000,14400,false);
        Ville osaka = new Ville("Osaka","Japon",0,CouleursMaladie.ROUGE,2871000,13000,false);
        Ville pekin = new Ville("Pékin","République Populaire De Chine",0,CouleursMaladie.ROUGE,1731000,5000,false);
        Ville seoul = new Ville("Séoul","Corée Du Sud",0,CouleursMaladie.ROUGE,22547000,10400,false);
        Ville shangai = new Ville("Shangai","République Populaire De Chine",0,CouleursMaladie.ROUGE,13482000,2200,false);
        Ville sydney = new Ville("Sydney","Australie",0,CouleursMaladie.ROUGE,37850000,2100,false);
        Ville taipei = new Ville("Taipei","Taïwan",0,CouleursMaladie.ROUGE,83330000,7300,false);
        Ville tokyo = new Ville("Tokyo","Japon",0,CouleursMaladie.ROUGE,13189000,6030,false);



        /*LES VILLES COLOREES EN JAUNE*/
        Ville bogota =new Ville("Bogota","Colombie",0,CouleursMaladie.JAUNE,8702000,2000, false);
        Ville buenos_aires =new Ville("Buenos Aires", "Argentine",0,CouleursMaladie.JAUNE, 13639000,5200,false);
        Ville johannesburg =new Ville("Johannesburg","Afrique Du Sud",0,CouleursMaladie.JAUNE,3888000,2400, false);
        Ville khartoum =new Ville("Khartoum", "Soudan",0,CouleursMaladie.JAUNE, 4887000,4500,false);
        Ville kinshasa =new Ville("Kinshasa","Congo",0,CouleursMaladie.JAUNE,9046000,15000,false);
        Ville lagos =new Ville("Lagos","Nigéria",0,CouleursMaladie.JAUNE,547000, 12700,false);
        Ville lima =new Ville("Lima","Pérou",0,CouleursMaladie.JAUNE,912000,14100,false);
        Ville los_angeles =new Ville("Los Angeles", "Etats Unis",0,CouleursMaladie.JAUNE,14900000,2400,false);
        Ville mexico =new Ville("Mexico","Mexique",0,CouleursMaladie.JAUNE,19463000,9500,false);
        Ville miami =new Ville("Miami","Etats-Unis",0,CouleursMaladie.JAUNE,5582000,1700,false);
        Ville santiago =new Ville("Santiago","Chili",0,CouleursMaladie.JAUNE,6015000,6500,false);
        Ville sao_paulo =new Ville("Sao Paulo", "Bresil",0,CouleursMaladie.JAUNE,20186000,6400,false);


        /*LES VILLES COLOREES EN NOIR*/
        Ville alger = new Ville("Alger","Algérie",0,CouleursMaladie.NOIR,2946000,6500,false);
        Ville bagdad = new Ville("Bagdad","Irak",0,CouleursMaladie.NOIR,6204000,10400,false);
        Ville calcutta = new Ville("Calcutta","Inde",0,CouleursMaladie.NOIR,14374000,1900,false);
        Ville chennai = new Ville("Chennai","Algérie",0,CouleursMaladie.NOIR,8865000,14600,false);
        Ville dehli = new Ville("Dehli","Inde",0,CouleursMaladie.NOIR,22242000,1500,false);
        Ville istanbul = new Ville("Istanbul","Turquie",0,CouleursMaladie.NOIR,13576000,9700,false);
        Ville karachi = new Ville("Karachi","Pakistan",0,CouleursMaladie.NOIR,28710000,25800,false);
        Ville le_caire = new Ville("Le Caire","Egypte",0,CouleursMaladie.NOIR,14780000,8900,false);
        Ville moscou = new Ville("Moscou","Russie",0,CouleursMaladie.NOIR,15512000,3500,false);
        Ville mumbai = new Ville("Mumbai","Inde",0,CouleursMaladie.NOIR,16910000,38900,false);
        Ville riyad = new Ville("Riyad","Arabie Saoudite ",0,CouleursMaladie.NOIR,5037000,3400,false);
        Ville teheran = new Ville("Teheran","Iran ",0,CouleursMaladie.NOIR,7419000,9500,false);


        /*LES VILLES COLOREES EN BLEU*/
        Ville atlanta = new Ville("Atlanta", "Etats-Unis", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville chicago = new Ville("Chicago", "Etats-Unis", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville essen = new Ville("Essen", "Allemagne", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville londres = new Ville("Londres", "Royaume-Uni", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville madrid = new Ville("Madrid", "Espagne", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville milan = new Ville("Milan", "Italie", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville montreal = new Ville("Montreal", "Canada", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville paris = new Ville("Paris", "France", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville saint_petersbourg = new Ville("Saint-Pétersbourg", "Russie", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville san_francisco = new Ville("San Francisco", "Etats-Unis", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville new_york = new Ville("New York", "Etats-Unis", 0,CouleursMaladie.BLEU,475000,700, false);
        Ville washington = new Ville("Washington", "Etats-Unis", 0,CouleursMaladie.BLEU,475000,700, false);

        /*VILLES LIEES*/
        atlanta.ajouterVillesLiees(chicago);
        chicago.ajouterVillesLiees(atlanta);
        montreal.ajouterVillesLiees(chicago);
        chicago.ajouterVillesLiees(montreal);
        chicago.ajouterVillesLiees(san_francisco);
        san_francisco.ajouterVillesLiees(chicago);
        chicago.ajouterVillesLiees(los_angeles);
        los_angeles.ajouterVillesLiees(chicago);
        chicago.ajouterVillesLiees(mexico);
        mexico.ajouterVillesLiees(chicago);

        montreal.ajouterVillesLiees(new_york);
        new_york.ajouterVillesLiees(montreal);
        montreal.ajouterVillesLiees(washington);
        washington.ajouterVillesLiees(montreal);

        washington.ajouterVillesLiees(new_york);
        new_york.ajouterVillesLiees(washington);
        washington.ajouterVillesLiees(miami);
        miami.ajouterVillesLiees(washington);
        washington.ajouterVillesLiees(atlanta);
        atlanta.ajouterVillesLiees(washington);

        atlanta.ajouterVillesLiees(miami);
        miami.ajouterVillesLiees(atlanta);

        miami.ajouterVillesLiees(mexico);
        mexico.ajouterVillesLiees(miami);
        miami.ajouterVillesLiees(bogota);
        bogota.ajouterVillesLiees(miami);
        mexico.ajouterVillesLiees(los_angeles);
        los_angeles.ajouterVillesLiees(mexico);
        mexico.ajouterVillesLiees(bogota);
        bogota.ajouterVillesLiees(mexico);
        mexico.ajouterVillesLiees(lima);
        lima.ajouterVillesLiees(mexico);

        lima.ajouterVillesLiees(santiago);
        santiago.ajouterVillesLiees(lima);
        lima.ajouterVillesLiees(bogota);
        bogota.ajouterVillesLiees(lima);

        bogota.ajouterVillesLiees(sao_paulo);
        sao_paulo.ajouterVillesLiees(bogota);
        bogota.ajouterVillesLiees(buenos_aires);
        buenos_aires.ajouterVillesLiees(bogota);
        buenos_aires.ajouterVillesLiees(sao_paulo);
        sao_paulo.ajouterVillesLiees(buenos_aires);

        new_york.ajouterVillesLiees(londres);
        londres.ajouterVillesLiees(new_york);
        new_york.ajouterVillesLiees(madrid);
        madrid.ajouterVillesLiees(new_york);

        madrid.ajouterVillesLiees(londres);
        londres.ajouterVillesLiees(madrid);
        madrid.ajouterVillesLiees(paris);
        paris.ajouterVillesLiees(madrid);
        madrid.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(madrid);

        paris.ajouterVillesLiees(londres);
        londres.ajouterVillesLiees(paris);
        paris.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(paris);
        paris.ajouterVillesLiees(essen);
        essen.ajouterVillesLiees(paris);

        londres.ajouterVillesLiees(essen);
        essen.ajouterVillesLiees(londres);
        essen.ajouterVillesLiees(milan);
        milan.ajouterVillesLiees(essen);
        essen.ajouterVillesLiees(saint_petersbourg);
        saint_petersbourg.ajouterVillesLiees(essen);

        saint_petersbourg.ajouterVillesLiees(moscou);
        moscou.ajouterVillesLiees(saint_petersbourg);
        saint_petersbourg.ajouterVillesLiees(istanbul);
        istanbul.ajouterVillesLiees(saint_petersbourg);

        milan.ajouterVillesLiees(paris);
        paris.ajouterVillesLiees(milan);
        milan.ajouterVillesLiees(istanbul);
        istanbul.ajouterVillesLiees(milan);

        istanbul.ajouterVillesLiees(moscou);
        moscou.ajouterVillesLiees(istanbul);
        moscou.ajouterVillesLiees(teheran);
        teheran.ajouterVillesLiees(moscou);

        istanbul.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(istanbul);
        istanbul.ajouterVillesLiees(le_caire);
        le_caire.ajouterVillesLiees(istanbul);
        istanbul.ajouterVillesLiees(bagdad);
        bagdad.ajouterVillesLiees(istanbul);

        alger.ajouterVillesLiees(madrid);
        madrid.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(paris);
        paris.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(istanbul);
        istanbul.ajouterVillesLiees(alger);
        alger.ajouterVillesLiees(le_caire);
        le_caire.ajouterVillesLiees(alger);

        le_caire.ajouterVillesLiees(bagdad);
        bagdad.ajouterVillesLiees(le_caire);
        le_caire.ajouterVillesLiees(riyad);
        riyad.ajouterVillesLiees(le_caire);
        le_caire.ajouterVillesLiees(khartoum);
        khartoum.ajouterVillesLiees(le_caire);

        bagdad.ajouterVillesLiees(riyad);
        riyad.ajouterVillesLiees(bagdad);
        bagdad.ajouterVillesLiees(teheran);
        teheran.ajouterVillesLiees(bagdad);
        bagdad.ajouterVillesLiees(karachi);
        karachi.ajouterVillesLiees(bagdad);

        teheran.ajouterVillesLiees(dehli);
        dehli.ajouterVillesLiees(teheran);
        teheran.ajouterVillesLiees(karachi);
        karachi.ajouterVillesLiees(teheran);

        dehli.ajouterVillesLiees(karachi);
        karachi.ajouterVillesLiees(dehli);
        dehli.ajouterVillesLiees(calcutta);
        calcutta.ajouterVillesLiees(dehli);
        dehli.ajouterVillesLiees(mumbai);
        mumbai.ajouterVillesLiees(dehli);
        dehli.ajouterVillesLiees(chennai);
        chennai.ajouterVillesLiees(dehli);

        karachi.ajouterVillesLiees(mumbai);
        mumbai.ajouterVillesLiees(karachi);
        karachi.ajouterVillesLiees(riyad);
        riyad.ajouterVillesLiees(karachi);

        mumbai.ajouterVillesLiees(chennai);
        chennai.ajouterVillesLiees(mumbai);

        calcutta.ajouterVillesLiees(chennai);
        chennai.ajouterVillesLiees(calcutta);
        calcutta.ajouterVillesLiees(hong_kong);
        hong_kong.ajouterVillesLiees(calcutta);
        calcutta.ajouterVillesLiees(bangkok);
        bangkok.ajouterVillesLiees(calcutta);

        chennai.ajouterVillesLiees(bangkok);
        bangkok.ajouterVillesLiees(chennai);
        chennai.ajouterVillesLiees(jakarta);
        jakarta.ajouterVillesLiees(chennai);

        bangkok.ajouterVillesLiees(hong_kong);
        hong_kong.ajouterVillesLiees(bangkok);
        bangkok.ajouterVillesLiees(ho_chi_minh_ville);
        ho_chi_minh_ville.ajouterVillesLiees(bangkok);
        bangkok.ajouterVillesLiees(jakarta);
        jakarta.ajouterVillesLiees(bangkok);

        hong_kong.ajouterVillesLiees(ho_chi_minh_ville);
        ho_chi_minh_ville.ajouterVillesLiees(hong_kong);
        hong_kong.ajouterVillesLiees(manille);
        manille.ajouterVillesLiees(hong_kong);
        hong_kong.ajouterVillesLiees(taipei);
        taipei.ajouterVillesLiees(hong_kong);
        hong_kong.ajouterVillesLiees(shangai);
        shangai.ajouterVillesLiees(hong_kong);

        jakarta.ajouterVillesLiees(sydney);
        sydney.ajouterVillesLiees(jakarta);
        jakarta.ajouterVillesLiees(ho_chi_minh_ville);
        ho_chi_minh_ville.ajouterVillesLiees(jakarta);

        ho_chi_minh_ville.ajouterVillesLiees(manille);
        manille.ajouterVillesLiees(ho_chi_minh_ville);

        manille.ajouterVillesLiees(sydney);
        sydney.ajouterVillesLiees(manille);
        manille.ajouterVillesLiees(taipei);
        taipei.ajouterVillesLiees(manille);

        taipei.ajouterVillesLiees(osaka);
        osaka.ajouterVillesLiees(taipei);
        taipei.ajouterVillesLiees(shangai);
        shangai.ajouterVillesLiees(taipei);

        shangai.ajouterVillesLiees(pekin);
        pekin.ajouterVillesLiees(shangai);
        shangai.ajouterVillesLiees(seoul);
        seoul.ajouterVillesLiees(shangai);
        shangai.ajouterVillesLiees(tokyo);
        tokyo.ajouterVillesLiees(shangai);

        osaka.ajouterVillesLiees(tokyo);
        tokyo.ajouterVillesLiees(osaka);

        tokyo.ajouterVillesLiees(seoul);
        seoul.ajouterVillesLiees(tokyo);

        seoul.ajouterVillesLiees(pekin);
        pekin.ajouterVillesLiees(seoul);

        khartoum.ajouterVillesLiees(lagos);
        lagos.ajouterVillesLiees(khartoum);
        khartoum.ajouterVillesLiees(kinshasa);
        kinshasa.ajouterVillesLiees(khartoum);
        khartoum.ajouterVillesLiees(johannesburg);
        johannesburg.ajouterVillesLiees(khartoum);

        johannesburg.ajouterVillesLiees(kinshasa);
        kinshasa.ajouterVillesLiees(johannesburg);

        kinshasa.ajouterVillesLiees(lagos);
        lagos.ajouterVillesLiees(kinshasa);

        lagos.ajouterVillesLiees(sao_paulo);
        sao_paulo.ajouterVillesLiees(lagos);

        sao_paulo.ajouterVillesLiees(madrid);
        madrid.ajouterVillesLiees(sao_paulo);

        san_francisco.ajouterVillesLiees(los_angeles);
        los_angeles.ajouterVillesLiees(san_francisco);


        lesVilles.add(bangkok);
        lesVilles.add(ho_chi_minh_ville);
        lesVilles.add(hong_kong);
        lesVilles.add(jakarta);
        lesVilles.add(manille);
        lesVilles.add(osaka);
        lesVilles.add(pekin);
        lesVilles.add(seoul);
        lesVilles.add(shangai);
        lesVilles.add(sydney);
        lesVilles.add(taipei);
        lesVilles.add(tokyo);


        lesVilles.add(bogota);
        lesVilles.add(buenos_aires);
        lesVilles.add(johannesburg);
        lesVilles.add(khartoum);
        lesVilles.add(kinshasa);
        lesVilles.add(lagos);
        lesVilles.add(lima);
        lesVilles.add(los_angeles);
        lesVilles.add(mexico);
        lesVilles.add(miami);
        lesVilles.add(santiago);
        lesVilles.add(sao_paulo);

        lesVilles.add(alger);
        lesVilles.add(bagdad);
        lesVilles.add(calcutta);
        lesVilles.add(chennai);
        lesVilles.add(dehli);
        lesVilles.add(istanbul);
        lesVilles.add(karachi);
        lesVilles.add(le_caire);
        lesVilles.add(moscou);
        lesVilles.add(mumbai);
        lesVilles.add(riyad);
        lesVilles.add(teheran);

        lesVilles.add(atlanta);
        lesVilles.add(chicago);
        lesVilles.add(essen);
        lesVilles.add(londres);
        lesVilles.add(madrid);
        lesVilles.add(milan);
        lesVilles.add(montreal);
        lesVilles.add(paris);
        lesVilles.add(saint_petersbourg);
        lesVilles.add(san_francisco);
        lesVilles.add(new_york);
        lesVilles.add(washington);


        return lesVilles;
    }
    public static List<ICartes> distribuerCartes(int i){
        return switch (i) {
            case 2 -> cartesJoueur.subList(0, 4);
            case 3 -> cartesJoueur.subList(5, 8);
            case 4 -> cartesJoueur.subList(8, 10);
            default -> null;
        };
    }

}
