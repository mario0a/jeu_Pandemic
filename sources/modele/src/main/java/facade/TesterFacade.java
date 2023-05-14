package facade;

import dao.Dao;

public class TesterFacade {

    private static Dao dao = new Dao();
    public static void main(String[] args) {

        dao.inscription("Paul","Lauriche");

        dao.seConnecter("pom","Lauriche");
       /* try {
            Dao.creerPartie("1","Paul");
            Dao.partieInitialisee("1");
            String action;
        } catch (PartiePleineException e) {
            throw new RuntimeException("Vous ne pouvez pas créer une partie");
        }*/
    }


}
