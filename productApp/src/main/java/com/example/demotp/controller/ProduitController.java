package com.example.demotp.controller;

import com.example.demotp.model.Produit;
import com.example.demotp.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String getAllProduits(Model model, @RequestParam(name = "search", required = false) String search) {
        List<Produit> produits;
        if (search != null && !search.isEmpty()) {
            produits = produitService.findByLibelleContainingIgnoreCase(search); // Use the service for search
        } else {
            produits = produitService.findAll(); // Fetch all products if no search is provided
        }
        model.addAttribute("produits", produits);
        model.addAttribute("search", search);
        return "produits"; // This matches the template name
    }

    // Add a new product
    @PostMapping("/add")
    public String addProduit(Produit produit) {
        produitService.save(produit);
        return "redirect:/produits";
    }

    // Show form to edit a product
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            model.addAttribute("produit", produit);
            return "editProduit"; // Updated to point to the edit view (editProduit.html)
        } else {
            return "redirect:/produits";
        }
    }

    // Update a product
    @PostMapping("/edit/{id}")
    public String updateProduit(@PathVariable("id") Long id, Produit produitDetails) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            produit.setLibelle(produitDetails.getLibelle());
            produit.setPrix(produitDetails.getPrix());
            produit.setQteStock(produitDetails.getQteStock());
            produitService.save(produit);
        }
        return "redirect:/produits";
    }

    // Delete a product
    @PostMapping("/delete/{id}")
    public String deleteProduit(@PathVariable("id") Long id) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            produitService.delete(id);
        }
        return "redirect:/produits";
    }
}
