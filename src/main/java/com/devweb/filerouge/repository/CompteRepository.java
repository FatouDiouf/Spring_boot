package com.devweb.filerouge.repository;


import com.devweb.filerouge.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    Optional<Compte> findCompteByNumcompte(String numcompte);
}