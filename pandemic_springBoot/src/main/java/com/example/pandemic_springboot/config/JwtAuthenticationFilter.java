package com.example.pandemic_springboot.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtTokens jwtTokens;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens) throws SQLException {
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("pandemic/authenticate");
        this.jwtTokens = jwtTokens;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws ServletException {
        UserDetails user = (UserDetails)authResult.getPrincipal();
        String token = jwtTokens.genereToken(user);
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    }

}
