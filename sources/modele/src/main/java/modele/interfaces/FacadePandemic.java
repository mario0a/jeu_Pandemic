package modele.interfaces;

import exceptions.PartieCompleteException;
import exceptions.PartieDejaTermineException;
import exceptions.PartieInexistantException;

public interface FacadePandemic {
    void accederUnepartie(String idPartie, String pseudo) throws PartieDejaTermineException, PartieInexistantException, PartieCompleteException;;
}
