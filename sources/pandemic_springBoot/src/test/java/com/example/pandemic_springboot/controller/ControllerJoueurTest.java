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

}

