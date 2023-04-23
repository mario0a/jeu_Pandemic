package com.example.pandemic_springboot.config;

import dao.Dao;
import modele.Joueur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsService implements UserDetailsService {
    private Dao dao= new Dao();

    @Override
    public UserDetails loadUserByUsername(String nomJoueur) throws UsernameNotFoundException {
        Joueur joueur =dao.findJoueurByName(nomJoueur);
        if (joueur==null) {
            throw  new UsernameNotFoundException("User "+nomJoueur+" not found");
        }
        String[] roles= new String[joueur.getRoles().size()];
        UserDetails userDetails = User.builder()
                .username(joueur.getNomJoueur())
                .password(joueur.getMdp())
                .roles(roles)
                .build();

        return userDetails;
    }
}