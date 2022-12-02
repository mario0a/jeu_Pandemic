package LesActions;

import exceptions.CarteArriveeInexistanteException;
import modele.Joueur;

public class ConstruireStationDeRecherche implements IDeplacements{
    private String villeAUneStationDeRecherche;

    @Override
    public void faireAction(Joueur joueur) throws CarteArriveeInexistanteException {
        if (joueur.getCartes_en_main().stream().anyMatch(carte ->carte.informations().equals(joueur.getPosition().getNomVille()))){
            if (joueur.getPartie().getLesVilles().stream.filter(v ->v))
        }
    }
}
