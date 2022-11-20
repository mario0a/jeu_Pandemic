package facade;

public class Partie1Joueur {

    //retire une carte des cartes que le joueur a en main
    public void diminuer_carte_en_main (CartesJoueur carte){
        cartes_en_main.removeIf(cartesJoueur -> cartesJoueur == carte);
    }

    /* regarde la carte qu'on a en main par rapport à la ville dans laquelle on se trouve*/
    //mettre dans la classe PartieJoueur
    public boolean carte_en_main_par_rapport_a_position(Ville ville){
        for (CartesJoueur carteSuivante:cartes_en_main) {
            if (carteSuivante.getNomVille().equals(ville.getNomVille())){
                return true;
            }
        }
        return false;
    }

    public void diminuerActions(){nombre_action --;}

    /*regarde si le joueur a une carte en main*/
    //à mettre dans la classe partieJoueur
    public boolean carte_en_main(CartesJoueur carte){
        for (CartesJoueur carteSuivante:cartes_en_main) {
            if (carteSuivante.equals(carte)){
                return true;
            }
        }
        return false;
    }
}
