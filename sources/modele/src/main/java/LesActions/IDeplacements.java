package LesActions;

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Ville;

public interface IDeplacements {
    void operationDeplacement(Joueur joueur, Ville choix) throws PasCentreRechercheException;
}
