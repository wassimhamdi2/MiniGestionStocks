package com.example.demotp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demotp.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByLibelleContainingIgnoreCase(String libelle);

}
