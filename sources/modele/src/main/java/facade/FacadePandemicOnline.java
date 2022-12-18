package facade;

import dao.Dao;
import exceptions.PartieNonRepriseException;
import exceptions.PartieNonSuspenduException;
import exceptions.PartiePleineException;
import modele.Partie;
import modele.Plateau;
import modele.Ville;
import modele.interfaces.ICartes;

import java.util.Collection;

public class FacadePandemicOnline implements IFacadePandemicOnline {

    @Override
    public boolean partieInitialisee(String idPartie) {
        return Dao.partieInitialisee(idPartie);
    }

    @Override
    public void inscription(String nomJoueur, String mdp) {
        Dao.inscription(nomJoueur,mdp);
    }
    @Override
    public boolean seConnecter(String nomJoueur, String mdp) {
        return Dao.seConnecter(nomJoueur,mdp);
    }

    @Override
    public void creerPartie(String id, String nomJoueur) throws PartiePleineException {
        Dao.creerPartie(id,nomJoueur);
    }

    @Override
    public Collection<Partie> getLesParties() {
        return Dao.getLesParties();
    }

    @Override
    public String getEtatPartie(String id) {
        return Dao.getEtatPartie(id);
    }

    @Override
    public Collection<Partie> getLesPartiesSuspendues() {
        return Dao.getLesPartiesSuspendues();
    }

    @Override
    public boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException {
        return Dao.suspendreLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean quitterLaPartie(String idPartie, String nomJoueur) {
        return Dao.quitterLaPartie(idPartie,nomJoueur);
    }

    @Override
    public boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException {
        return Dao.reprendreUnePartie(idPartie,nomJoueur);
    }

    @Override
    public boolean peutQuitterLaPartie(String idPartie) {
        return Dao.peutQuitterLaPartie(idPartie);
    }

    @Override
    public boolean createurPartie(String idPartie, String nomJoueur) {
        return Dao.createurPartie(idPartie, nomJoueur);
    }
}