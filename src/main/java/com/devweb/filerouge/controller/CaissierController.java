package com.devweb.filerouge.controller;

import com.devweb.filerouge.model.Compte;
import com.devweb.filerouge.model.Depot;
import com.devweb.filerouge.model.Partajout;
import com.devweb.filerouge.repository.CompteRepository;
import com.devweb.filerouge.repository.DepotRepository;
import com.devweb.filerouge.repository.PartenaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(value = "/caissier")
public class CaissierController {
    @Autowired
    DepotRepository depotRepository;
    @Autowired
    CompteRepository compteRepository;
    @PostMapping(value = "/depot", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_CAISSIER')")
    public String doDepot (@RequestBody(required = false)Partajout p){
       Compte compte= compteRepository.findCompteByNumcompte(p.getNumcompte()).orElseThrow();

       compte.setSolde(compte.getSolde() + p.getMontant());
        compteRepository.saveAndFlush(compte);
        Depot d = new Depot();
        Date actuelle = new Date();
       // DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formater= new SimpleDateFormat("dd-MM-yyyy");
        String dat = formater.format(actuelle);
        d.setDatedepot(dat);
        d.setMontant(p.getMontant());
        d.setCompte(compte);
        depotRepository.save(d);
        return "dépot fait";
    }
}
