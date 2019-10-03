package com.devweb.filerouge.controller;


import com.devweb.filerouge.model.Partenaire;
import com.devweb.filerouge.repository.PartenaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@CrossOrigin
@RequestMapping(value = "/employe")
public class PartenaireController {
    @Autowired
    private PartenaireRepository partenaireRepository;

    @GetMapping(value = "/liste")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Partenaire> liste(){
        return partenaireRepository.findAll();
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Partenaire add (@RequestBody(required = false) Partenaire p){
        return partenaireRepository.save(p);
    }
}
