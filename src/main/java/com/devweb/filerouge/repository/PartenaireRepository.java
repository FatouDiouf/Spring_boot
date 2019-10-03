package com.devweb.filerouge.repository;


import com.devweb.filerouge.model.Partenaire;
import com.devweb.filerouge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Integer> {

}