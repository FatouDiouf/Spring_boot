package com.devweb.filerouge.controller;


import com.devweb.filerouge.model.*;
import com.devweb.filerouge.repository.CompteRepository;
import com.devweb.filerouge.repository.PartenaireRepository;
import com.devweb.filerouge.repository.UserRepository;
import com.devweb.filerouge.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin
@RequestMapping(value = "/partenaire")
public class PartenaireController {
    @Autowired
    private PartenaireRepository partenaireRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CompteRepository compteRepository;

    //@GetMapping(value = "/liste")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // List<Partenaire> listep(){
       // return partenaireRepository.findAll();
    //}

    @PostMapping(value = "/adduser", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addUser (@RequestBody(required = false) Partajout p){
        User u=userDetailsService.getUserconnecte();
        User user = new User();
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(p.getProfil());//l id sera envoyé grace au value du select
        roles.add(role);
        user.setRoles(roles);
        user.setEmail(p.getEmail());
        user.setName(p.getName());
        user.setPassword(encoder.encode(p.getPassword()));
        user.setUsername(p.getUsername());
        user.setAdresse(p.getAdresse());
        user.setCompte("NULL");
        user.setStatut(p.getStatut());
        user.setTelephone(p.getTelephone());
        user.setPartenaire(u.getPartenaire());
        userRepository.save(user);

        return "Utilisateur ajouté!!!";
    }

    @PostMapping(value = "/updatecompte", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updatecompte (@RequestBody(required = false) Partajout p){
        User u=userDetailsService.getUserconnecte();
        //Compte compte= compteRepository.findCompteByNumcompte(p.getNumcompte()).orElseThrow();
        User user = userRepository.findByUsername(p.getUsername()).orElseThrow();
        user.setCompte(p.getCompte());
        userRepository.save(user);
        return "Compte modifié!!!";
    }

    @GetMapping(value = "/listeUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> liste(){
        return userRepository.findAll();
    }
}
