package com.example.pandemic_springboot.controller;


import com.example.pandemic_springboot.dto.JoueurDto;
import facade.FacadePandemicOnline;
import facade.IFacadePandemicOnline;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/pandemic")
public class ControllerJoueur {
    private IFacadePandemicOnline iFacadePandemicOnline = new FacadePandemicOnline();

    @PostMapping(value = "/inscription"/*,headers = "Content-Type=application/json",consumes = "application/json",produces = "application/json"*/)
    public boolean inscription(@RequestBody JoueurDto joueurDto) {
        return  this.iFacadePandemicOnline.inscription(joueurDto.getNomJoueur(), joueurDto.getMdp());
    }




}
