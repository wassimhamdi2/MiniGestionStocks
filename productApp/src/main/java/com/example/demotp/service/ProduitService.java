package com.example.demotp.service;

import com.example.demotp.model.Produit;
import com.example.demotp.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ProduitService {
 @Autowired
 private ProduitRepository produitRepository;
 public List<Produit> findAll() {
 return produitRepository.findAll();
 }
 public Produit save(Produit produit) {
 return produitRepository.save(produit);
 }
 public Produit findById(Long id) {
 Optional<Produit> produit = produitRepository.findById(id);
 return produit.orElse(null);
 }
 public void delete(Long id) {
 produitRepository.deleteById(id);
 } 
 public List<Produit> findByLibelleContainingIgnoreCase(String libelle) {
    return produitRepository.findByLibelleContainingIgnoreCase(libelle);
}
}