package dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Updates;
import exceptions.ActionNotAutorizedException;
import exceptions.PartieNonSuspenduException;
import exceptions.PartiePleineException;
import modele.*;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.*;

import static dao.DaoProperties.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Dao {
    private static final MongoClient mongoClient = MongoClients.create(MONGO_URI_PORT);
    private static final CodecRegistry pojoCodeRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder()
                    .register(Joueur.class)
                    .register(Partie1Joueur.class)
                    .register(Partie.class)
                    .register(Plateau.class)
                    .register(Carte.class)
                    .register(Ville.class)
                    .register(TypeRole.class)
                    .register(TourJoueur.class)
                    .automatic(true).build()));

    private final MongoDatabase db = mongoClient.getDatabase(PANDEMIC).withCodecRegistry(pojoCodeRegistry);

    //pour s'inscrire
    // FINI
    public boolean inscription(String nomJoueur, String mdp) {
        Joueur joueur = new Joueur(nomJoueur,mdp);
        try{
            MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
            joueurMongoCollection.insertOne(joueur);
            return true;
        }catch(MongoWriteException e){
            return false;
        }
    }


    public boolean seConnecter(String nomJoueur, String mdp) {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
        Bson filter = Filters.and(Filters.eq(NOM_JOUEUR, nomJoueur), Filters.eq(PASSWORD, mdp));
        joueurMongoCollection.find(filter).first();
        return true;
    }

    public Joueur findJoueurByName(String nomJoueur){
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
        return joueurMongoCollection.find(Filters.eq(NOM_JOUEUR,nomJoueur)).first();
    }

    //Initialiser une partie
    //FINI
    public Partie partieInitialisee(String idPartie) throws ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = getPartie(idPartie);
        if(partie.isInitialisation()){
            return this.getPartie(idPartie);
        }
        final Map<String, byte[]> imageMap = partie.partieInitialisee();
        insererImage(imageMap);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), partie);
        return this.getPartie(idPartie);
    }

    public Partie actualiserPlateau(String idPartie) {
        return this.getPartie(idPartie);
    }
    public Partie creerPartie(String nomJoueur) throws ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
        if(Objects.nonNull(joueurMongoCollection.find(Filters.eq(NOM_JOUEUR, nomJoueur)).first())){
            ObjectId objectID = new ObjectId();
            System.out.println(objectID.toHexString());
            partieMongoCollection.insertOne(new Partie(objectID,new Partie1Joueur(nomJoueur, true)));
            return partieMongoCollection.find(Filters.eq(ID_PARTIE, objectID)).first();
        }else{
            throw new ActionNotAutorizedException("");
        }
    }

    public Partie rejoindrePartie(String idPartie, String nomJoueur) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);

        if(partie != null && Objects.nonNull(joueurMongoCollection.find(Filters.eq(NOM_JOUEUR, nomJoueur)).first())){
            if(partie.getPartieJoueur().stream().noneMatch(partieJoueur -> partieJoueur.getNom().equals(nomJoueur))){
                partie.ajouterJoueur(nomJoueur);
            }
            partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.set(PARTIE_JOUEUR,partie.getPartieJoueur()));
            return partie;
        }
        return null;
    }
    // FINI
    public Collection<Joueur> getLesJoueurs() {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
        Collection<Joueur> joueurCollection = new ArrayList<>();
        joueurMongoCollection.find().forEach(joueurCollection::add);
        return joueurCollection;
    }

    public Collection<Partie> getLesPartieARejoindre(){
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.eq(ETAT_PARTIE, EtatPartie.DEBUT)).forEach(partieCollection::add);
        return partieCollection;
    }
    public boolean supprimerLesJoueurs(){
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);
        joueurMongoCollection.drop();
        return true;
    }



    //FINI
    public Collection<Partie> getLesParties() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find().forEach(partieCollection::add);
        return partieCollection;
    }

    public Partie getPartie(String idPartie) {
        return db.getCollection(PARTIES, Partie.class).find(Filters.eq(ID_PARTIE,getObjectIdByIdPartie(idPartie))).first();
    }
    public boolean supprimerLesParties(){
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.drop();
        return true;
    }

    //Récuperer l'état actuel d'une partie
    //Fini
    public String getEtatPartie(String id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, id)).first();
        return partie != null ? String.valueOf(partie.getEtatPartie()) : "";
    }

    //Récupérer les parties suspendues
    //FINI
    public Collection<Partie> getLesPartiesSuspendues() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.eq(ETAT_PARTIE, EtatPartie.SUSPENDUE)).forEach(partieCollection::add);
        return partieCollection;
    }

    public Collection<Partie> getMesPartiesSuspendues(String nomJoueur) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.and
                        (Filters.eq(ETAT_PARTIE, EtatPartie.SUSPENDUE)
                                ,Filters.eq("partieJoueur.nom", nomJoueur)
                                ,Filters.eq("partieJoueur.createur", true)))
                .forEach(partieCollection::add);
        return partieCollection;
    }

    // FINI
    public boolean suspendreLaPartie(String idPartie, String nomJoueur) {
        MongoCollection<Partie> parties = db.getCollection(PARTIES, Partie.class);
        Partie partie = parties.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null && partie.getPartieJoueur().stream().anyMatch(partie1Joueur -> partie1Joueur.getNom().equals(nomJoueur))){
            partie.setEtatPartie(EtatPartie.SUSPENDUE);
            parties.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.set(ETAT_PARTIE, EtatPartie.SUSPENDUE));
            return true;
        }
        return false;
    }
    //pour quitter la partie
    public boolean quitterLaPartie(String idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null && partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
            partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(Updates.set(ETAT_PARTIE, EtatPartie.TERMINEE)));
            return true;
        } else {
            return false;
        }
    }

    public boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)),Filters.eq(ETAT_PARTIE,EtatPartie.SUSPENDUE))).first();
        if(Objects.nonNull(partie)){
            if(partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur()){
                partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(Updates.set(ETAT_PARTIE, EtatPartie.DEBUT)));
                return true;
            } else {
                return false;
            }
        }
        else {
            throw new PartieNonSuspenduException();
        }

    }

    public boolean peutQuitterLaPartie(String idPartie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)),Filters.or(Filters.eq(ETAT_PARTIE,EtatPartie.EN_COURS), Filters.eq(ETAT_PARTIE,DEBUT_PARTIE)))).first();
        return Objects.isNull(partie);
    }

    public  boolean createurPartie(String idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        return partie != null && partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur();
    }

    // Les actions des joueurs
    //modification le 02/04/2023
    public Partie traiterMaladie(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }
    //modification le 02/04/2023
    public Partie construireStationRecherche(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie deplacerStationRecherche(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie decouvrirRemede(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie piocherCarte(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie echangerCarte(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie passerTour(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    public Partie seDeplacer(Partie partie) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        partieMongoCollection.replaceOne(Filters.eq(ID_PARTIE,partie.getId()), partie);
        return this.getPartie(partie.getId().toString());
    }

    // Méthodes interne au DAO
    private ObjectId getObjectIdByIdPartie(String idPartie){
        return new ObjectId(idPartie);
    }


    private void insererImage(Map<String,byte[]> imageData){
        MongoCollection<Document> collection = db.getCollection("images");
        if(collection.countDocuments() == 0L) {
            List<Document> lesDocuments = new ArrayList<>();
            for (Map.Entry<String, byte[]> entry : imageData.entrySet()) {
                Document document = new Document("_id", entry.getKey())
                        .append("dataImage", new Binary(entry.getValue()));
                lesDocuments.add(document);
            }
            collection.insertMany(lesDocuments);
        }
    }

    public Map<String, byte[]> getImage(){
        MongoCollection<Document> collection = db.getCollection("images");
        FindIterable<Document> documents = collection.find();
        Map<String,byte[]> imageMap = new HashMap<>();
        for (Document document : documents) {
            String nom = document.getString("_id");
            byte[] imageBytes = document.get("dataImage", Binary.class).getData();
            imageMap.put(nom,imageBytes);
        }
        return imageMap;
    }



}
