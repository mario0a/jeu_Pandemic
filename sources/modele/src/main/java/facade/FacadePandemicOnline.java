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
    public Partie creerPartie(Long id, String nomJoueur) throws PartiePleineException, ActionNotAutorizedException {
        return dao.creerPartie(id,nomJoueur);
    }

    @Override
    public boolean partieInitialisee(Long id) throws ActionNotAutorizedException {
        return dao.partieInitialisee(id);
    }

    @Override
    public Partie rejoindrePartie(Long idPartie, String nomJoueur) {
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
    public String getEtatPartie(Long id) {
        return dao.getEtatPartie(id);
    }

    @Override
    public Collection<Partie> getLesPartiesSuspendues() {
        return dao.getLesPartiesSuspendues();
    }

    @Override
    public boolean suspendreLaPartie(Long idPartie, String nomJoueur) throws PartieNonRepriseException {
        return dao.suspendreLaPartie(idPartie,nomJoueur);    }

    @Override
    public boolean quitterLaPartie(Long idPartie, String nomJoueur) {
        return dao.quitterLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean reprendreUnePartie(Long idPartie, String nomJoueur) throws PartieNonSuspenduException {
        return dao.reprendreUnePartie(idPartie,nomJoueur);
    }

    @Override
    public boolean peutQuitterLaPartie(Long idPartie) {
        return dao.peutQuitterLaPartie(idPartie);
    }

    @Override
    public boolean createurPartie(Long idPartie, String nomJoueur) {
        return dao.createurPartie(idPartie, nomJoueur);
    }

    @Override
    public void traiterMaladie(Long idPartie, String nomJoueur, String couleurMaladieStr) {
        dao.traiterMaladie( idPartie,  nomJoueur,  couleurMaladieStr);
    }

    @Override
    public void construireStationRecherche(Long idPartie, String nomJoueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        dao.construireStationRecherche( idPartie,  nomJoueur);
    }


    @Override
    public void deplacerStationRecherche(Long idPartie, String nomJoueur, String villeStr) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException {
        dao.deplacerStationRecherche( idPartie, nomJoueur, villeStr);
    }

    @Override
    public void decouvrirRemede(Long idPartie, String nomJoueur) throws CentreRechercheInexistantException {
        dao.decouvrirRemede( idPartie,  nomJoueur);
    }

    @Override
    public void piocherCarte(Long idPartie, String nomJoueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        dao.piocherCarte(idPartie,  nomJoueur, cartesJoueurList);
    }

    @Override
    public void echangerCarte(Long idPartie, String nomJoueurDonneur, String nomJoueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        dao.echangerCarte(idPartie,  nomJoueurDonneur,  nomJoueurReceveur,  carte);
    }
}
