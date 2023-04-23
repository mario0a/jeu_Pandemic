package com.example.pandemic_springboot.controller;


import com.example.pandemic_springboot.config.JwtTokens;
import com.example.pandemic_springboot.dto.ActionDto;
import com.example.pandemic_springboot.dto.JoueurDto;
import com.example.pandemic_springboot.dto.PartieDto;
import exceptions.*;
import facade.IFacadePandemicOnline;
import modele.Joueur;
import modele.Partie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerJoueurTest {

    @InjectMocks
    private ControllerJoueur controllerJoueur;

    @Mock
    private IFacadePandemicOnline iFacadePandemicOnline;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtTokens jwtUtil;

    @Test
    public void testInscriptionOk() {
        JoueurDto joueurDto = new JoueurDto("nomDuJoueur","motDePasse");

        Mockito.when(iFacadePandemicOnline.inscription(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("mdpEncodé");

        boolean resultat = controllerJoueur.inscription(joueurDto);

        Mockito.verify(iFacadePandemicOnline).inscription("nomDuJoueur", "mdpEncodé");

        assertTrue(resultat);
    }

    @Test
    public void testInscriptionKo() {
        JoueurDto joueurDto = new JoueurDto("nomDuJoueur","motDePasse");
        Mockito.when(iFacadePandemicOnline.inscription(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("mdpEncodé");

        boolean resultat = controllerJoueur.inscription(joueurDto);

        Mockito.verify(iFacadePandemicOnline).inscription("nomDuJoueur", "mdpEncodé");
        assertFalse(resultat);
    }

    @Test
    public void testCreerPartieAvecSucces() throws ActionNotAutorizedException, PartiePleineException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("JohnDoe");

        when(iFacadePandemicOnline.creerPartie(1L, "JohnDoe")).thenReturn(new Partie());

        ResponseEntity<Partie> response = controllerJoueur.creerPartie(partieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(iFacadePandemicOnline, times(1)).creerPartie(1L, "JohnDoe");
    }

    @Test
    public void testCreerPartie_Conflict() throws ActionNotAutorizedException, PartiePleineException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("John");

        when(iFacadePandemicOnline.creerPartie(eq(1L), eq("John"))).thenReturn(null);

        ResponseEntity<Partie> response = controllerJoueur.creerPartie(partieDto);

        verify(iFacadePandemicOnline).creerPartie(eq(1L), eq("John"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test(expected = ActionNotAutorizedException.class)
    public void testCreerPartie_ActionNotAutorizedException() throws ActionNotAutorizedException, PartiePleineException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("John");

        when(iFacadePandemicOnline.creerPartie(eq(1L), eq("John"))).thenThrow(new ActionNotAutorizedException());

        controllerJoueur.creerPartie(partieDto);
    }
    @Test(expected = PartiePleineException.class)
    public void testCreerPartie_PartiePleineException() throws ActionNotAutorizedException, PartiePleineException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("John");

        when(iFacadePandemicOnline.creerPartie(eq(1L), eq("John"))).thenThrow(new PartiePleineException());

        controllerJoueur.creerPartie(partieDto);
    }


    @Test
    public void testRejoindrePartie_Success() {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("John");

        Partie partie = new Partie();
        when(iFacadePandemicOnline.rejoindrePartie(eq(1L), eq("John"))).thenReturn(partie);

        ResponseEntity<Partie> response = controllerJoueur.rejoindrePartie(partieDto);

        verify(iFacadePandemicOnline).rejoindrePartie(eq(1L), eq("John"));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(partie, response.getBody());
    }

    @Test
    public void testRejoindrePartie_Conflict() {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("John");

        when(iFacadePandemicOnline.rejoindrePartie(eq(1L), eq("John"))).thenReturn(null);

        ResponseEntity<Partie> response = controllerJoueur.rejoindrePartie(partieDto);

        verify(iFacadePandemicOnline).rejoindrePartie(eq(1L), eq("John"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testInitialiserPartie_Success() throws ActionNotAutorizedException {
        Long idPartie = 1L;
        when(iFacadePandemicOnline.partieInitialisee(eq(idPartie))).thenReturn(true);

        ResponseEntity<Boolean> response = controllerJoueur.initialiserPartie(idPartie);

        verify(iFacadePandemicOnline).partieInitialisee(eq(idPartie));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void testInitialiserPartie_Conflict() throws ActionNotAutorizedException {
        Long idPartie = 1L;
        when(iFacadePandemicOnline.partieInitialisee(eq(idPartie))).thenReturn(false);

        ResponseEntity<Boolean> response = controllerJoueur.initialiserPartie(idPartie);

        verify(iFacadePandemicOnline).partieInitialisee(eq(idPartie));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody());
    }
    @Test(expected = RuntimeException.class)
    public void testInitialiserPartie_RuntimeException() throws ActionNotAutorizedException {
        Long idPartie = 1L;

        Mockito.when(iFacadePandemicOnline.partieInitialisee(Mockito.anyLong()))
                .thenThrow(new ActionNotAutorizedException());

        controllerJoueur.initialiserPartie(idPartie);
    }

    @Test
    public void testGetLesParties_OK() {
        Collection<Partie> parties = new ArrayList<>();
        parties.add(new Partie());
        parties.add(new Partie());

        when(iFacadePandemicOnline.getLesParties())
                .thenReturn(parties);

        Collection<Partie> result = controllerJoueur.getLesParties();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetLesParties_KO() {
        when(iFacadePandemicOnline.getLesParties())
                .thenReturn(null);

        Collection<Partie> result = controllerJoueur.getLesParties();

        assertNull(result);
    }

    @Test
    public void testGetEtatPartie_OK() {
        Long idPartie = 1L;
        String etatPartie = "EN_COURS";

        when(iFacadePandemicOnline.getEtatPartie(idPartie))
                .thenReturn(etatPartie);

        String result = controllerJoueur.getEtatPartie(idPartie);

        assertNotNull(result);
        assertEquals(etatPartie, result);
    }

    @Test
    public void testGetEtatPartie_KO() {
        Long idPartie = 1L;

        when(iFacadePandemicOnline.getEtatPartie(idPartie))
                .thenReturn(null);

        String result = controllerJoueur.getEtatPartie(idPartie);

        assertNull(result);
    }

    @Test
    public void testSuspendrePartie_OK() throws PartieNonRepriseException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("Joueur1");

        when(iFacadePandemicOnline.suspendreLaPartie(partieDto.getId(), partieDto.getNomJoueur()))
                .thenReturn(true);

        boolean result = controllerJoueur.suspendrePartie(partieDto);

        assertTrue(result);
    }

    @Test(expected = PartieNonRepriseException.class)
    public void testSuspendrePartie_KO() throws PartieNonRepriseException {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("Joueur1");

        when(iFacadePandemicOnline.suspendreLaPartie(partieDto.getId(), partieDto.getNomJoueur()))
                .thenThrow(PartieNonRepriseException.class);

        controllerJoueur.suspendrePartie(partieDto);
    }

    @Test
    public void testGetLesPartiesSuspendues_OK() {
        List<Partie> partiesSuspendues = Arrays.asList(new Partie(), new Partie());

        when(iFacadePandemicOnline.getLesPartiesSuspendues())
                .thenReturn(partiesSuspendues);

        Collection<Partie> result = controllerJoueur.getLesPartiesSuspendues();

        assertNotNull(result);
        assertEquals(partiesSuspendues.size(), result.size());
        assertTrue(result.containsAll(partiesSuspendues));
    }

    @Test
    public void testGetLesPartiesSuspendues_KO() {
        when(iFacadePandemicOnline.getLesPartiesSuspendues())
                .thenReturn(null);

        Collection<Partie> result = controllerJoueur.getLesPartiesSuspendues();

        assertNull(result);
    }

    @Test
    public void testQuitterLaPartie_OK() {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("Joueur1");

        when(iFacadePandemicOnline.quitterLaPartie(partieDto.getId(), partieDto.getNomJoueur()))
                .thenReturn(true);

        boolean result = controllerJoueur.quitterLaPartie(partieDto);

        assertTrue(result);
    }

    @Test
    public void testQuitterLaPartie_KO() {
        PartieDto partieDto = new PartieDto();
        partieDto.setId(1L);
        partieDto.setNomJoueur("Joueur1");

        when(iFacadePandemicOnline.quitterLaPartie(partieDto.getId(), partieDto.getNomJoueur()))
                .thenReturn(false);

        boolean result = controllerJoueur.quitterLaPartie(partieDto);

        assertFalse(result);
    }

    @Test
    public void testGetLesJoueurs_OK() {
        Collection<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Joueur1"));
        joueurs.add(new Joueur("Joueur2"));

        when(iFacadePandemicOnline.getLesJoueurs())
                .thenReturn(joueurs);

        Collection<Joueur> result = controllerJoueur.getLesJoueurs();

        assertEquals(joueurs, result);
    }

    @Test
    public void testGetLesJoueurs_KO() {
        when(iFacadePandemicOnline.getLesJoueurs())
                .thenReturn(null);

        Collection<Joueur> result = controllerJoueur.getLesJoueurs();

        assertNull(result);
    }

    @Test
    public void testTraiterMaladieSuccess() {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Joueur1");
        actionDto.setElement("Element1");

        doNothing().when(iFacadePandemicOnline)
                .traiterMaladie(actionDto.getIdPartie(), actionDto.getNomJoueur(), actionDto.getElement());

        controllerJoueur.traiterMaladie(actionDto);

        verify(iFacadePandemicOnline, times(1))
                .traiterMaladie(actionDto.getIdPartie(), actionDto.getNomJoueur(), actionDto.getElement());
    }

    @Test(expected = Exception.class)
    public void testTraiterMaladieFailure() {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Joueur1");
        actionDto.setElement("Element1");

        doThrow(new Exception("Erreur")).when(iFacadePandemicOnline)
                .traiterMaladie(actionDto.getIdPartie(), actionDto.getNomJoueur(), actionDto.getElement());

        controllerJoueur.traiterMaladie(actionDto);

        verify(iFacadePandemicOnline, times(1))
                .traiterMaladie(actionDto.getIdPartie(), actionDto.getNomJoueur(), actionDto.getElement());
    }
    @Test
    public void testConstruireStationRecherche_Ok() throws Exception {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Alice");

        doNothing().when(iFacadePandemicOnline).construireStationRecherche(actionDto.getIdPartie(), actionDto.getNomJoueur());
        controllerJoueur.construireStationRecherche(actionDto);

        verify(iFacadePandemicOnline, times(1)).construireStationRecherche(actionDto.getIdPartie(), actionDto.getNomJoueur());
    }

    @Test(expected = CentreRechercheDejaExistantException.class)
    public void testConstruireStationRecherche_Ko() throws Exception {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Alice");

        doThrow(new CentreRechercheDejaExistantException()).when(iFacadePandemicOnline).construireStationRecherche(actionDto.getIdPartie(), actionDto.getNomJoueur());
        controllerJoueur.construireStationRecherche(actionDto);
    }

    @Test
    public void testDecouvrirRemede_Ok() throws Exception {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Alice");

        doNothing().when(iFacadePandemicOnline).decouvrirRemede(actionDto.getIdPartie(), actionDto.getNomJoueur());
        controllerJoueur.decouvrirRemede(actionDto);

        verify(iFacadePandemicOnline, times(1)).decouvrirRemede(actionDto.getIdPartie(), actionDto.getNomJoueur());
    }

    @Test(expected = CentreRechercheInexistantException.class)
    public void testDecouvrirRemede_Ko() throws Exception {
        ActionDto actionDto = new ActionDto();
        actionDto.setIdPartie(1L);
        actionDto.setNomJoueur("Alice");

        doThrow(new CentreRechercheInexistantException()).when(iFacadePandemicOnline).decouvrirRemede(actionDto.getIdPartie(), actionDto.getNomJoueur());
        controllerJoueur.decouvrirRemede(actionDto);

    }


}
