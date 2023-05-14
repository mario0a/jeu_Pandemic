package com.example.pandemic_springboot.controller;

import com.example.pandemic_springboot.config.CustomUserDetailsService;
import com.example.pandemic_springboot.config.JwtTokens;

import dtos.JoueurDto;
import dtos.actions.*;
import dtos.jeu.PlateauDto;
import dtos.jeu.PlateauInitialDto;
import dtos.parties.PartieDto;
import dtos.parties.PartieIdDto;
import dtos.parties.PartiesSuspenduesDto;
import exceptions.*;
import facade.FacadePandemicOnline;
import facade.IFacadePandemicOnline;


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
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping(path = "/pandemic")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerJoueur {
    private final IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();
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

                return ResponseEntity.ok().headers(headers).body(jsonObject.toString());
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
    public ResponseEntity<PartieDto> creerPartie(@RequestBody PartieDto partieDto) throws ActionNotAutorizedException, PartiePleineException {
        PartieDto partiedto = this.iFacadePandemicOnline.creerPartie(partieDto.getNomJoueur());
        return ResponseEntity.status(HttpStatus.CREATED).body(partiedto);
    }

    @PostMapping("/rejoindrePartie")
    public ResponseEntity<PartieDto> rejoindrePartie(@RequestBody PartieDto partieDto){
        PartieDto partie=this.iFacadePandemicOnline.rejoindrePartie(partieDto.getIdPartie(), partieDto.getNomJoueur());
        if(partie!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(partie);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }



    // Fini
    @PostMapping("/initialiserPartie")
    public PlateauInitialDto initialiserPartie(@RequestBody PartieIdDto partieIdDto) throws ActionNotAutorizedException {
        return this.iFacadePandemicOnline.partieInitialisee(partieIdDto.getIdPartie());
    }

    @PostMapping("/actualiserPlateau")
    public PlateauDto actualiserPlateau(@RequestBody PartieIdDto partieIdDto) {
        return this.iFacadePandemicOnline.actualiserPlateau(partieIdDto.getIdPartie());
    }

    @RequestMapping(value = "/joueurLogged", method = RequestMethod.GET)
    @ResponseBody
    public JoueurDto currentUserName(Authentication authentication) {
        return iFacadePandemicOnline.findJoueurByName(authentication.getName());
    }

    @GetMapping("/joueur/{nomJoueur}")
    public ResponseEntity<JoueurDto> findJoueurByName(@PathVariable String nomJoueur){
        JoueurDto joueurDto= this.iFacadePandemicOnline.findJoueurByName(nomJoueur);
        if(joueurDto!=null){
            return new ResponseEntity<>(joueurDto,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/lesParties")
    public Collection<PartieDto> getLesParties(){
        return this.iFacadePandemicOnline.getLesParties();
    }

    @PostMapping("/maPartie")
    public ResponseEntity<PartieDto> getPartie(@RequestBody PartieIdDto partieIdDto){
        return new ResponseEntity<>(this.iFacadePandemicOnline.getPartie(partieIdDto.getIdPartie()),HttpStatus.ACCEPTED);
    }

    @PostMapping("/etatPartie")
    public String getEtatPartie(@RequestBody String idPartie){
        return this.iFacadePandemicOnline.getEtatPartie(idPartie);
    }

    @PutMapping("/suspendrePartie")
    public boolean suspendrePartie(@RequestBody PartieDto partieDto) throws PartieNonRepriseException {
        return this.iFacadePandemicOnline.suspendreLaPartie(partieDto.getIdPartie(), partieDto.getNomJoueur());
    }

    @GetMapping("/lesPartiesSuspendues")
    public Collection<PartiesSuspenduesDto> getLesPartiesSuspendues(){
        return this.iFacadePandemicOnline.getLesPartiesSuspendues();
    }

    @GetMapping("/lesPartiesARejoindre")
    public Collection<PartieDto> getLesPartiesARejoindre(){
        return this.iFacadePandemicOnline.getLesPartiesARejoindre();
    }

    @PostMapping("/mesPartiesSuspendues")
    public Collection<PartiesSuspenduesDto> getMesPartiesSuspendues(@RequestBody PartieDto partieDto){
        return this.iFacadePandemicOnline.getMesPartiesSuspendues(partieDto.getNomJoueur());
    }

    @PutMapping("/quitterPartie")
    public boolean quitterLaPartie(@RequestBody PartieDto partieDto){
        return this.iFacadePandemicOnline.quitterLaPartie(partieDto.getIdPartie(),partieDto.getNomJoueur());
    }


    @DeleteMapping("/supprimerLesParties")
    public boolean supprimerLesParties(){
        return this.iFacadePandemicOnline.supprimerLesParties();
    }

    @GetMapping("/lesJoueurs")
    public Collection<JoueurDto> getLesJoueurs(){
        return this.iFacadePandemicOnline.getLesJoueurs();
    }

    @DeleteMapping("/supprimerLesJoueurs")
    public boolean supprimerLesJoueurs(){
        return this.iFacadePandemicOnline.supprimerLesJoueurs();
    }



    /// Actions
    @PostMapping("/traiterMaladie")
    public void traiterMaladie(@RequestBody TraiterMaladieDto traiterMaladieDto) throws ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        this.iFacadePandemicOnline.traiterMaladie(traiterMaladieDto);
    }

    @PostMapping("/construireStationRecherche")
    public void construireStationRecherche(@RequestBody NomJoueurDTO nomJoueurDTO) throws NombreMaxCentreRechercheAtteintException, AbsenceCarteJoueurException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        this.iFacadePandemicOnline.construireStationRecherche(nomJoueurDTO);
    }

    @PostMapping("/decouvrirRemede")
    public void decouvrirRemede(@RequestBody NomJoueurDTO nomJoueurDTO) throws CentreRechercheInexistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        this.iFacadePandemicOnline.decouvrirRemede(nomJoueurDTO);
    }

    @PostMapping("/deplacerStationRecherche")
    public void deplacerStationRecherche(@RequestBody VilleCarteDto villeDto) throws VilleIdentiqueException, CentreRechercheInexistantException, CentreRechercheDejaExistantException, ActionNotAutorizedException, ActionJoueurFiniException, PasTourJoueurException {
        this.iFacadePandemicOnline.deplacerStationRecherche(villeDto);
    }

    @PostMapping("/echangerCarte")
    public void echangerCarte(@RequestBody EchangerCarteDto echangerCarteDto) throws ActionNotAutorizedException, NombreCarteDepasseException, CarteVilleDifferentePositionJoueur, PositionJoueursDifferenteExceptions, AbsenceCarteJoueurException, ActionJoueurFiniException, PasTourJoueurException {
        this.iFacadePandemicOnline.echangerCarte(echangerCarteDto);
    }

    @PostMapping("/piocherCarte")
    public PlateauDto piocherCarte(@RequestBody NomJoueurDTO nomJoueurDTO) throws ActionNotAutorizedException, NombreCarteDepasseException, CartesJoueurInsuffisantes, ActionJoueurFiniException, PasTourJoueurException {
        return this.iFacadePandemicOnline.piocherCarte(nomJoueurDTO);
    }

    @PostMapping("/passerTour")
    public void passerTour(@RequestBody NomJoueurDTO nomJoueurDTO) throws ActionNotAutorizedException, PasTourJoueurException {
        this.iFacadePandemicOnline.passerTour(nomJoueurDTO);
    }

    @PostMapping("/deplacementAvecVoiture")
    public void deplacementAvecVoiture(@RequestBody DeplacementDto deplacementDto) throws PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.iFacadePandemicOnline.seDeplacerAvecVoiture(deplacementDto);
    }
    @PostMapping("/deplacementVolCharter")
    public void deplacementVolCharter(@RequestBody DeplacementDto deplacementDto) throws PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.iFacadePandemicOnline.seDeplacerVolCharter(deplacementDto);
    }
    @PostMapping("/deplacementVolDirect")
    public void deplacementVolDirect(@RequestBody DeplacementDto deplacementDto) throws PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.iFacadePandemicOnline.seDeplacerVolDirect(deplacementDto);
    }
    @PostMapping("/deplacementNavette")
    public void deplacementNavette(@RequestBody DeplacementDto deplacementDto) throws PasTourJoueurException, VilleInexistanteException, PasCentreRechercheException, ActionJoueurFiniException, VilleNonVoisineException, AbsenceCarteJoueurException {
        this.iFacadePandemicOnline.seDeplacerAvecVoiture(deplacementDto);
    }

}
