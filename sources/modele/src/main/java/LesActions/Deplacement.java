package LesActions;
//mettre en place le pattern stratégie pour gérer les déplacments
public interface Deplacement {
    public void executeData(); // permettra au context d'exécuter les différentes stratégies de déplacement
}
