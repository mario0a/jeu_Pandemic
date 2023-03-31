package com.example.pandemic_springboot.dto;

public class PartieDto {
     Long id;
     String nomJoueur;
     String etatPartie;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getNomJoueur() {
          return nomJoueur;
     }

     public void setNomJoueur(String nomJoueur) {
          this.nomJoueur = nomJoueur;
     }

     public String getEtatPartie() {
          return etatPartie;
     }

     public void setEtatPartie(String etatPartie) {
          this.etatPartie = etatPartie;
     }
}
