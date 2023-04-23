package dao;

import LesActions.Actions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import exceptions.*;
import facade.JeuDeCartes;
import modele.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Dao {
    private static final MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
    private static final CodecRegistry pojoCodeRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder()
                    .register(Joueur.class)
                    .register(Partie1Joueur.class)
                    .register(Partie.class)
                    .register(Plateau.class)
                    .register(Carte.class)
                    .register(Ville.class)
                    .register(TypeRole.class)
                    .automatic(true).build()));

    private final MongoDatabase db = mongoClient.getDatabase("pandemic").withCodecRegistry(pojoCodeRegistry);

    //pour s'inscrire
    // FINI
    public boolean inscription(String nomJoueur, String mdp) {
        Joueur joueur = new Joueur(nomJoueur,mdp);
        try{
            MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
            joueurMongoCollection.insertOne(joueur);
            return true;
        }catch(MongoWriteException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean seConnecter(String nomJoueur, String mdp) {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        return Objects.nonNull(joueurMongoCollection.find(Filters.and(Filters.eq("_id", nomJoueur), Filters.eq("mdp", mdp))).first());
    }

    public Joueur findJoueurByName(String nomJoueur){
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Joueur joueur=joueurMongoCollection.find(Filters.eq("nomJoueur",nomJoueur)).first();
        return joueur;
    }

    //Initialiser une partie
    //FINI
    public boolean partieInitialisee(Long id) throws ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();

        if(partie.partieInitialisee()) {
            partieMongoCollection.updateOne(Filters.eq("_id", id), Updates.combine(
                    Updates.set("partieJoueur", partie.getPartieJoueur()),
                    Updates.set("plateau",partie.getPlateau())));
            return true;
        }
        return false;
    }

    //Pour créer une nouvelle partie
    // FINI
    public Partie creerPartie(Long id, String nomJoueur) throws PartiePleineException, ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);

        if(Objects.nonNull(joueurMongoCollection.find(Filters.eq("nomJoueur", nomJoueur)).first())){
            partieMongoCollection.insertOne(new Partie(id,new Partie1Joueur(nomJoueur, true)));
            return partieMongoCollection.find(Filters.eq("_id",id)).first();
        }else{
            throw new ActionNotAutorizedException();
        }

    }

    public Partie rejoindrePartie(Long idPartie, String nomJoueur) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", idPartie)).first();
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);

        if(Objects.nonNull(joueurMongoCollection.find(Filters.eq("nomJoueur", nomJoueur)).first())){
            partie.ajouterJoueur(nomJoueur);
            partieMongoCollection.updateOne(Filters.eq("_id",idPartie), Updates.set("partieJoueur",partie.getPartieJoueur()));
            return partie;
        }
        return null;
    }
    // FINI
    public Collection<Joueur> getLesJoueurs() {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Collection<Joueur> joueurCollection = new ArrayList<>();
        joueurMongoCollection.find().forEach(joueurCollection::add);
        return joueurCollection;
    }

    public boolean supprimerLesJoueurs(){
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        joueurMongoCollection.drop();
        return true;
    }

    //FINI
    public Collection<Partie> getLesParties() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find().forEach(partieCollection::add);
        partieCollection.forEach(System.out::println);
        return partieCollection;
    }



    public boolean supprimerLesParties(){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        partieMongoCollection.drop();
        return true;
    }

    //Récuperer l'état actuel d'une partie
    //Fini
    public String getEtatPartie(Long id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();
        return String.valueOf(partie.getEtatPartie());
    }

    //Récupérer les parties suspendues
    //FINI
    public Collection<Partie> getLesPartiesSuspendues() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.eq("etatPartie", EtatPartie.SUSPENDUE)).forEach(partieCollection::add);
        return partieCollection;
    }

    // FINI
    public boolean suspendreLaPartie(Long id, String nomJoueur) {
        MongoCollection<Partie> parties = db.getCollection("parties", Partie.class);
        Partie partie = parties.find(Filters.eq("_id", id)).first();
        if(partie.getPartieJoueur().stream().anyMatch(partie1Joueur -> partie1Joueur.getNom().equals(nomJoueur))){
            partie.setEtatPartie(EtatPartie.SUSPENDUE);
            parties.updateOne(Filters.eq("_id",id), Updates.set("etatPartie", EtatPartie.SUSPENDUE));
            return true;
        }
        return false;
    }
    //pour quitter la partie
    public boolean quitterLaPartie(Long idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", idPartie)).first();
        if(partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
            partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("etatPartie", EtatPartie.TERMINEE)));
            return true;
        }
        else {
            return false;
        }
    }

    public boolean reprendreUnePartie(Long idPartie, String nomJoueur) throws PartieNonSuspenduException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id",idPartie),Filters.eq("etatPartie",EtatPartie.SUSPENDUE))).first();
        if(Objects.nonNull(partie)){
            if(partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
                partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("etatPartie", EtatPartie.DEBUT)));
                return true;
            }
            else {
                return false;
            }
        }
        else {
            throw new PartieNonSuspenduException();
        }

    }

    public boolean peutQuitterLaPartie(Long idPartie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id",idPartie),Filters.or(Filters.eq("etatPartie",EtatPartie.EN_COURS), Filters.eq("etatPartie","DEBUT")))).first();
        return Objects.isNull(partie);
    }

    public  boolean createurPartie(Long idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        return partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur();
    }

    public MongoDatabase getDb() {
        return db;
    }

    public void test(Long idPartie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        partie.setEtatPartie(EtatPartie.DEBUT);
        partie.getPlateau().ajouterCarteALaDefausse(partie.getPlateau().getCartesJoueur().get(0));
        partie.getPartieJoueur().get(0).setPosition(partie.getPlateau().getLesVilles().get(15));
        partieMongoCollection.replaceOne(Filters.eq("_id", idPartie), partie);
        // updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("partie",partie)));
        System.out.println(partie.toString()+"partie initiale \n début update");
        Partie partie1 = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        System.out.println(partie1.toString());
    }
    // Les actions des joueurs
    //modification le 02/04/2023
    public  void traiterMaladie(Long idPartie, String nomJoueur, String couleurMaladieStr){
        CouleursMaladie couleurMaladie = CouleursMaladie.valueOf(couleurMaladieStr);
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur)).findFirst().get();
        new Actions().traiterMaladie(partie,partie1Joueur,couleurMaladie);
        partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("partie1Joueur",partie1Joueur)));

        //insérer en BDD
    }
    //modification le 02/04/2023
    public void construireStationRecherche(Long idPartie,String nomJoueur) throws CentreRechercheDejaExistantException,
            NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));

        new Actions().construireStationRecherche(partie,partie1Joueur);
        partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("partie",partie)));

        //insérer en BDD
    }

    //modification le 02/04/2023
    public void deplacerStationRecherche(Long idPartie,String nomJoueur,String villeStr) throws CentreRechercheDejaExistantException,
            CentreRechercheInexistantException, VilleIdentiqueException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        Ville ville = (Ville) partie1Joueur.getVillesPartie().stream().filter(ville1 -> ville1.getNomVille().equals(villeStr));

        new Actions().deplacerStationRecherche(partie1Joueur,ville);
        partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("partie",partie)));
        //insérer en BDD
    }

    //modification le 02/04/2023
    public void decouvrirRemede(Long idPartie,String nomJoueur) throws CentreRechercheInexistantException{
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));

        new Actions().decouvrirRemede(partie,partie1Joueur);

        //insérer en BDD
    }

    //modification le 02/04/2023
    public void piocherCarte(Long idPartie,String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes,
            NombreCarteDepasseException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));

        new Actions().piocherCarte(partie1Joueur,cartesJoueurList);

        //insérer en BDD
    }

    //modification le 02/04/2023
    //carteJoueur devient string entre dao et spring (faire un cast)
    public void echangerCarte(Long idPartie,String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur joueurDonneur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurDonneur));
        Partie1Joueur joueurReceveur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurReceveur));

        new Actions().echangerCarte(joueurDonneur,joueurReceveur,carte);

        //insérer en BDD
    }

}



















