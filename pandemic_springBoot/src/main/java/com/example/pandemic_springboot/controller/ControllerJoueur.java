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
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pandemic")
public class ControllerJoueur {
    private IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();

    @PostMapping(value = "/inscription"/*,headers = "Content-Type=application/json",consumes = "application/json",produces = "application/json"*/)
    public boolean inscription(@RequestBody JoueurDto joueurDto) {
        return  this.iFacadePandemicOnline.inscription(joueurDto.getNomJoueur(), joueurDto.getMdp());
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
