package LesActions;

import exceptions.CarteArriveeInexistanteException;
import facade.Joueur;

public interface IDeplacements {
    public void faireAction(Joueur joueur) throws CarteArriveeInexistanteException;
}
