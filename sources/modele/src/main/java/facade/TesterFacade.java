package facade;

import dao.Dao;
import exceptions.PartiePleineException;

public class TesterFacade {
    public static void main(String[] args) {
        System.out.println(Dao.getDb());
        Dao.inscription("Paul","Lauriche");
        System.out.println(Dao.getDb().getCollection("joueurs").find().first());
        Dao.seConnecter("pom","Lauriche");
       /* try {
            Dao.creerPartie("1","Paul");
            Dao.partieInitialisee("1");
            String action;
        } catch (PartiePleineException e) {
            throw new RuntimeException("Vous ne pouvez pas créer une partie");
        }*/
    }


}
