package LesActions;

import exceptions.*;
import modele.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Actions implements IAction{
    @Override
    public void traiterMaladie(Partie partie,Partie1Joueur partie1Joueur, CouleursMaladie couleurMaladie) {
        // pas remède → -1 d'une couleur
        // remède trouvé => tous les cubes d'une couleur à retirer
        // si dernier cube d'une couleur est retiré → maladie éradiquée(action sur remède)
        // Medeçin : retire tous les cubes d'une couleur
        HashMap<CouleursMaladie,TypeRemede> correspondanceCouleur = new HashMap<>(Map.of(
                CouleursMaladie.BLEU,TypeRemede.REMEDE_BLEU,
                CouleursMaladie.NOIR,TypeRemede.REMEDE_NOIR,
                CouleursMaladie.JAUNE,TypeRemede.REMEDE_JAUNE,
                CouleursMaladie.ROUGE,TypeRemede.REMEDE_ROUGE
        ));
        if(partie.getPlateau().getLesRemedesActif().contains(correspondanceCouleur.get(couleurMaladie)) || partie1Joueur.getTypeRole().equals(TypeRole.MEDECIN) ){
            partie1Joueur.getPosition().getCube().replace(couleurMaladie, 0);
        }else if(!partie.getPlateau().getLesRemedesActif().contains(correspondanceCouleur.get(couleurMaladie))){
            partie1Joueur.getPosition().getCube().replace(couleurMaladie,partie1Joueur.getPosition().getCube().get(couleurMaladie)-1);
        }
    }

    //modification le 02/04/2023
    @Override
    public void construireStationRecherche(Partie partie,Partie1Joueur partie1Joueur) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException {
        if(partie.getPlateau().getLesStationsDeRecherche().size()>5) throw new NombreMaxCentreRechercheAtteintException();
        if(partie1Joueur.getCartes_en_main().stream().noneMatch(Carte -> Carte.getInformation().equals(partie1Joueur.getPosition().getNomVille()))) throw new AbsenceCarteJoueurException();
        if(partie1Joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheDejaExistantException();
        partie1Joueur.getPosition().setaUnCentreDeRecherche(true);
        Carte uneCarte = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.getInformation().equals(partie1Joueur.getPosition().getNomVille())).toList().get(0);
        if (!partie1Joueur.getTypeRole().equals(TypeRole.EXPERT_AUX_OPERATIONS)){
            partie1Joueur.getCartes_en_main().remove(uneCarte);
            partie.getPlateau().getDefausse_cartesJoueur().add(uneCarte);
        }

    }

    @Override
    public void deplacerStationRecherche(Partie1Joueur partie1Joueur, Ville ville) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException {
        if(partie1Joueur.getPosition().equals(ville)) throw new VilleIdentiqueException();
        if(partie1Joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheDejaExistantException();
        if(!ville.getaUnCentreDeRecherche()) throw new CentreRechercheInexistantException();
        ville.setaUnCentreDeRecherche(false);
        partie1Joueur.getPosition().setaUnCentreDeRecherche(true);
    }

    @Override
    public void decouvrirRemede(Partie partie,Partie1Joueur partie1Joueur) throws CentreRechercheInexistantException {
        if(!partie1Joueur.getPosition().getaUnCentreDeRecherche()) throw new CentreRechercheInexistantException();

        List<Carte> cartesMainJoueur = partie1Joueur.getCartes_en_main().stream().filter(carte -> carte.getInformation().equals(partie1Joueur.getPosition().getNomVille())).collect(Collectors.toList());
        int jaune = 0,rouge = 0,bleu = 0, noir = 0;
        for(Carte carte : cartesMainJoueur){
            for(Ville ville : partie.getPlateau().getLesVilles()){
                if(ville.getNomVille()==carte.getInformation()){
                    if(ville.getMaladie().equals(CouleursMaladie.JAUNE)) {jaune++;}
                    if(ville.getMaladie().equals(CouleursMaladie.ROUGE)) {rouge++;}
                    if(ville.getMaladie().equals(CouleursMaladie.BLEU)) {bleu++;}
                    if(ville.getMaladie().equals(CouleursMaladie.NOIR)) {noir++;}
                }
            }
        }
        if(partie1Joueur.getTypeRole().equals(TypeRole.SCIENTIFIQUE)){
            if(jaune==4){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_JAUNE);}
            if(rouge==4){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_ROUGE);}
            if(bleu==4){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_BLEU);}
            if(noir==4){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_NOIR);}
        }else{
            if(jaune==5){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_JAUNE);}
            if(rouge==5){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_ROUGE);}
            if(bleu==5){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_BLEU);}
            if(noir==5){partie.getPlateau().getLesRemedesActif().add(TypeRemede.REMEDE_NOIR);}
        }
    }

    @Override
    public void piocherCarte(Partie1Joueur partie1Joueur, List<Carte> cartesJoueurList) throws CartesJoueurInsuffisantes, NombreCarteDepasseException {
        if(cartesJoueurList.size()<2) throw new CartesJoueurInsuffisantes(); // activer fin de partie
        if(partie1Joueur.getCartes_en_main().size()>5) throw new NombreCarteDepasseException();// ajouter l'option défausser carte
        partie1Joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
        partie1Joueur.getCartes_en_main().add(cartesJoueurList.get(cartesJoueurList.size()-1));
        cartesJoueurList.remove(cartesJoueurList.size()-1);
    }

    @Override
    public void echangerCarte(Partie1Joueur joueurDonneur, Partie1Joueur joueurReceveur, Carte carte) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur {
        if(!joueurDonneur.getCartes_en_main().contains(carte)) throw new AbsenceCarteJoueurException();
        if(!joueurDonneur.getPosition().equals(joueurReceveur.getPosition())) throw new PositionJoueursDifferenteExceptions();
        if(joueurReceveur.getCartes_en_main().size()==7) throw new NombreCarteDepasseException();

        if(!joueurDonneur.getTypeRole().equals(TypeRole.CHERCHEUSE)) {
            joueurReceveur.getCartes_en_main().add(carte);
            joueurDonneur.getCartes_en_main().remove(carte);
        }else {
            if(!joueurDonneur.getPosition().equals(carte.getInformation()))throw new CarteVilleDifferentePositionJoueur();
            joueurReceveur.getCartes_en_main().add(carte);
            joueurDonneur.getCartes_en_main().remove(carte);
        }
    }


    @Override
    public void retireCubeAutomatiquement(Partie1Joueur partie1Joueur, Ville ville, CouleursMaladie couleursMaladie) throws ActionNotAutorizedException {
        // Medeçin : si maladie guérie, retirer tous les cubes correspondants et bloquer les nouveaux cubes d'une maladie guérie (dans une autre méthode)
        if(!partie1Joueur.getTypeRole().equals(TypeRole.MEDECIN)) throw new ActionNotAutorizedException();

    }

    // Cartes évènements

    // fini
    @Override
    public void subventionPublique(Partie partie,Partie1Joueur partie1Joueur, Ville ville) throws CentreRechercheDejaExistantException, NombreMaxCentreRechercheAtteintException {
        if(ville.getaUnCentreDeRecherche())  throw new CentreRechercheDejaExistantException();
        if(partie.getPlateau().getLesStationsDeRecherche().size()>5) throw new NombreMaxCentreRechercheAtteintException();
        ville.setaUnCentreDeRecherche(true);
        partie.getPlateau().getLesStationsDeRecherche().add(ville);
    }

    // fini
    @Override
    public void parUneNuitTranquille(Plateau plateau) {
        plateau.setParUneNuitTranquille(true);
    }

    // Fini
    @Override
    public void populationResiliente(Carte choix, Plateau plateau) throws  ActionNotAutorizedException {
        if(!plateau.getDefausse_carteDePropagation().contains(choix)) throw new ActionNotAutorizedException();
        if(plateau.getEtatEpidemie()!=EtatEpidemie.INFECTION ||plateau.getEtatEpidemie()!=EtatEpidemie.INTENSIFICATION ) throw new ActionNotAutorizedException();
        plateau.getDefausse_carteDePropagation().remove(choix);
    }


    //fini   le rôle du répartiteur est fait
    @Override
    public void deplacerUnPionQuelqconque(Partie1Joueur joueurDeplace,Partie1Joueur joueurADeplacer, Ville ville) throws ActionNotAutorizedException {
        if(joueurADeplacer.getAutorisationDeplacementPion()) throw  new ActionNotAutorizedException();
        if(!joueurDeplace.getTypeRole().equals(TypeRole.REPARTITEUR)) throw new ActionNotAutorizedException();
        joueurADeplacer.setPosition(ville);
        joueurADeplacer.setAutorisationDeplacementPion(false);
    }

    // a revoir
    @Override
    public void prevention(Plateau plateau) {
        List<Carte> cartesPropagations=plateau.getCartesPropagation();
        Queue<Carte> cartesPropagation;
        for (Carte carte:cartesPropagations) {
            System.out.println();// ???
        }
    }

}