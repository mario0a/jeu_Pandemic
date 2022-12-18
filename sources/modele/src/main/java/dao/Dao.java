package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import exceptions.PartieNonRepriseException;
import exceptions.PartieNonRepriseException;
import exceptions.PartieNonSuspenduException;
import exceptions.PartiePleineException;
import modele.Joueur;
import modele.Partie;
import modele.Partie1Joueur;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Dao {
    private static final MongoClient mongoClient = MongoClients.create("mongodb://172.17.0.2:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic1").withCodecRegistry(pojoCodeRegistry);

    //pour s'inscrire
    public static void inscription(String nomJoueur, String mdp) {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        Joueur joueur = new Joueur(nomJoueur, mdp);
        joueurMongoCollection.insertOne(joueur);
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
}
