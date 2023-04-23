package com.example.pandemic_springboot.controller;


import com.example.pandemic_springboot.config.JwtTokens;
import com.example.pandemic_springboot.dto.ActionDto;
import com.example.pandemic_springboot.dto.JoueurDto;
import com.example.pandemic_springboot.dto.PartieDto;
import exceptions.*;
import facade.FacadePandemicOnline;
import facade.IFacadePandemicOnline;
import modele.Carte;
import modele.Joueur;
import modele.Partie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/pandemic")
public class ControllerJoueur {
    private IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokens jwtUtil;

    @PostMapping(value = "/inscription"/*,headers = "Content-Type=application/json",consumes = "application/json",produces = "application/json"*/)
    public boolean inscription(@RequestBody JoueurDto joueurDto) {
        return  this.iFacadePandemicOnline.inscription(joueurDto.getNomJoueur(),passwordEncoder.encode(joueurDto.getMdp()));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@RequestBody JoueurDto joueurDto) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(joueurDto.getNomJoueur(), joueurDto.getMdp()));
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.genereToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            JoueurDto response = new JoueurDto(user.getUsername());

            return ResponseEntity.ok().headers(headers).body(response.getNomJoueur());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/creerPartie")
    public ResponseEntity<Partie> creerPartie(@RequestBody PartieDto partieDto) throws ActionNotAutorizedException, PartiePleineException {
        Partie partie=this.iFacadePandemicOnline.creerPartie(partieDto.getId(), partieDto.getNomJoueur());
        if(partie!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(partie);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PostMapping("/rejoindrePartie")
    public ResponseEntity<Partie> rejoindrePartie(@RequestBody PartieDto partieDto){
        Partie partie=this.iFacadePandemicOnline.rejoindrePartie(partieDto.getId(), partieDto.getNomJoueur());
        if(partie!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(partie);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    // Fini
    @PostMapping("/initialiserPartie/{id}")
    public ResponseEntity<Boolean> initialiserPartie(@PathVariable Long id){
        Boolean res = null;
        try {
            res = this.iFacadePandemicOnline.partieInitialisee(id);
        } catch (ActionNotAutorizedException e) {
            throw new RuntimeException(e);
        }
        if(res==true){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }

    }

    @GetMapping("/joueur/{nomJoueur}")
    public Joueur findJoueurByName(@PathVariable String nomJoueur){
        return this.iFacadePandemicOnline.findJoueurByName(nomJoueur);
    }
    @GetMapping("/lesParties")
    public Collection<Partie> getLesParties(){
        return this.iFacadePandemicOnline.getLesParties();
    }

    @GetMapping("/etatPartie/{id}")
    public String getEtatPartie(@PathVariable Long id){
        return this.iFacadePandemicOnline.getEtatPartie(id);
    }

    @PutMapping("/suspendrePartie")
    public boolean suspendrePartie(@RequestBody PartieDto partieDto) throws PartieNonRepriseException {
        return this.iFacadePandemicOnline.suspendreLaPartie(partieDto.getId(), partieDto.getNomJoueur());
    }
    @GetMapping("/lesPartiesSuspendues")
    public Collection<Partie> getLesPartiesSuspendues(){
        return this.iFacadePandemicOnline.getLesPartiesSuspendues();
    }

    @PutMapping("/quitterPartie")
    public boolean quitterLaPartie(@RequestBody PartieDto partieDto){
        return this.iFacadePandemicOnline.quitterLaPartie(partieDto.getId(),partieDto.getNomJoueur());
    }


    @DeleteMapping("/supprimerLesParties")
    public boolean supprimerLesParties(){
        return this.iFacadePandemicOnline.supprimerLesParties();
    }

    @GetMapping("/lesJoueurs")
    public Collection<Joueur> getLesJoueurs(){
        return this.iFacadePandemicOnline.getLesJoueurs();
    }

    @DeleteMapping("/supprimerLesJoueurs")
    public boolean supprimerLesJoueurs(){
        return this.iFacadePandemicOnline.supprimerLesJoueurs();
    }



    /// Actions
    @PostMapping("/traiterMaladie")
    public void traiterMaladie(@RequestBody ActionDto actionDto){
        this.iFacadePandemicOnline.traiterMaladie(actionDto.getIdPartie(),actionDto.getNomJoueur(),actionDto.getElement());
    }


    // tests unitaires à faire
    @PostMapping("/construireStationRecherche")
    public void construireStationRecherche(@RequestBody ActionDto actionDto) throws NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException, CentreRechercheDejaExistantException {
        this.iFacadePandemicOnline.construireStationRecherche(actionDto.getIdPartie(),actionDto.getNomJoueur());
    }
    @PostMapping("/decouvrirRemede")
    public void decouvrirRemede(@RequestBody ActionDto actionDto) throws CentreRechercheInexistantException {
        this.iFacadePandemicOnline.decouvrirRemede(actionDto.getIdPartie(),actionDto.getNomJoueur());
    }
    @PostMapping("/deplacerStationRecherche")
    public void deplacerStationRecherche(@RequestBody ActionDto actionDto) throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException {
        this.iFacadePandemicOnline.deplacerStationRecherche(actionDto.getIdPartie(), actionDto.getNomJoueur(),actionDto.getElement());
    }
    // DTO adapté ?
    @PostMapping("/echangerCarte")
    public void echangerCarte(Long idPartie,String nomJoueurDonneur, String nomJoueurReceveur, Carte carte){
        System.out.println();
    }


    // à revoir
    // la pioche(List<CarteJoueur>) ? ne doit-elle pas être dans la partie ?
    @PostMapping("/piocherCarte")
    public void piocherCarte(Long idPartie,String nomJoueur, List<Carte> cartesJoueurList){
        System.out.println();
    }
}
