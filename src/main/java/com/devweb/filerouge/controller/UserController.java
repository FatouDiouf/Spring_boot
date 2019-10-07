package com.devweb.filerouge.controller;


import com.devweb.filerouge.model.*;
import com.devweb.filerouge.repository.CompteRepository;
import com.devweb.filerouge.repository.PartenaireRepository;
import com.devweb.filerouge.repository.UserRepository;
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
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PartenaireRepository partenaireRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping(value = "/listepart")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public List<Partenaire> liste(){
        return partenaireRepository.findAll();
    }

    @GetMapping(value = "/listeuser")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public List<User> lister(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/userpart", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public String addAdmin (@RequestBody(required = false) Partajout p){

        Partenaire part = new Partenaire();
        part.setAdresse(p.getAdresse());
        part.setNinea(p.getNinea());
        part.setRaionsociale(p.getRaionsociale());
        part.setStatut(p.getStatut());
        partenaireRepository.save(part);

        Compte compte = new Compte();
        double x = (int)(Math.random()*((999999)+10));
        compte.setNumcompte(String.valueOf(x));
        compte.setSolde(0);
        compte.setPartenaire(part);
        compteRepository.save(compte);

        User u = new User();
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(p.getProfil());//l id sera envoyé grace au value du select
        roles.add(role);
        u.setRoles(roles);
        u.setEmail(p.getEmail());
        u.setName(p.getName());
        u.setPassword(encoder.encode(p.getPassword()));
        u.setUsername(p.getUsername());
        u.setAdresse(p.getAdresse());
        u.setCompte("");
        u.setStatut(p.getStatut());
        u.setTelephone(p.getTelephone());
        u.setPartenaire(part);
        userRepository.save(u);
        return "partenaire ajoute";
    }

    @PostMapping(value = "/caissier", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public String addCaissier (@RequestBody(required = false) Partajout p){

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
        userRepository.save(user);

        return "caissier ajouté";
    }

    @GetMapping(value = "/compte/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public String addCompte (@PathVariable("id") int id) {
     Partenaire part = new Partenaire();
     part=partenaireRepository.findById(id).orElseThrow();
        Compte compte = new Compte();
        double x = (int)(Math.random()*((999999)+10));
        compte.setNumcompte(String.valueOf(x));
        compte.setSolde(0);
        compte.setPartenaire(part);
        compteRepository.saveAndFlush(compte);
        return " Compte ajouté!!!!";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public String update(@PathVariable("id") int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
       if (user.getUsername().equals("toufa")){
          return "le Super administrateur ne peut pas être bloqué";
       }
        if(user.getStatut().equals("bloqué")){
            user.setStatut("débloqué");
        }else if(user.getStatut().equals("débloqué")){
            user.setStatut("bloqué");
        }
        userRepository.saveAndFlush(user);
        return "update-user";
    }

    @GetMapping("/edite/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public String updatepart(@PathVariable("id") int id) {
        Partenaire part = partenaireRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if(part.getStatut().equals("bloqué")){
            part.setStatut("débloqué");
        }else if(part.getStatut().equals("débloqué")){
            part.setStatut("bloqué");
        }
        partenaireRepository.saveAndFlush(part);
        return "partenaire modifié!!!!";
    }

    }
