package LesActions;

import exceptions.AbsenceCarteJoueurException;
import exceptions.NombreCarteDepasseException;
import exceptions.PositionJoueursDifferenteExceptions;
import modele.CartesJoueur;
import modele.Joueur;

public class PartageConnaissancesRecevoir implements IPartageConnaissancesRecevoir{
    @Override
    public void recevoirCarte(Joueur joueurDonneur, Joueur joueurReceveur, CartesJoueur carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions {
        if(!joueurDonneur.getCartes_en_main().contains(carte)) throw new AbsenceCarteJoueurException();
        if(!joueurDonneur.getPosition().equals(joueurReceveur.getPosition())) throw new PositionJoueursDifferenteExceptions();
        if(joueurReceveur.getCartes_en_main().size()==7) throw new NombreCarteDepasseException();
        joueurReceveur.getCartes_en_main().add(carte);
        joueurDonneur.getCartes_en_main().remove(carte);
    }
}
