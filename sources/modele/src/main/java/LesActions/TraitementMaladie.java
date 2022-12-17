package LesActions;

import modele.CouleursMaladie;
import modele.Joueur;

public class TraitementMaladie implements ITraitementMaladie{
    /*à revoir*/
    @Override
    public void traiterMaladie(Joueur joueur, CouleursMaladie couleurMaladie) {
        if(joueur.getPartie().getPartie().getLesRemedesActif().contains(couleurMaladie)) {
            joueur.getPosition().getCube().replace(couleurMaladie, 0);
        }else{
            joueur.getPosition().getCube().replace(couleurMaladie,joueur.getPosition().getCube().get(couleurMaladie)-1);
        }

    }
}

