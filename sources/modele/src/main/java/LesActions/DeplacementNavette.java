package LesActions;

import exceptions.AucunCentreDeRechercheDansLaVilleArriveeException;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import exceptions.PasDeCentreDeRechercheVillePositionJoueurException;
import modele.Joueur;
import modele.Ville;

import java.util.stream.Collectors;

/*permet s'il y a plusieurs centres de recherches sur le plateau, de se déplacer de l'un de ces centres à un autre*/
public class DeplacementNavette implements IDeplacements {
    @Override
    public void operationDeplacement(Joueur joueur, Ville choix) throws PasCentreRechercheException {
        if (!joueur.getPosition().aUnCentreDeRecherche() || !choix.aUnCentreDeRecherche()) throw new PasCentreRechercheException();
        joueur.setPosition(choix);
    }
}
