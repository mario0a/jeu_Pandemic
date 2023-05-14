package facade;

import LesActions.Actions;
import controller.ModelController;
import dao.Dao;
import dtos.JoueurDto;
import dtos.actions.*;
import dtos.jeu.PlateauDto;
import dtos.jeu.PlateauInitialDto;
import dtos.parties.PartieDto;
import dtos.parties.PartiesSuspenduesDto;
import exceptions.*;
import modele.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacadePandemicOnline implements IFacadePandemicOnline {
    ModelController modelController = new ModelController();
    Dao dao = new Dao();
    ModelMapper modelMapper = new ModelMapper();


    @Override
    public boolean inscription(String nomJoueur, String mdp) {

        return dao.inscription(nomJoueur,mdp);
    }

    @Override
    public JoueurDto findJoueurByName(String nomJoueur) {
        Joueur joueur = dao.findJoueurByName(nomJoueur);
        return modelMapper.map(joueur,JoueurDto.class);
    }

    @Override
    public boolean seConnecter(String nomJoueur, String mdp) {
        return dao.seConnecter(nomJoueur,mdp);
    }

    @Override
    public PartieDto creerPartie(String nomJoueur) throws PartiePleineException, ActionNotAutorizedException{
        Partie partie = dao.creerPartie(nomJoueur);
        PartieDto partieDto = modelMapper.map(partie, PartieDto.class);
        for(Partie1Joueur partie1Joueur:partie.getPartieJoueur()){
            String nom =partie1Joueur.getNom();
            partieDto.getLesJoueurs().add(nom);
        }
        return partieDto;
    }

    @Override
    public PlateauInitialDto partieInitialisee(String idPartie) throws ActionNotAutorizedException {
        Partie partie = dao.partieInitialisee(idPartie);
        PlateauDto plateauDto = getPlateau(partie);
        PlateauInitialDto plateauInitialDto = new PlateauInitialDto(plateauDto);
        plateauInitialDto.setImageData(dao.getImage());
        return plateauInitialDto;
    }

    @Override
    public PlateauDto actualiserPlateau(String idPartie){
        Partie partie = dao.actualiserPlateau(idPartie);
        return getPlateau(partie);
    }

    @Override
    public PartieDto rejoindrePartie(String idPartie,String nomJoueur) {
        Partie partie = dao.rejoindrePartie(idPartie,nomJoueur);
        PartieDto partieDto = modelMapper.map(partie, PartieDto.class);
        for(Partie1Joueur partie1Joueur:partie.getPartieJoueur()){
            String nom =partie1Joueur.getNom();
            partieDto.getLesJoueurs().add(nom);
        }
        return partieDto;
    }

    @Override
    public boolean supprimerLesParties() {
        return dao.supprimerLesParties();
    }

    @Override
    public Collection<PartieDto> getLesParties() {
        Collection<Partie> parties = dao.getLesParties();
        return getPartieDtos(parties);
    }

    @Override
    public PartieDto getPartie(String idPartie) {
        Partie partie = dao.getPartie(idPartie);
        PartieDto partieDto = modelMapper.map(partie, PartieDto.class);
        for(Partie1Joueur partie1Joueur:partie.getPartieJoueur()){
            String nom =partie1Joueur.getNom();
            partieDto.getLesJoueurs().add(nom);
        }
        return partieDto;
    }

    @Override
    public Collection<PartieDto> getLesPartiesARejoindre() {
        Collection<Partie> parties = dao.getLesPartieARejoindre();
        return getPartieDtos(parties);
    }


    @Override
    public Collection<JoueurDto> getLesJoueurs() {
        Collection<Joueur> joueurs = dao.getLesJoueurs();
        Collection<JoueurDto> joueursDtos = new ArrayList<>();
        for(Joueur joueur :joueurs){
            JoueurDto joueurDto = modelMapper.map(joueur, JoueurDto.class);
            joueursDtos.add(joueurDto);
        }
        return joueursDtos;
    }

    @Override
    public boolean supprimerLesJoueurs() {
        return dao.supprimerLesJoueurs();
    }

    @Override
    public String getEtatPartie(String idPartie) {
        return dao.getEtatPartie(idPartie);
    }

    @Override
    public Collection<PartiesSuspenduesDto> getLesPartiesSuspendues() {
        Collection<Partie> parties = dao.getLesPartiesSuspendues();
        return getPartiesSuspenduesDtos(parties);
    }

    @Override
    public Collection<PartiesSuspenduesDto> getMesPartiesSuspendues(String nomJoueur) {
        Collection<Partie> parties = dao.getMesPartiesSuspendues(nomJoueur);
        return getPartiesSuspenduesDtos(parties);
    }

    @Override
    public boolean suspendreLaPartie(String idPartie, String nomJoueur) {
        return dao.suspendreLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean quitterLaPartie(String idPartie, String nomJoueur) {
        return dao.quitterLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException {
        return dao.reprendreUnePartie(idPartie,nomJoueur);
    }

    @Override
    public boolean peutQuitterLaPartie(String idPartie) {
        return dao.peutQuitterLaPartie(idPartie);
    }

    @Override
    public boolean createurPartie(String idPartie, String nomJoueur) {
        return dao.createurPartie(idPartie, nomJoueur);
    }
    @Override
    public PlateauDto traiterMaladie(TraiterMaladieDto traiterMaladieDto) throws ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.traiterMaladieControleur(traiterMaladieDto);
        return getPlateau(partie);
    }
    @Override
    public PlateauDto construireStationRecherche(NomJoueurDTO nomJoueurDTO) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.construireStationRechercheControleur(nomJoueurDTO);
        return getPlateau(partie);
    }

    @Override
    public PlateauDto deplacerStationRecherche(VilleCarteDto villeDto) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.deplacerStationRechercheControleur(villeDto);
        return getPlateau(partie);
    }

    @Override
    public PlateauDto decouvrirRemede(NomJoueurDTO nomJoueurDTO) throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.decouvrirRemedeControleur(nomJoueurDTO);
        return getPlateau(partie);
    }

    @Override
    public PlateauDto piocherCarte(NomJoueurDTO nomJoueurDTO) throws CartesJoueurInsuffisantes, NombreCarteDepasseException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.piocherCarteControleur(nomJoueurDTO);
        return getPlateau(partie);
    }

    @Override
    public PlateauDto echangerCarte(EchangerCarteDto echangerCarteDto) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = modelController.echangerCarteControleur(echangerCarteDto);
        return getPlateau(partie);
    }

    @Override
    public PlateauDto passerTour(NomJoueurDTO nomJoueurDTO) throws PasTourJoueurException {
        Partie partie = modelController.passerTourControleur(nomJoueurDTO);
        return getPlateau(partie);
    }
    @Override
    public PlateauDto seDeplacerVolDirect(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException {
        Partie partie = modelController.seDeplacerVolDirectControleur(deplacementDto);
        return getPlateau(partie);
    }
    @Override
    public PlateauDto seDeplacerAvecVoiture(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException {
        Partie partie = modelController.seDeplacerAvecVoitureControleur(deplacementDto);
        return getPlateau(partie);
    }
    @Override
    public PlateauDto seDeplacerNavette(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException {
        Partie partie = modelController.seDeplacerNavetteControleur(deplacementDto);
        return getPlateau(partie);
    }
    @Override
    public PlateauDto seDeplacerVolCharter(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException {
        Partie partie = modelController.seDeplacerVolCharterControleur(deplacementDto);
        return getPlateau(partie);
    }

    private String nomJoueurQuiJoue(Partie partie){
        return partie.getTourJoueur().aQuiLeTour();
    }
    private PlateauDto getPlateau(Partie partie){
        PlateauDto plateauDto = modelMapper.map(partie, PlateauDto.class);
        plateauDto.setNomJoueur(nomJoueurQuiJoue(partie));
        for(Partie1Joueur partie1Joueur:partie.getPartieJoueur()){
            String nom =partie1Joueur.getNom();
            plateauDto.getLesJoueurs().add(nom);
        }
        for(Ville ville:partie.getPlateau().getLesVilles()){
            String nom =ville.getNomVille();
            plateauDto.getLesVilles().add(nom);
        }

        for(Carte carte:partie.getPlateau().getCartesJoueur()){
            if(carte.getTypeCarte()==TypeCarte.EPIDEMIE){
                String nom =carte.getNomCarte();
                plateauDto.getCarteEpidemie().add(nom);
            }
        }

        for(Carte carte:partie.getPlateau().getCartesPropagation()){
            String nom =carte.getNomCarte();
            plateauDto.getCartesPropagation().add(nom);
        }

        for(Carte carte:partie.getPlateau().getDefausse_carteDePropagation()){
            String nom =carte.getNomCarte();
            plateauDto.getDefausse_carteDePropagation().add(nom);
        }

        for(Carte carte:partie.getPlateau().getDefausse_cartesJoueur()){
            String nom =carte.getNomCarte();
            plateauDto.getDefausse_cartesJoueur().add(nom);
        }

        for(Carte carte:partie.getPlateau().getCartesJoueur()){
            String nom =carte.getNomCarte();
            plateauDto.getCartesJoueur().add(nom);
        }
        plateauDto.setCartes_en_main(
                partie.getPartieJoueurByNomJoueur(
                        plateauDto.getNomJoueur()).getCartesEnMain().stream().map(Carte::getReference).toList()
        );

        return plateauDto;
    }
    private Collection<PartieDto> getPartieDtos(Collection<Partie> parties) {
        Collection<PartieDto> partiesDtos = new ArrayList<>();
        for (Partie partie : parties) {
            PartieDto partieDto = modelMapper.map(partie, PartieDto.class);
            for(Partie1Joueur partie1Joueur:partie.getPartieJoueur()){
                String nom =partie1Joueur.getNom();
                partieDto.getLesJoueurs().add(nom);
            }
            partiesDtos.add(partieDto);
        }
        return partiesDtos;
    }
    private Collection<PartiesSuspenduesDto> getPartiesSuspenduesDtos(Collection<Partie> parties) {
        Collection<PartiesSuspenduesDto> partiesDtos = new ArrayList<>();
        for (Partie partie : parties) {
            PartiesSuspenduesDto partieDto = modelMapper.map(partie, PartiesSuspenduesDto.class);
            for (Partie1Joueur partie1Joueur : partie.getPartieJoueur()) {
                String nom = partie1Joueur.getNom();
                partieDto.getLesJoueurs().add(nom);
            }
            partiesDtos.add(partieDto);
        }
        return partiesDtos;
    }

}