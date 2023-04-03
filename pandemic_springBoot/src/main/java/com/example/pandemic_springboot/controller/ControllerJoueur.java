package com.example.pandemic_springboot.controller;


import com.example.pandemic_springboot.dto.JoueurDto;
import com.example.pandemic_springboot.dto.PartieDto;
import exceptions.ActionNotAutorizedException;
import exceptions.PartieNonRepriseException;
import exceptions.PartiePleineException;
import facade.FacadePandemicOnline;
import facade.IFacadePandemicOnline;
import modele.Joueur;
import modele.Partie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/pandemic")
public class ControllerJoueur {
    private IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();

    @PostMapping(value = "/inscription"/*,headers = "Content-Type=application/json",consumes = "application/json",produces = "application/json"*/)
    public ResponseEntity<String> inscription(@RequestBody JoueurDto joueurDto, UriComponentsBuilder base) {
        boolean resultat =this.iFacadePandemicOnline.inscription(joueurDto.getNomJoueur(), joueurDto.getMdp());
        if(resultat){
            URI location = base.path("/pandemic/inscription/{nomJoueur}").buildAndExpand(joueurDto.getNomJoueur()).toUri();
            return ResponseEntity.created(location).body("Inscription réussie");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Inscription échouée");
    }

    @PostMapping("/creerPartie")
    public void creerPartie(@RequestBody PartieDto partieDto) throws ActionNotAutorizedException, PartiePleineException {
        this.iFacadePandemicOnline.creerPartie(partieDto.getId(), partieDto.getNomJoueur());
    }
    @GetMapping("/lesParties")
    public Collection<Partie> getLesParties() {
        return this.iFacadePandemicOnline.getLesParties();
    }

    @GetMapping("/etatPartie/{id}")
    public String getEtatPartie(@PathVariable Long id) {
        return this.iFacadePandemicOnline.getEtatPartie(id);
    }

    @PutMapping("/suspendrePartie")
    public boolean suspendrePartie(@RequestBody PartieDto partieDto) throws PartieNonRepriseException {
        return this.iFacadePandemicOnline.suspendreLaPartie(partieDto.getId(), partieDto.getNomJoueur());
    }

    @DeleteMapping("/supprimerLesParties")
    public boolean supprimerLesParties() {
        return this.iFacadePandemicOnline.supprimerLesParties();
    }

    @GetMapping("/lesJoueurs")
    public Collection<Joueur> getLesJoueurs() {
        return this.iFacadePandemicOnline.getLesJoueurs();
    }

    @DeleteMapping("/supprimerLesJoueurs")
    public boolean supprimerLesJoueurs() {
        return this.iFacadePandemicOnline.supprimerLesJoueurs();
    }
}
