package facade;

import LesActions.Actions;
import dtos.JoueurDto;
import dtos.actions.*;
import dtos.jeu.PlateauDto;
import dtos.jeu.PlateauInitialDto;
import dtos.parties.PartieDto;
import dtos.parties.PartiesSuspenduesDto;
import exceptions.*;
import modele.*;

import java.util.Collection;
import java.util.List;

//contient les différentes actions qu'on peut effectuer sur le plateau

public interface IFacadePandemicOnline {
 //contient les différentes actions qu'on peut effectuer sur le plateau

 boolean inscription(String nomJoueur, String mdp);
 JoueurDto findJoueurByName(String nomJoueur);

 boolean seConnecter(String nomJoueur, String mdp);

 PartieDto creerPartie(String nomJoueur) throws PartiePleineException,ActionNotAutorizedException;

 PlateauInitialDto partieInitialisee(String idPartie) throws ActionNotAutorizedException;

 PlateauDto actualiserPlateau(String idPartie);

 PartieDto rejoindrePartie(String idPartie, String nomJoueur);
 boolean supprimerLesParties();
 Collection<PartieDto> getLesParties();

 Collection<PartieDto> getLesPartiesARejoindre();
 Collection<JoueurDto> getLesJoueurs();
 boolean supprimerLesJoueurs();

 String getEtatPartie(String idPartie);

 Collection<PartiesSuspenduesDto> getLesPartiesSuspendues();
 Collection<PartiesSuspenduesDto> getMesPartiesSuspendues(String nomJoueur);

 boolean suspendreLaPartie(String idPartie, String nomJoueur) throws PartieNonRepriseException;

 boolean quitterLaPartie(String idPartie, String nomJoueur);

 boolean reprendreUnePartie(String idPartie, String nomJoueur) throws PartieNonSuspenduException;

 boolean peutQuitterLaPartie(String idPartie);

 boolean createurPartie(String idPartie, String nomJoueur);

 PlateauDto traiterMaladie(TraiterMaladieDto traiterMaladieDto) throws ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PlateauDto construireStationRecherche(NomJoueurDTO nomJoueurDTO) throws CentreRechercheDejaExistantException,
         NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PlateauDto deplacerStationRecherche(VilleCarteDto villeDto) throws CentreRechercheDejaExistantException, CentreRechercheInexistantException, VilleIdentiqueException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PlateauDto decouvrirRemede(NomJoueurDTO nomJoueurDTO) throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PlateauDto piocherCarte(NomJoueurDTO nomJoueurDTO) throws CartesJoueurInsuffisantes, NombreCarteDepasseException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PlateauDto echangerCarte(EchangerCarteDto echangerCarteDto) throws NombreCarteDepasseException, AbsenceCarteJoueurException, PositionJoueursDifferenteExceptions, CarteVilleDifferentePositionJoueur, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException;

 PartieDto getPartie(String idPartie);

 PlateauDto passerTour(NomJoueurDTO nomJoueurDTO) throws ActionNotAutorizedException, PasTourJoueurException;

 PlateauDto seDeplacerVolDirect(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException;

 PlateauDto seDeplacerAvecVoiture(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException;

 PlateauDto seDeplacerNavette(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException;

 PlateauDto seDeplacerVolCharter(DeplacementDto deplacementDto) throws VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, PasTourJoueurException, AbsenceCarteJoueurException;
}