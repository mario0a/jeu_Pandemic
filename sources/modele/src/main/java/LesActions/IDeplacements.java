package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.Joueur;

public interface IDeplacements {
    public void operationDeplacement(Joueur joueur) throws CarteArriveeInexistanteException;
}
