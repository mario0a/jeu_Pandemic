package LesActions;

import exceptions.AucunCentreDeRechercheDansLaVilleArriveeException;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import exceptions.PasDeCentreDeRechercheVillePositionJoueurException;
import modele.Joueur;
import modele.Partie1Joueur;
import modele.Ville;

import java.util.stream.Collectors;

/*permet s'il y a plusieurs centres de recherches sur le plateau, de se déplacer de l'un de ces centres à un autre*/
public class DeplacementNavette implements IDeplacements {
    @Override
    public void operationDeplacement(Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException {
        if (!partie1Joueur.getPosition().aUnCentreDeRecherche() || !choix.aUnCentreDeRecherche()) throw new PasCentreRechercheException();
        partie1Joueur.setPosition(choix);
    }
}
