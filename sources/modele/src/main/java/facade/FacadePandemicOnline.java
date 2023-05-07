package facade;

import LesActions.Actions;
import dao.Dao;
import exceptions.*;
import modele.*;

import java.util.Collection;
import java.util.List;

public class FacadePandemicOnline implements IFacadePandemicOnline {
    Dao dao = new Dao();


    @Override
    public boolean inscription(String nomJoueur, String mdp) {
        return dao.inscription(nomJoueur,mdp);
    }

    @Override
    public Joueur findJoueurByName(String nomJoueur) {
        return dao.findJoueurByName(nomJoueur);
    }

    @Override
    public boolean seConnecter(String nomJoueur, String mdp) {
        return dao.seConnecter(nomJoueur,mdp);
    }

    @Override
    public String creerPartie(String nomJoueur) throws PartiePleineException, ActionNotAutorizedException{
        return dao.creerPartie(nomJoueur);
    }
    @Override
    public boolean partieInitialisee(String idPartie) throws ActionNotAutorizedException {
        return dao.partieInitialisee(idPartie);
    }

    @Override
    public Partie rejoindrePartie(String idPartie,String nomJoueur) {
        return dao.rejoindrePartie(idPartie,nomJoueur);
    }

    @Override
    public boolean supprimerLesParties() {
        return dao.supprimerLesParties();
    }

    @Override
    public Collection<Partie> getLesParties() {
        return dao.getLesParties();
    }

    @Override
    public Collection<Joueur> getLesJoueurs() {
        return dao.getLesJoueurs();
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
    public Collection<Partie> getLesPartiesSuspendues() {
        return dao.getLesPartiesSuspendues();
    }

    @Override
    public Collection<Partie> getMesPartiesSuspendues(String nomJoueur) {
        return dao.getMesPartiesSuspendues(nomJoueur);
    }

    @Override
    public boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException {
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
    //modification le 02/04/2023
    @Override
    public void traiterMaladie(String idPartie, String nomJoueur, String couleurMaladieStr) {
        dao.traiterMaladie( idPartie,  nomJoueur,  couleurMaladieStr);
    }
    //modification le 02/04/2023
    @Override
    public void construireStationRecherche(String idPartie, String nomJoueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        dao.construireStationRecherche( idPartie,  nomJoueur);
    }

    @Override
    public void deplacerStationRecherche(String idPartie, String nomJoueur, String villeStr) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException {
        dao.deplacerStationRecherche( idPartie, nomJoueur, villeStr);
    }
    //modification le 02/04/2023
    @Override
    public void decouvrirRemede(String idPartie, String nomJoueur) throws CentreRechercheInexistantException {
        dao.decouvrirRemede( idPartie, nomJoueur);
    }
    //modification
    @Override
    public void piocherCarte(String idPartie, String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        dao.piocherCarte(idPartie, nomJoueur, cartesJoueurList);
    }

    //modification
    @Override
    public void echangerCarte(String idPartie, String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        dao.echangerCarte(idPartie, nomJoueurDonneur,  nomJoueurReceveur,  carte);
    }



}