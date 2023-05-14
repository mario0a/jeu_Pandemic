package com.example.pandemic_springboot.controller;

import com.example.pandemic_springboot.config.CustomUserDetailsService;
import com.example.pandemic_springboot.config.JwtTokens;
import dtos.JoueurDto;
import dtos.actions.NomJoueurDTO;
import dtos.actions.TraiterMaladieDto;
import dtos.actions.VilleCarteDto;
import dtos.jeu.PlateauInitialDto;
import dtos.parties.PartieDto;
import dtos.parties.PartieIdDto;
import exceptions.*;
import facade.IFacadePandemicOnline;
import modele.EtatPartie;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerJoueurTest {

    @InjectMocks
    private ControllerJoueur controllerJoueur;

    @Mock
    private IFacadePandemicOnline facadePandemicOnline;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtTokens jwtUtil;

    @Test
    public void testInscriptionOk() {
        JoueurDto joueurDto = new JoueurDto();
        joueurDto.setNomJoueur("testUser");
        joueurDto.setMdp("password");

        when(facadePandemicOnline.inscription(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        boolean result = controllerJoueur.inscription(joueurDto);

        assertTrue(result);
        verify(facadePandemicOnline).inscription("testUser", "encodedPassword");
    }

    @Test
    public void testInscriptionKo() {
        JoueurDto joueurDto = new JoueurDto();
        joueurDto.setNomJoueur("testUser");
        joueurDto.setMdp("password");

        when(facadePandemicOnline.inscription(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        boolean result = controllerJoueur.inscription(joueurDto);

        assertFalse(result);
        verify(facadePandemicOnline).inscription("testUser", "encodedPassword");
    }

    @Test
    public void testLoginOk() {
        JoueurDto joueurDto = new JoueurDto();
        joueurDto.setNomJoueur("lauriche");
        joueurDto.setMdp("123");

        CustomUserDetailsService customUserDetailsService = mock(CustomUserDetailsService.class);
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        User user = mock(User.class);

        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtUtil.genereToken(any(User.class))).thenReturn("accessToken");

        ResponseEntity responseEntity = controllerJoueur.login(joueurDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Authorization"));
    }

    @Test
    public void testLoginKo() {
        JoueurDto joueurDto = new JoueurDto();
        joueurDto.setNomJoueur("lauriche");
        joueurDto.setMdp("123");

        CustomUserDetailsService customUserDetailsService = mock(CustomUserDetailsService.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseEntity responseEntity = controllerJoueur.login(joueurDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }


    @Test
    public void testCreerPartieOk() throws ActionNotAutorizedException, PartiePleineException {
        // Given
        PartieDto partieDto = new PartieDto();
        partieDto.setNomJoueur("John");
        when(facadePandemicOnline.creerPartie(partieDto.getNomJoueur())).thenReturn(partieDto);

        // When
        ResponseEntity<PartieDto> response = controllerJoueur.creerPartie(partieDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(partieDto, response.getBody());
        verify(facadePandemicOnline, times(1)).creerPartie(partieDto.getNomJoueur());
    }


    @Test
    public void testRejoindrePartieOk() {

        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(String.valueOf(1L));
        partieDto.setNomJoueur("John");
        when(facadePandemicOnline.rejoindrePartie(partieDto.getIdPartie(), partieDto.getNomJoueur())).thenReturn(partieDto);
        ResponseEntity<PartieDto> response = controllerJoueur.rejoindrePartie(partieDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(partieDto, response.getBody());
        verify(facadePandemicOnline, times(1)).rejoindrePartie(partieDto.getIdPartie(), partieDto.getNomJoueur());
    }

    @Test
    public void testRejoindrePartieKo() {
        // Given
        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(String.valueOf(1L));
        partieDto.setNomJoueur("John");
        when(facadePandemicOnline.rejoindrePartie(partieDto.getIdPartie(), partieDto.getNomJoueur())).thenReturn(null);

        // When
        ResponseEntity<PartieDto> response = controllerJoueur.rejoindrePartie(partieDto);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
        verify(facadePandemicOnline, times(1)).rejoindrePartie(partieDto.getIdPartie(), partieDto.getNomJoueur());
    }


    @Test
    public void rejoindrePartieOk() {
        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(String.valueOf(1L));
        partieDto.setNomJoueur("John");
        Mockito.when(facadePandemicOnline.rejoindrePartie(String.valueOf(1L), "John")).thenReturn(partieDto);
        ResponseEntity<PartieDto> response = controllerJoueur.rejoindrePartie(partieDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(partieDto, response.getBody());
    }

    @Test
    public void rejoindrePartieConflict() {
        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(String.valueOf(1L));
        partieDto.setNomJoueur("John");
        Mockito.when(facadePandemicOnline.rejoindrePartie(String.valueOf(1L), "John")).thenReturn(null);
        ResponseEntity<PartieDto> response = controllerJoueur.rejoindrePartie(partieDto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void initialiserPartieOk() throws ActionNotAutorizedException {
        PlateauInitialDto plateauInitialDto = new PlateauInitialDto();
        PartieIdDto partieIdDto = new PartieIdDto();
        partieIdDto.setIdPartie(String.valueOf(1L));
        Mockito.when(facadePandemicOnline.partieInitialisee(String.valueOf(1L))).thenReturn(plateauInitialDto);
        PlateauInitialDto response = controllerJoueur.initialiserPartie(partieIdDto);
        assertEquals(plateauInitialDto, response);
    }

    @Test(expected = ActionNotAutorizedException.class)
    public void initialiserPartieActionNotAuthorized() throws ActionNotAutorizedException {
        PartieIdDto partieIdDto = new PartieIdDto();
        partieIdDto.setIdPartie(String.valueOf(1L));
        Mockito.doThrow(new ActionNotAutorizedException("")).when(facadePandemicOnline).partieInitialisee(String.valueOf(1L));
        controllerJoueur.initialiserPartie(partieIdDto);
    }


    @Test
    public void getLesPartiesOk() {
        Collection<PartieDto> parties = new ArrayList<>();
        parties.add(new PartieDto());
        parties.add(new PartieDto());
        Mockito.when(facadePandemicOnline.getLesParties()).thenReturn(parties);
        Collection<PartieDto> response = controllerJoueur.getLesParties();
        assertEquals(parties, response);
    }

    @Test
    public void getLesPartiesNull() {
        Mockito.when(facadePandemicOnline.getLesParties()).thenReturn(null);
        Collection<PartieDto> response = controllerJoueur.getLesParties();
        assertNull(response);
    }

    @Test
    public void testGetPartie() throws Exception {
        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(String.valueOf(1));
        partieDto.setNomJoueur("Joueur1");
        partieDto.setLesJoueurs(new ArrayList<String>());
        partieDto.setEtatPartie(String.valueOf(EtatPartie.EN_COURS));

        when(facadePandemicOnline.getPartie(String.valueOf(1))).thenReturn(partieDto);

        PartieIdDto partieIdDto = new PartieIdDto();
        partieIdDto.setIdPartie(String.valueOf(1));

        ResponseEntity<PartieDto> responseEntity = controllerJoueur.getPartie(partieIdDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isEqualTo(partieDto);
    }


    @Test
    public void testGetEtatPartieOk() {
        String etatPartie = "EN_COURS";
        when(facadePandemicOnline.getEtatPartie(anyString())).thenReturn(etatPartie);
        String response = controllerJoueur.getEtatPartie("1");
        assertThat(response).isEqualTo(etatPartie);
    }

    @Test
    public void testQuitterLaPartieOk() {
        String idPartie = new ObjectId().toString();
        String nomJoueur = "Joueur 1";
        when(facadePandemicOnline.quitterLaPartie(idPartie, nomJoueur)).thenReturn(true);
        PartieDto partieDto = new PartieDto();
        partieDto.setIdPartie(idPartie);
        partieDto.setNomJoueur(nomJoueur);
        boolean response = controllerJoueur.quitterLaPartie(partieDto);

        assertTrue(response);
    }



    @Test(expected = ActionNotAutorizedException.class)
    public void testTraitementMaladieKo() throws ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        TraiterMaladieDto traiterMaladieDto = new TraiterMaladieDto();
        doThrow(new ActionNotAutorizedException("Action non autorisée")).when(facadePandemicOnline).traiterMaladie(traiterMaladieDto);
        controllerJoueur.traiterMaladie(traiterMaladieDto);
    }

    @Test
    public void decouvrirRemede_OK() throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        NomJoueurDTO nomJoueurDTO = new NomJoueurDTO();
        nomJoueurDTO.setNomJoueur("Alice");
        controllerJoueur.decouvrirRemede(nomJoueurDTO);
        verify(facadePandemicOnline, times(1)).decouvrirRemede(nomJoueurDTO);
    }

    @Test(expected = CentreRechercheInexistantException.class)
    public void decouvrirRemede_KO_CentreRechercheInexistantException() throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        NomJoueurDTO nomJoueurDTO = new NomJoueurDTO();
        nomJoueurDTO.setNomJoueur("Bob");
        doThrow(new CentreRechercheInexistantException()).when(facadePandemicOnline).decouvrirRemede(nomJoueurDTO);

        controllerJoueur.decouvrirRemede(nomJoueurDTO);

        fail("L'exception CentreRechercheInexistantException aurait dû être levée.");
    }

    @Test(expected = ActionNotAutorizedException.class)
    public void decouvrirRemede_KO_ActionNotAutorizedException() throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        NomJoueurDTO nomJoueurDTO = new NomJoueurDTO();
        nomJoueurDTO.setNomJoueur("Charlie");
        doThrow(new ActionNotAutorizedException("Action non autorisée")).when(facadePandemicOnline).decouvrirRemede(nomJoueurDTO);
        controllerJoueur.decouvrirRemede(nomJoueurDTO);
        fail("L'exception ActionNotAutorizedException aurait dû être levée.");
    }

    @Test
    public void deplacerStationRecherche_OK() throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        VilleCarteDto villeDto = new VilleCarteDto();
        villeDto.setNomVilleCarte("Atlanta");
        controllerJoueur.deplacerStationRecherche(villeDto);
        verify(facadePandemicOnline, times(1)).deplacerStationRecherche(villeDto);
    }

    @Test(expected = VilleIdentiqueException.class)
    public void deplacerStationRecherche_KO_VilleIdentiqueException() throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        VilleCarteDto villeDto = new VilleCarteDto();
        villeDto.setNomVilleCarte("Atlanta");
        doThrow(new VilleIdentiqueException()).when(facadePandemicOnline).deplacerStationRecherche(villeDto);
        controllerJoueur.deplacerStationRecherche(villeDto);
        fail("L'exception VilleIdentiqueException aurait dû être levée.");
    }

    @Test(expected = CentreRechercheInexistantException.class)
    public void deplacerStationRecherche_KO_CentreRechercheInexistantException() throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        VilleCarteDto villeDto = new VilleCarteDto();
        villeDto.setNomVilleCarte("Atlanta");
        doThrow(new CentreRechercheInexistantException()).when(facadePandemicOnline).deplacerStationRecherche(villeDto);
        controllerJoueur.deplacerStationRecherche(villeDto);
        fail("L'exception CentreRechercheInexistantException aurait dû être levée.");
    }

    @Test(expected = CentreRechercheDejaExistantException.class)
    public void deplacerStationRecherche_KO_CentreRechercheDejaExistantException() throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        VilleCarteDto villeDto = new VilleCarteDto();
        villeDto.setNomVilleCarte("Atlanta");
        doThrow(new CentreRechercheDejaExistantException()).when(facadePandemicOnline).deplacerStationRecherche(villeDto);
        controllerJoueur.deplacerStationRecherche(villeDto);
        fail("L'exception CentreRechercheDejaExistantException aurait dû être levée.");
    }
}

