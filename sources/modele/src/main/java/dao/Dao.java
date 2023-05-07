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
import modele.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    public boolean partieInitialisee(String idPartie) throws ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();

        if(partie != null && partie.partieInitialisee()) {
            partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(
                    Updates.set(PARTIE_JOUEUR, partie.getPartieJoueur()),
                    Updates.set(PLATEAU,partie.getPlateau())));
            return true;
        }
        return false;
    }

    public String creerPartie(String nomJoueur) throws PartiePleineException, ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);

        if(Objects.nonNull(joueurMongoCollection.find(Filters.eq(NOM_JOUEUR, nomJoueur)).first())){
            ObjectId objectID = new ObjectId();
            partieMongoCollection.insertOne(new Partie(objectID,new Partie1Joueur(nomJoueur, true)));
            return objectID.toString();
        }else{
            throw new ActionNotAutorizedException();
        }
    }

    public Partie rejoindrePartie(String idPartie, String nomJoueur) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection(JOUEURS, Joueur.class);

        if(partie != null && Objects.nonNull(joueurMongoCollection.find(Filters.eq(NOM_JOUEUR, nomJoueur)).first())){
            partie.ajouterJoueur(nomJoueur);
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
    public  void traiterMaladie(String idPartie, String nomJoueur, String couleurMaladieStr){
        CouleursMaladie couleurMaladie = CouleursMaladie.valueOf(couleurMaladieStr);
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur partie1Joueur = partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur)).findFirst().orElse(null);
            if(partie1Joueur != null) {
                new Actions().traiterMaladie(partie, partie1Joueur, couleurMaladie);
                partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(Updates.set(PARTIE_1JOUEUR, partie1Joueur)));
            }
        }
    }
    //modification le 02/04/2023
    public void construireStationRecherche(String idPartie,String nomJoueur) throws CentreRechercheDejaExistantException,
            NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
            new Actions().construireStationRecherche(partie, partie1Joueur);
            partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(Updates.set(PARTIE, partie)));
        }
    }

    //modification le 02/04/2023
    public void deplacerStationRecherche(String idPartie,String nomJoueur,String villeStr) throws CentreRechercheDejaExistantException,
            CentreRechercheInexistantException, VilleIdentiqueException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
            Ville ville = (Ville) partie1Joueur.getVillesPartie().stream().filter(ville1 -> ville1.getNomVille().equals(villeStr));
            new Actions().deplacerStationRecherche(partie1Joueur, ville);
            partieMongoCollection.updateOne(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie)), Updates.combine(Updates.set(PARTIE, partie)));
        }
    }

    //modification le 02/04/2023
    public void decouvrirRemede(String idPartie,String nomJoueur) throws CentreRechercheInexistantException{
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
            new Actions().decouvrirRemede(partie, partie1Joueur);
        }
        //insérer en BDD
    }

    //modification le 02/04/2023
    public void piocherCarte(String idPartie,String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes,
            NombreCarteDepasseException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
            new Actions().piocherCarte(partie1Joueur, cartesJoueurList);
        }
        //insérer en BDD
    }

    //modification le 02/04/2023
    //carteJoueur devient string entre dao et spring (faire un cast)
    public void echangerCarte(String idPartie,String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        MongoCollection<Partie> partieMongoCollection = db.getCollection(PARTIES, Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq(ID_PARTIE, getObjectIdByIdPartie(idPartie))).first();
        if(partie != null) {
            Partie1Joueur joueurDonneur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurDonneur));
            Partie1Joueur joueurReceveur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurReceveur));
            new Actions().echangerCarte(joueurDonneur, joueurReceveur, carte);
        }
        //insérer en BDD
    }

    public MongoDatabase getDb() {
        return db;
    }

    // Méthodes interne au DAO
    private ObjectId getObjectIdByIdPartie(String idPartie){
        return new ObjectId(idPartie);
    }
}






