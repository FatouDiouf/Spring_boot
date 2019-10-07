package com.devweb.filerouge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "depot")
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double montant;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String datedepot;

    @JsonIgnore
    @JoinColumn(name = "compte_id",referencedColumnName ="id")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties("compte")
    private Compte compte;



    public Depot(int id, @NotBlank @Size(min = 75000) double montant, String datedepot, Compte compte) {
        this.id = id;
        this.montant = montant;
        this.datedepot = datedepot;
        this.compte = compte;
    }

    public Depot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDatedepot() {
        return datedepot;
    }

    public void setDatedepot(String datedepot) {
        this.datedepot = datedepot;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
