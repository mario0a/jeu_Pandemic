package facade;

import dao.Dao;
import exceptions.PartieNonRepriseException;
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

    }

    @Override
    public boolean seConnecter(String nomJoueur, String mdp) {
        return Dao.seConnecter(nomJoueur,mdp);
    }

    @Override
    public void creerPartie(String id, String nomJoueur) throws PartiePleineException {

    }

    @Override
    public Collection<Partie> getLesParties() {
        return null;
    }

    @Override
    public String getEtatPartie(String id) {
        return null;
    }

    @Override
    public Collection<Partie> getLesPartiesSuspendues() {
        return null;
    }

    @Override
    public boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException {
        return false;
    }

    @Override
    public boolean quitterLaPartie(String idPartie, String nomJoueur) {
        return false;
    }

    @Override
    public boolean peutQuitterLaPartie(String idPartie) {
        return false;
    }

    @Override
    public boolean createurPartie(String idPartie, String nomJoueur) {
        return false;
    }

    @Override
    public void piocherCarte(ICartes carte) {

    }

    @Override
    public void seDeplacer() {

    }

    @Override
    public void contruireUneStationDeRcherche() {

    }

    @Override
    public void traiterUneMaladie() {

    }

    @Override
    public void partagerDesConnaissances() {

    }

    @Override
    public void trouvrerRemede() {

    }

    @Override
    public boolean carte_en_main_par_rapport_a_position(Ville ville) {
        return false;
    }

    @Override
    public void diminuerActions() {

    }

    @Override
    public boolean carte_en_main() {
        return false;
    }

    @Override
    public void diminuer_carte_en_main() {

    }

    @Override
    public void distribution(String idPartie) {

    }

    @Override
    public void partieCommence(String idPartie) {

    }

    @Override
    public void accederPartie(String idPartie, String pseudo) {

    }

    @Override
    public void accederUnePartie(String idPartie, String pseudo) {

    }

    @Override
    public boolean connexion(String pseudo, String mdp) {
        return false;
    }

    @Override
    public boolean reAccederAuJeu(String idPartie, String pseudo) {
        return false;
    }

    @Override
    public Collection<Plateau> getLesPartiesSuspendues(String idPArtie, String pseudo) {
        return null;
    }
}