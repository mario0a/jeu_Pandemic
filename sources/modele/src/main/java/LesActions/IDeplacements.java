package LesActions;

import exceptions.AbsenceCarteJoueurException;
import exceptions.CarteArriveeInexistanteException;
import exceptions.PasCentreRechercheException;
import exceptions.VilleNonVoisineException;
import modele.Joueur;
import modele.Partie;
import modele.Partie1Joueur;
import modele.Ville;

public interface IDeplacements {
    void operationDeplacement(Partie partie, Partie1Joueur partie1Joueur, Ville choix) throws PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException;
}
