package com.devweb.filerouge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "compte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "numcompte"
        })
})
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 30)
    private String numcompte;

    @NotBlank
    private String solde;

    @JoinColumn(name = "partenaire_id",referencedColumnName ="id")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties("users")
    private Partenaire partenaire;

    public Compte() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumcompte() {
        return numcompte;
    }

    public void setNumcompte(String numcompte) {
        this.numcompte = numcompte;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }
}
