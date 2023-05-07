package com.example.pandemic_springboot.dto;

import lombok.Data;

@Data
public class JoueurDto {
     String nomJoueur;
     String mdp;

     public JoueurDto(String nomJoueur, String mdp) {
          this.nomJoueur = nomJoueur;
          this.mdp = mdp;
     }

     public JoueurDto(String nomJoueur) {
          this.nomJoueur = nomJoueur;
     }
}