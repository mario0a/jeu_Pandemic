package dao;

import LesActions.Actions;
import LesActions.IAction;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import exceptions.*;
import exceptions.PartieNonRepriseException;
import modele.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Dao {
    private static final MongoClient mongoClient = MongoClients.create("mongodb://0.0.0.0:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic1").withCodecRegistry(pojoCodeRegistry);


    //pour s'inscrire
    public static void inscription(String nomJoueur, String mdp) {
        try{
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Joueur joueur = new Joueur(nomJoueur, mdp);
        joueurMongoCollection.insertOne(joueur);
        }catch(MongoWriteException e){
            System.out.println(e.getMessage());
        }
    }


    public static boolean seConnecter(String nomJoueur, String mdp) {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        return Objects.nonNull(joueurMongoCollection.find(Filters.and(Filters.eq("_id", nomJoueur), Filters.eq("mdp", mdp))).first());
    }

    //Initialiser une partie
    public static boolean partieInitialisee(String id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();
        return partie.partieInitialisee();
    }

    //Pour créer une nouvelle partie
    public static void creerPartie(String id, String nomJoueur) throws PartiePleineException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = new Partie(id);
        partie.ajouterPartie1Joueur(new Partie1Joueur(nomJoueur, true));
        partieMongoCollection.insertOne(partie);
    }

    public static Collection<Partie> getLesParties() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find().forEach(p -> partieCollection.add(p));
        return partieCollection;
    }

    //Récuperer l'état actuel d'une partie
    public static String getEtatPartie(String id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();
        return String.valueOf(partie.getEtatPartie());
    }

    //Récupérer les parties suspendues
    public static Collection<Partie> getLesPartiesSuspendues() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.eq("etatPartie", "SUSPENDU")).forEach(p -> partieCollection.add(p));
        return partieCollection;
    }

    public static boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id", idPartie), Filters.or(Filters.eq("etatPartie", "EN_COURS"), Filters.eq("etatPartie", "DEBUT")))).first();
        if (Objects.nonNull(partie)) {
            if (partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()) {
                partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("etatPartie", "SUSPENDU")));
                return true;
            } else {
                return false;
            }
        } else {
            throw new PartieNonRepriseException();
        }
    }

    //pour quitter la partie
    public static boolean quitterLaPartie(String idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", idPartie)).first();
        if(partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
            partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("etatPartie", "TERMINE")));
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id",idPartie),Filters.eq("etatPartie","SUSPENDU"))).first();
        if(Objects.nonNull(partie)){
            if(partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
                partieMongoCollection.updateOne(Filters.eq("_id", idPartie), Updates.combine(Updates.set("etatPartie", "DEBUT")));
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

    public static boolean peutQuitterLaPartie(String idPartie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id",idPartie),Filters.or(Filters.eq("etatPartie","EN_COURS"), Filters.eq("etatPartie","DEBUT")))).first();
        return Objects.isNull(partie);
    }

    public  static boolean createurPartie(String idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        return partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur();
    }

    public static MongoDatabase getDb() {
        return db;
    }


    // Les actions des joueurs

    public void traiterMaladie(String idPartie, String nomJoueur, CouleursMaladie couleurMaladie, Actions actions){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueur=partie.getJoueurByName(nomJoueur);
        actions.traiterMaladie(joueur,couleurMaladie);
    }

    public void construireStationRecherche(String idPartie,String nomJoueur, Actions actions) throws CentreRechercheDejaExistantException,
            NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueur=partie.getJoueurByName(nomJoueur);
        actions.construireStationRecherche(joueur);
    }


    public void deplacerStationRecherche(String idPartie,String nomJoueur, Actions actions, Ville ville) throws CentreRechercheDejaExistantException,
            CentreRechercheInexistantException, VilleIdentiqueException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueur=partie.getJoueurByName(nomJoueur);
        actions.deplacerStationRecherche(joueur,ville);
    }


    public void decouvrirRemede(String idPartie,String nomJoueur,Actions actions) throws CentreRechercheInexistantException{
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueur=partie.getJoueurByName(nomJoueur);
        actions.decouvrirRemede(joueur);
    }


    public void piocherCarte(String idPartie,String nomJoueur,Actions actions, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes,
            NombreCarteDepasseException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueur=partie.getJoueurByName(nomJoueur);
        actions.piocherCarte(joueur,cartesJoueurList);
    }


    public void echangerCarte(String idPartie,String nomJoueurDonneur, String nomJoueurReceveur, CartesJoueur carte,Actions actions) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Joueur joueurDonneur=partie.getJoueurByName(nomJoueurDonneur);
        Joueur joueurReceveur=partie.getJoueurByName(nomJoueurReceveur);
        actions.echangerCarte(joueurDonneur,joueurReceveur,carte);
    }

    }
