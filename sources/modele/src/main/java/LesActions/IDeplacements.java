package LesActions;

import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import modele.Joueur;
import modele.Partie1Joueur;
import modele.Ville;

public interface IDeplacements {
    void operationDeplacement(Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException;
}
