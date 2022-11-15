package modele;

import exceptions.PartiePleinExecption;
import exceptions.partieDejaTermineException;
import exceptions.partieInexistantException;

public interface FacadePandemic {
    void accederUnepartie(String idPartie, String pseudo) throws partieDejaTermineException, partieInexistantException, PartiePleinExecption;;
}
