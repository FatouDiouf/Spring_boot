package com.devweb.filerouge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "partenaire", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "ninea"
        })
})
public class Partenaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 30)
    private String ninea;

    @NotBlank
    @Size(min=3, max = 30)
    private String raionsociale;

    @NotBlank
    @Size(min=3, max = 30)
    private String adresse;

    @NotBlank
    @Size(min=3, max = 30)
    private String statut;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy ="partenaire")
    @JsonIgnoreProperties("partenaire")
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy ="partenaire")
    @JsonIgnoreProperties("partenaire")
    private List<Compte> comptes;

    public Partenaire(int id,  String ninea,String raionsociale,String adresse) {
        this.id = id;
        this.ninea = ninea;
        this.raionsociale = raionsociale;
        this.adresse = adresse;
    }

    public Partenaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getRaionsociale() {
        return raionsociale;
    }

    public void setRaionsociale(String raionsociale) {
        this.raionsociale = raionsociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
