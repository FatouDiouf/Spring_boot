package com.devweb.filerouge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.devweb.filerouge.config.JwtTokenUtil;
import com.devweb.filerouge.model.JwtRequest;
import com.devweb.filerouge.model.JwtResponse;
import com.devweb.filerouge.model.User;
import com.devweb.filerouge.repository.UserRepository;
import com.devweb.filerouge.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        User user  = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
                () -> new Exception("user introuvable")
        );
        if(user.getStatut().equals("bloqué")){
            throw new Exception("vous etes bloqué");
        }
        if( user.getPartenaire()!= null && user.getPartenaire().getStatut().equals("bloqué") ){
            throw new Exception("votre partenaire bloqué");
        }
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public @ResponseBody String createLoginToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
       userDetails.getAuthorities().forEach(u ->{
            System.out.println(u.getAuthority());
        });
        return token;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/roles", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public List<String> getUserRoles(@RequestParam("username") String username) throws Exception {

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return userDetails.getAuthorities().stream()
                .map(u->((GrantedAuthority) u)
                        .getAuthority()).collect(Collectors.toList());
    }
}
