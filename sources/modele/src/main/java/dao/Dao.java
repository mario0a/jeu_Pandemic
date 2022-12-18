package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import modele.Partie;
import modele.Partie1Joueur;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Dao {
    private static final MongoClient mongoClient = MongoClients.create("mongodb://172.17.0.2:27017");
    private static final CodecRegistry pojoCodeRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final MongoDatabase db = mongoClient.getDatabase("pandemic1").withCodecRegistry(pojoCodeRegistry);

    //pour s'inscrire
    public static void inscription(String nomJoueur, String mdp){
        MongoCollection<Partie>
    }

    //Initialiser une partie
    public static boolean partieInitialisee(String id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();
        return partie.partieInitialisee();
    }
}
