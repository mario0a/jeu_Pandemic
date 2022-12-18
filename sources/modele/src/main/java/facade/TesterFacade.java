package facade;

import dao.Dao;

public class TesterFacade {
    public static void main(String[] args) {
        System.out.println(Dao.getDb());
        Dao.inscription("pom","Lauriche");
        System.out.println(Dao.getDb().getCollection("joueurs").find().first());
        Dao.seConnecter("pom","Lauriche");
    }


}
