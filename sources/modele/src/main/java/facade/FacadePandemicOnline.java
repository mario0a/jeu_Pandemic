package facade;

import LesActions.Actions;
import dao.Dao;
import exceptions.*;
import modele.*;
import modele.interfaces.ICartes;

import java.util.Collection;
import java.util.List;

public class FacadePandemicOnline implements IFacadePandemicOnline {

    @Override
    public boolean partieInitialisee(String idPartie) {
        return Dao.partieInitialisee(idPartie);
    }

    @Override
    public boolean inscription(String nomJoueur, String mdp) {
        return Dao.inscription(nomJoueur,mdp);
    }

    @Override
    public boolean seConnecter(String nomJoueur, String mdp) {
        return Dao.seConnecter(nomJoueur,mdp);
    }

    @Override
    public void creerPartie(Long id, String nomJoueur) throws PartiePleineException, ActionNotAutorizedException {
        Dao.creerPartie(id,nomJoueur);
    }


    @Override
    public boolean supprimerLesParties() {
        return Dao.supprimerLesParties();
    }

    @Override
    public Collection<Partie> getLesParties() {
        return Dao.getLesParties();
    }

    @Override
    public Collection<Joueur> getLesJoueurs() {
        return Dao.getLesJoueurs();
    }

    @Override
    public boolean supprimerLesJoueurs() {
        return Dao.supprimerLesJoueurs();
    }

    @Override
    public String getEtatPartie(Long id) {
        return Dao.getEtatPartie(id);
    }

    @Override
    public Collection<Partie> getLesPartiesSuspendues() {
        return Dao.getLesPartiesSuspendues();
    }

    @Override
    public boolean suspendreLaPartie(Long idPartie, String nomJoueur) throws PartieNonRepriseException {
        return Dao.suspendreLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean quitterLaPartie(Long idPartie, String nomJoueur) {
        return Dao.quitterLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean reprendreUnePartie(Long idPartie, String nomJoueur) throws PartieNonSuspenduException {
        return Dao.reprendreUnePartie(idPartie,nomJoueur);
    }

    @Override
    public boolean peutQuitterLaPartie(Long idPartie) {
        return Dao.peutQuitterLaPartie(idPartie);
    }

    @Override
    public boolean createurPartie(Long idPartie, String nomJoueur) {
        return Dao.createurPartie(idPartie, nomJoueur);
    }

    @Override
    public void traiterMaladie(Long idPartie, String nomJoueur, CouleursMaladie couleurMaladie, Actions actions) {
        Dao.traiterMaladie( idPartie,  nomJoueur,  couleurMaladie,  actions);
    }

    @Override
    public void construireStationRecherche(Long idPartie, String nomJoueur, Actions actions) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        Dao.construireStationRecherche( idPartie,  nomJoueur,  actions);
    }

    @Override
    public void deplacerStationRecherche(Long idPartie, String nomJoueur, Actions actions, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException {
        Dao.deplacerStationRecherche( idPartie,  nomJoueur,  actions,  ville);
    }

    @Override
    public void decouvrirRemede(Long idPartie, String nomJoueur, Actions actions) throws CentreRechercheInexistantException {
        Dao.decouvrirRemede( idPartie,  nomJoueur,  actions);
    }

    @Override
    public void piocherCarte(Long idPartie, String nomJoueur, Actions actions, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        Dao.piocherCarte(idPartie,  nomJoueur,  actions, cartesJoueurList);
    }

    @Override
    public void echangerCarte(Long idPartie, String nomJoueurDonneur, String nomJoueurReceveur, CartesJoueur carte, Actions actions) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        Dao.echangerCarte(idPartie,  nomJoueurDonneur,  nomJoueurReceveur,  carte,  actions);
    }
}