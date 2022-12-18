package facade;

import modele.Plateau;
import modele.Ville;
import modele.interfaces.ICartes;

import java.util.Collection;

public class FacadePandemicOnline implements IFacadePandemicOnline {
    @Override
    public ICartes piocherCarte() {
        return null;
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
    public void trouverRemede() {

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
    public void inscription(String pseudo, String mdp) {

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

    @Override
    public boolean suspendreLaPartie(String idPArtie, String pseudo) {
        return false;
    }

    @Override
    public boolean quitterLaPartie(String idPartie, String pseudo) {
        return false;
    }

    @Override
    public boolean reprendreUnePartie(String idPartie, String pseudo) {
        return false;
    }
}
