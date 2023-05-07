package com.example.pandemic_springboot.controller;

import com.example.pandemic_springboot.config.CustomUserDetailsService;
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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping(path = "/pandemic")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerJoueur {
    private IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokens jwtUtil;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity login(@RequestBody JoueurDto joueurDto) {
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(joueurDto.getNomJoueur());
        try {
            if(passwordEncoder.matches(joueurDto.getMdp(), userDetails.getPassword())) {
                Authentication authentication = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(joueurDto.getNomJoueur(), joueurDto.getMdp())
                );
                User user = (User) authentication.getPrincipal();
                String accessToken = jwtUtil.genereToken(user);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + accessToken);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nomJoueur",joueurDto.getNomJoueur());

                return ResponseEntity.ok().headers(headers).body(jsonObject);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/inscription")
    public boolean inscription(@RequestBody JoueurDto joueurDto) {
        return  this.iFacadePandemicOnline.inscription(joueurDto.getNomJoueur(),passwordEncoder.encode(joueurDto.getMdp()));
    }
    @PostMapping("/creerPartie")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity creerPartie(@RequestBody PartieDto partieDto) throws ActionNotAutorizedException, PartiePleineException {
        this.iFacadePandemicOnline.creerPartie(partieDto.getNomJoueur());
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<Boolean> initialiserPartie(@PathVariable String idPartie){
        boolean res = false;
        try {
            res = this.iFacadePandemicOnline.partieInitialisee(idPartie);
        } catch (ActionNotAutorizedException e) {
            throw new RuntimeException(e);
        }
        return res ? ResponseEntity.status(HttpStatus.ACCEPTED).body(res) : ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @RequestMapping(value = "/joueurLogged", method = RequestMethod.GET)
    @ResponseBody
    public Joueur currentUserName(Authentication authentication) {
        return iFacadePandemicOnline.findJoueurByName(authentication.getName());
    }

    @GetMapping("/joueur/{nomJoueur}")
    public ResponseEntity<Joueur> findJoueurByName(@PathVariable String nomJoueur){
        Joueur joueur= this.iFacadePandemicOnline.findJoueurByName(nomJoueur);
        if(joueur!=null){
            return new ResponseEntity<>(joueur,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/lesParties")
    public Collection<Partie> getLesParties(){
        return this.iFacadePandemicOnline.getLesParties();
    }

    @GetMapping("/etatPartie/{id}")
    public String getEtatPartie(@PathVariable String idPartie){
        return this.iFacadePandemicOnline.getEtatPartie(idPartie);
    }

    @PutMapping("/suspendrePartie")
    public boolean suspendrePartie(@RequestBody PartieDto partieDto) throws PartieNonRepriseException {
        return this.iFacadePandemicOnline.suspendreLaPartie(partieDto.getId(), partieDto.getNomJoueur());
    }

    @GetMapping("/lesPartiesSuspendues")
    public Collection<Partie> getLesPartiesSuspendues(){
        return this.iFacadePandemicOnline.getLesPartiesSuspendues();
    }


    @GetMapping("/mesPartiesSuspendues")
    public Collection<Partie> getMesPartiesSuspendues(@RequestBody PartieDto partieDto){
        return this.iFacadePandemicOnline.getMesPartiesSuspendues(partieDto.getNomJoueur());
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
