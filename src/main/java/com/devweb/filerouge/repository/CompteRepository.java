package com.devweb.filerouge.repository;


import com.devweb.filerouge.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {

}