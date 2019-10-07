package com.devweb.filerouge.repository;


import com.devweb.filerouge.model.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Integer> {
    Optional<Partenaire> findPartenaireByNinea(String ninea);
    //Boolean existsByUsername(String username);
    //Boolean existsByEmail(String email);
}