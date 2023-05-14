package controller;

import LesActions.*;
import dao.Dao;
import dtos.actions.*;
import exceptions.*;
import modele.*;

import java.util.List;

public class ModelController {
    private final Dao dao = new Dao();
    private final IAction mesActions = new Actions();
    private IDeplacements mesDeplacements;

    public Partie traiterMaladieControleur(TraiterMaladieDto traiterMaladieDto) throws ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(traiterMaladieDto.getIdPartie());

        if(peutPasJouer(partie, traiterMaladieDto.getNomJoueur())) throw new PasTourJoueurException();

        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(traiterMaladieDto.getNomJoueur());

        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();

        CouleursMaladie couleursMaladie = CouleursMaladie.valueOf(traiterMaladieDto.getCouleurMaladie());
        mesActions.traiterMaladie(partie,partieJoueur,couleursMaladie);

        partieJoueur.diminuerActions();

        return dao.traiterMaladie(partie);
    }


    public Partie construireStationRechercheControleur(NomJoueurDTO nomJoueurDTO) throws NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(nomJoueurDTO.getIdPartie());
        if(peutPasJouer(partie, nomJoueurDTO.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(nomJoueurDTO.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        mesActions.construireStationRecherche(partie, partieJoueur);
        partieJoueur.diminuerActions();
        return dao.construireStationRecherche(partie);
    }

    public Partie deplacerStationRechercheControleur(VilleCarteDto villeCarteDto) throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(villeCarteDto.getIdPartie());
        if(peutPasJouer(partie, villeCarteDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(villeCarteDto.getNomJoueur());
        Ville ville = (Ville) partieJoueur.getVillesPartie().stream().filter(ville1 -> ville1.getNomVille().equals(villeCarteDto.getNomVilleCarte()));
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        mesActions.deplacerStationRecherche(partieJoueur, ville);
        partieJoueur.diminuerActions();
        return dao.deplacerStationRecherche(partie);
    }

    public Partie decouvrirRemedeControleur(NomJoueurDTO nomJoueurDTO) throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(nomJoueurDTO.getIdPartie());
        if(peutPasJouer(partie, nomJoueurDTO.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(nomJoueurDTO.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        mesActions.decouvrirRemede(partie,partieJoueur);
        partieJoueur.diminuerActions();
        return dao.decouvrirRemede(partie);
    }

    public Partie echangerCarteControleur(EchangerCarteDto echangerCarteDto) throws NombreCarteDepasseException, CarteVilleDifferentePositionJoueur, PositionJoueursDifferenteExceptions, AbsenceCarteJoueurException, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(echangerCarteDto.getIdPartie());
        if(peutPasJouer(partie, echangerCarteDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueurDonneur = partie.getPartieJoueurByNomJoueur(echangerCarteDto.getNomJoueur());
        Partie1Joueur partieJoueurReceveur = partie.getPartieJoueurByNomJoueur(echangerCarteDto.getReceveurNomJoueur());
        final Carte carte = partie.getCarteByNomCarte(echangerCarteDto.getNomCarte());
        if(peutPasFaireAction(partieJoueurDonneur)) throw new ActionJoueurFiniException();
        mesActions.echangerCarte(partieJoueurDonneur, partieJoueurReceveur, carte);
        partieJoueurDonneur.diminuerActions();
        return dao.echangerCarte(partie);
    }

    public Partie piocherCarteControleur(NomJoueurDTO nomJoueurDTO) throws NombreCarteDepasseException, CartesJoueurInsuffisantes, ActionJoueurFiniException, PasTourJoueurException {
        Partie partie = dao.getPartie(nomJoueurDTO.getIdPartie());
        if(peutPasJouer(partie, nomJoueurDTO.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(nomJoueurDTO.getNomJoueur());
        List<Carte> cartesJoueur = partie.getPlateau().getCartesJoueur();
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        new Actions().piocherCarte(partieJoueur, cartesJoueur);
        partieJoueur.diminuerActions();
        return dao.piocherCarte(partie);
    }

    public Partie passerTourControleur(NomJoueurDTO nomJoueurDTO) throws PasTourJoueurException {
        Partie partie = dao.getPartie(nomJoueurDTO.getIdPartie());
        if(peutPasJouer(partie, nomJoueurDTO.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(nomJoueurDTO.getNomJoueur());
        partieJoueur.setNombreAction(4);
        partie.getTourJoueur().suivant();
        return dao.passerTour(partie);
    }


    public Partie seDeplacerVolDirectControleur(DeplacementDto deplacementDto) throws ActionJoueurFiniException, PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        Partie partie = dao.getPartie(deplacementDto.getIdPartie());
        if(peutPasJouer(partie, deplacementDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(deplacementDto.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        if (partie.getPlateau().getLesVilles().stream().noneMatch(ville -> ville.getNomVille().equals(deplacementDto.getNomVille()))) {
            throw new VilleInexistanteException();
        }
        Ville ville = partie.getPlateau().getLesVilles().stream().filter(villeFilter -> villeFilter.getNomVille().equals(deplacementDto.getNomVille())).toList().get(0);
        mesDeplacements = new DeplacementVolDirect();
        mesDeplacements.operationDeplacement(partie,partieJoueur,ville);
        partieJoueur.diminuerActions();
        return dao.seDeplacer(partie);
    }

    public Partie seDeplacerAvecVoitureControleur(DeplacementDto deplacementDto) throws ActionJoueurFiniException, PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        Partie partie = dao.getPartie(deplacementDto.getIdPartie());
        if(peutPasJouer(partie, deplacementDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(deplacementDto.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        if (partie.getPlateau().getLesVilles().stream().noneMatch(ville -> ville.getNomVille().equals(deplacementDto.getNomVille()))) {
            throw new VilleInexistanteException();
        }
        Ville ville = partie.getPlateau().getLesVilles().stream().filter(villeFilter -> villeFilter.getNomVille().equals(deplacementDto.getNomVille())).toList().get(0);
        mesDeplacements = new DeplacementAvecVoiture();
        mesDeplacements.operationDeplacement(partie,partieJoueur,ville);
        partieJoueur.diminuerActions();
        return dao.seDeplacer(partie);
    }

    public Partie seDeplacerNavetteControleur(DeplacementDto deplacementDto) throws ActionJoueurFiniException, PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        Partie partie = dao.getPartie(deplacementDto.getIdPartie());
        if(peutPasJouer(partie, deplacementDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(deplacementDto.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        if (partie.getPlateau().getLesVilles().stream().noneMatch(ville -> ville.getNomVille().equals(deplacementDto.getNomVille()))) {
            throw new VilleInexistanteException();
        }
        Ville ville = partie.getPlateau().getLesVilles().stream().filter(villeFilter -> villeFilter.getNomVille().equals(deplacementDto.getNomVille())).toList().get(0);
        mesDeplacements = new DeplacementNavette();
        mesDeplacements.operationDeplacement(partie,partieJoueur,ville);
        partieJoueur.diminuerActions();
        return dao.seDeplacer(partie);
    }

    public Partie seDeplacerVolCharterControleur(DeplacementDto deplacementDto) throws ActionJoueurFiniException, PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, VilleNonVoisineException, AbsenceCarteJoueurException {
        Partie partie = dao.getPartie(deplacementDto.getIdPartie());
        if(peutPasJouer(partie, deplacementDto.getNomJoueur())) throw new PasTourJoueurException();
        Partie1Joueur partieJoueur = partie.getPartieJoueurByNomJoueur(deplacementDto.getNomJoueur());
        if(peutPasFaireAction(partieJoueur)) throw new ActionJoueurFiniException();
        if (partie.getPlateau().getLesVilles().stream().noneMatch(ville -> ville.getNomVille().equals(deplacementDto.getNomVille()))) {
            throw new VilleInexistanteException();
        }
        Ville ville = partie.getPlateau().getLesVilles().stream().filter(villeFilter -> villeFilter.getNomVille().equals(deplacementDto.getNomVille())).toList().get(0);
        mesDeplacements = new DeplacementVolCharter();
        mesDeplacements.operationDeplacement(partie,partieJoueur,ville);
        partieJoueur.diminuerActions();
        return dao.seDeplacer(partie);
    }

    private boolean peutPasJouer(Partie partie, String nomJoueur){
        return !partie.getTourJoueur().aQuiLeTour().equals(nomJoueur);
    }

    private boolean peutPasFaireAction(Partie1Joueur partieJoueur){
        return partieJoueur.getNombreAction() <= 0;

    }
}
