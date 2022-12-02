package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.Joueur;

public class ContexteDeplacement {
    private IDeplacements lesDeplacements;

    public ContexteDeplacement(IDeplacements lesDeplacements) {
        this.lesDeplacements = lesDeplacements;
    }

    public void setLesDeplacements(IDeplacements lesDeplacements) {
        this.lesDeplacements = lesDeplacements;
    }

    public IDeplacements getLesDeplacements() {
        return lesDeplacements;
    }

    //délègue le déplacement d'un pion à la stratégie lesDeplacements qui fera à la bonne stratégie concrète de déplacement
    public void executerDeplacement(Joueur joueur){
        try {
            lesDeplacements.executeDeplacement(joueur); // lève une exception, à traiter!!
        } catch (CarteArriveeInexistanteException e) {
            throw new RuntimeException(e);
        }
    }
}
