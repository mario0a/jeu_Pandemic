package LesActions;

//pertmet de se déplacer d'une ville à une autre à condition qu'il y ait un trait qui réunit ces 2 destinations

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Partie;
import modele.Partie1Joueur;
import modele.Ville;

import java.util.List;

public class DeplacementAvecVoiture implements IDeplacements{


    @Override
    public void operationDeplacement(Partie partie, Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        List<String> villesVoisines=partie1Joueur.getPosition().getVillesLiees();
        if(villesVoisines.contains(choix.getNomVille())){
            partie1Joueur.setPosition(choix);
        }else{
            System.out.println("Le partie1Joueur n'a pas le droit de se déplacer en voiture, veuillez choisir un autre moyen de déplacement");
        }
    }
}
