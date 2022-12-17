package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.JeuDeCartes;
import modele.Joueur;
import modele.Plateau;

public class ConstruireStationDeRecherche {
    //private String villeAUneStationDeRecherche;
    public void construireStationDeRecherche(Joueur joueur, Plateau plateau) throws CarteArriveeInexistanteException {
        if (joueur.getCartes_en_main().stream().anyMatch(carte->carte.informations().equals(joueur.getPosition().getNomVille()))){
            if (JeuDeCartes.lesVilles().stream().anyMatch(v ->v.getNomVille().equals(joueur.getPosition().getNomVille()))){
                int index = plateau.getLesVilles().indexOf(joueur.getPosition());
                plateau.getLesVilles().get(index).setaUnCentreDeRecherche(true);
            }
        }
    }
}
