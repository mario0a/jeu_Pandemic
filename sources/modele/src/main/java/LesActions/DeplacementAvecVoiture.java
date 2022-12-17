package LesActions;

//pertmet de se déplacer d'une ville à une autre à condition qu'il y ait un trait qui réunit ces 2 destinations

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Ville;

import java.util.List;

public class DeplacementAvecVoiture implements IDeplacements{

    @Override
    public void operationDeplacement(Joueur joueur, Ville choix) throws PasCentreRechercheException {

        List<Ville> villesVoisines=joueur.getPosition().getVillesLiees();
        if(villesVoisines.contains(choix)){
            joueur.setPosition(choix);
        }else{
            System.out.println("Le joueur n'a pas le droit de se déplacer en voiture, veuillez choisir un autre moyen de déplacement");
        }
    }
}
