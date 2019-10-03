package com.devweb.filerouge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "depot")
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=75000)
    private String montant;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datedepot;

    @JoinColumn(name = "compte_id",referencedColumnName ="id")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties("depot")
    private Compte compte;

    public Depot(int id, @NotBlank @Size(min = 75000) String montant, Date datedepot, Compte compte) {
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

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public Date getDatedepot() {
        return datedepot;
    }

    public void setDatedepot(Date datedepot) {
        this.datedepot = datedepot;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
