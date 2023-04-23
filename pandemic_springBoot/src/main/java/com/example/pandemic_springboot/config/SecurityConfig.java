package com.example.pandemic_springboot.config;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokens jwtTokens;

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokens))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokens))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/pandemic/authenticate").permitAll()
                .antMatchers(HttpMethod.POST,"/pandemic/inscription").permitAll()
                .antMatchers(HttpMethod.POST,"/pandemic/creerPartie").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/rejoindrePartie").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/initialiserPartie/{id}").hasRole("JOUEUR")
                .antMatchers(HttpMethod.GET,"/pandemic/joueur/{nomJoueur}").hasRole("JOUEUR")
                .antMatchers(HttpMethod.GET,"/pandemic/lesParties").hasRole("JOUEUR")
                .antMatchers(HttpMethod.GET,"/pandemic/etatPartie/{id}").hasRole("JOUEUR")
                .antMatchers(HttpMethod.PUT,"/pandemic/lesPartiesSuspendues").hasRole("JOUEUR")
                .antMatchers(HttpMethod.GET,"/pandemic/suspendrePartie").hasRole("JOUEUR")
                .antMatchers(HttpMethod.PUT,"/pandemic/quitterPartie").hasRole("JOUEUR")
                .antMatchers(HttpMethod.GET,"/pandemic/lesJoueurs").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/traiterMaladie").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/construireStationRecherche").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/decouvrirRemede").hasRole("JOUEUR")
                .antMatchers(HttpMethod.POST,"/pandemic/deplacerStationRecherche").hasRole("JOUEUR")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    public SecretKey getSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
