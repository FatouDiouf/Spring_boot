package com.devweb.filerouge.repository;


import com.devweb.filerouge.model.Compte;
import com.devweb.filerouge.model.Depot;
import com.devweb.filerouge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepotRepository extends JpaRepository<Depot, Integer> {
  // @Query("SELECT d FROM Depot d WHERE d.caissier = :user AND d.compte = :compte")
  // List<Depot> findMesDepots(@Param("user") User caissier, @Param("compte")Compte compte);
}