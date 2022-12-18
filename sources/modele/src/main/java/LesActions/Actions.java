package LesActions;

import exceptions.*;
import facade.JeuDeCartes;
import modele.*;
import modele.interfaces.ICartes;

import java.util.List;
import java.util.stream.Collectors;

public class Actions implements IAction{
    @Override
    public void traiterMaladie(Joueur joueur, CouleursMaladie couleurMaladie) {
        if(joueur.getPartie().getPartie().getLesRemedesActif().contains(couleurMaladie)) {
            joueur.getPosition().getCube().replace(couleurMaladie, 0);
        }else{
            joueur.getPosition().getCube().replace(couleurMaladie,joueur.getPosition().getCube().get(couleurMaladie)-1);
        }
    }

    @Override
    public void construireStationRecherche(Joueur joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        if(joueur.getPartie().getPartie().getLesStationsDeRecherche().size()>5) throw new NombreMaxCentreRechercheAtteintException();
        if(joueur.getCartes_en_main().contains(joueur.getPosition())) throw new AbsenceCarteJoueurException();
        if(joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheDejaExistantException();
        joueur.getPosition().setaUnCentreDeRecherche(true);
        joueur.getCartes_en_main().remove(joueur.getPosition());
        ICartes uneCarte = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(joueur.getPosition().getNomVille())).collect(Collectors.toList()).get(0);
        joueur.getPartie().getPartie().getDefausse_cartesJoueur().add(uneCarte);
    }

    @Override
    public void deplacerStationRecherche(Joueur joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException {
        if(joueur.getPosition().equals(ville)) throw new VilleIdentiqueException();
        if(joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheDejaExistantException();
        if(!ville.getaUnCentreDeRecherche()) throw new CentreRechercheInexistantException();
        ville.setaUnCentreDeRecherche(false);
        joueur.getPosition().setaUnCentreDeRecherche(true);
    }

    @Override
    public void decouvrirRemede(Joueur joueur) throws CentreRechercheInexistantException {
        if(!joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheInexistantException();

        List<ICartes> cartesMainJoueur = joueur.getCartes_en_main().stream().filter(carte -> carte.informations().equals(joueur.getPosition().getNomVille())).collect(Collectors.toList());
        int jaune = 0,rouge = 0,bleu = 0, noir = 0;
        for(ICartes carte : cartesMainJoueur){
            for(Ville ville : joueur.getPartie().getPartie().getLesVilles()){
                if(ville.getNomVille()==carte.informations()){
                    if(ville.getMaladie().equals(CouleursMaladie.JAUNE)) {jaune++;}
                    if(ville.getMaladie().equals(CouleursMaladie.ROUGE)) {rouge++;}
                    if(ville.getMaladie().equals(CouleursMaladie.BLEU)) {bleu++;}
                    if(ville.getMaladie().equals(CouleursMaladie.NOIR)) {noir++;}
                }
            }
        }
        if(joueur.getTypeRole().equals(TypeRole.SCIENTIFIQUE)){
            if(jaune==4){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_JAUNE);}
            if(rouge==4){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_ROUGE);}
            if(bleu==4){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_BLEU);}
            if(noir==4){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_NOIR);}
        }else{
            if(jaune==5){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_JAUNE);}
            if(rouge==5){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_ROUGE);}
            if(bleu==5){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_BLEU);}
            if(noir==5){joueur.getPartie().getPartie().getLesRemedesActif().add(TypeRemede.REMEDE_NOIR);}
        }
    }

    @Override
    public void piocherCarte(Joueur joueur, List<CartesJoueur> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        if(cartesJoueurList.size()<2) throw new CartesJoueurInsuffisantes(); // activer fin de partie
        if(joueur.getCartes_en_main().size()>5) throw new NombreCarteDepasseException();// ajouter l'option défosser carte
        joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
        joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
    }

    @Override
    public void echangerCarte(Joueur joueurDonneur, Joueur joueurReceveur, CartesJoueur carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions {
        if(!joueurDonneur.getCartes_en_main().contains(carte)) throw new AbsenceCarteJoueurException();
        if(!joueurDonneur.getPosition().equals(joueurReceveur.getPosition())) throw new PositionJoueursDifferenteExceptions();
        if(joueurReceveur.getCartes_en_main().size()==7) throw new NombreCarteDepasseException();
        joueurReceveur.getCartes_en_main().add(carte);
    }


}
