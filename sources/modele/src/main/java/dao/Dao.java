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
                    .register(JeuDeCartes.class)
                    .register(Ville.class)
                    .register(CartesJoueur.class)
                    .automatic(true).build()));

    private static final MongoDatabase db = mongoClient.getDatabase("pandemic-1").withCodecRegistry(pojoCodeRegistry);

    //pour s'inscrire
    public boolean inscription(String nomJoueur, String mdp) {
        Joueur joueur = new Joueur(nomJoueur, mdp);
        try {
            MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
            joueurMongoCollection.insertOne(joueur);
            return true;
        } catch (MongoWriteException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean seConnecter(String nomJoueur, String mdp) {
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        return Objects.nonNull(joueurMongoCollection.find(Filters.and(Filters.eq("_id", nomJoueur), Filters.eq("mdp", mdp))).first());
    }

    //Initialiser une partie
    public boolean partieInitialisee(String id) {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.eq("_id", id)).first();
        return partie.partieInitialisee();
    }

    //Pour créer une nouvelle partie
    // FINI
    public void creerPartie(Long id, String nomJoueur) throws PartiePleineException, ActionNotAutorizedException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        MongoCollection<Joueur> joueurMongoCollection = db.getCollection("joueurs", Joueur.class);
        if(Objects.nonNull(joueurMongoCollection.find(Filters.eq("nom", nomJoueur)).first())){
            partieMongoCollection.insertOne(new Partie(id,new Partie1Joueur(nomJoueur, true)));
        }else{
            throw new ActionNotAutorizedException();
        }

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
    public Collection<Partie> getLesPartiesSuspendues() {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        partieMongoCollection.find(Filters.eq("etatPartie", "SUSPENDU")).forEach(partieCollection::add);
        return partieCollection;
    }

    public boolean suspendreLaPartie(Long id, String nomJoueur) {
        MongoCollection<Partie> parties = db.getCollection("parties", Partie.class);
        Partie partie = parties.find(Filters.eq("_id", id)).first();
        if(partie.getPartieJoueur().stream().anyMatch(partie1Joueur -> partie1Joueur.getNom().equals(nomJoueur))){
            partie.setEtatPartie(EtatPartie.SUSPENDUE);
            parties.updateOne(Filters.eq("_id",id), Updates.set("etatPartie", "SUSPENDUE"));
            return true;
        }
        return false;
    }

    //pour quitter la partie
    public boolean quitterLaPartie(Long idPartie, String nomJoueur){
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
//pour reprendre une partie
    public boolean reprendreUnePartie(Long idPartie, String nomJoueur) throws PartieNonSuspenduException {
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

    public boolean peutQuitterLaPartie(Long idPartie){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Partie partie = partieMongoCollection.find(Filters.and(Filters.eq("_id",idPartie),Filters.or(Filters.eq("etatPartie","EN_COURS"), Filters.eq("etatPartie","DEBUT")))).first();
        return Objects.isNull(partie);
    }
    //pour récupérer le joueur créateur de la partie
    public  boolean createurPartie(Long idPartie, String nomJoueur){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        return partie.getPartieJoueurByNomJoueur(nomJoueur).isCreateur();
    }

    public MongoDatabase getDb() {
        return db;
    }

    // Les actions des joueurs

    public  void traiterMaladie(Long idPartie, String nomJoueur, CouleursMaladie couleurMaladie, Actions actions){
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        actions.traiterMaladie(partie1Joueur,couleurMaladie);
    }

    //construction d'une station de recherche
    public void construireStationRecherche(Long idPartie,String nomJoueur, Actions actions) throws CentreRechercheDejaExistantException,
            NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        actions.construireStationRecherche(partie1Joueur);
    }
    //déplacement d'une station de recherche
    public void deplacerStationRecherche(Long idPartie,String nomJoueur, Actions actions, Ville ville) throws CentreRechercheDejaExistantException,
            CentreRechercheInexistantException, VilleIdentiqueException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        actions.deplacerStationRecherche(partie1Joueur,ville);
    }
    //découvrir un remède
    public void decouvrirRemede(Long idPartie,String nomJoueur,Actions actions) throws CentreRechercheInexistantException{
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        actions.decouvrirRemede(partie1Joueur);
    }

    //piocher une carte
    public void piocherCarte(Long idPartie,String nomJoueur,Actions actions, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes,
            NombreCarteDepasseException {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur partie1Joueur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueur));
        actions.piocherCarte(partie1Joueur,cartesJoueurList);
    }
    //échanger une carte avec un autre joueur
    public void echangerCarte(Long idPartie,String nomJoueurDonneur, String nomJoueurReceveur, CartesJoueur carte,Actions actions) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        MongoCollection<Partie> partieMongoCollection = db.getCollection("parties", Partie.class);
        Collection<Partie> partieCollection = new ArrayList<>();
        Partie partie = partieMongoCollection.find(Filters.eq("_id",idPartie)).first();
        Partie1Joueur joueurDonneur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurDonneur));
        Partie1Joueur joueurReceveur = (Partie1Joueur) partie.getPartieJoueur().stream().filter(partie1Joueur1 -> partie1Joueur1.getNom().equals(nomJoueurReceveur));
        actions.echangerCarte(joueurDonneur,joueurReceveur,carte);
    }






















}
