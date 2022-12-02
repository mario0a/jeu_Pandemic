package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.Joueur;

public interface IDeplacements {
    public void faireAction(Joueur joueur) throws CarteArriveeInexistanteException;
}
