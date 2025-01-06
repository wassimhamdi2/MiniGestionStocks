package com.example.demotp.controller;

import com.example.demotp.model.Produit;
import com.example.demotp.model.Stock;
import com.example.demotp.service.ProduitService;
import com.example.demotp.service.StockService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProduitService produitService;

    // POST: Add or remove stock
@PostMapping
@Transactional
public ResponseEntity<?> manageStock(@RequestBody Stock stockRequest) {
    if (stockRequest.getProduits() == null || stockRequest.getProduits().isEmpty()) {
        return new ResponseEntity<>("No products provided for the stock operation.", HttpStatus.BAD_REQUEST);
    }

    // Create a managed list of products
    List<Produit> managedProduits = new ArrayList<>();
    
    for (Produit produit : stockRequest.getProduits()) {
        Produit existingProduit = produitService.findById(produit.getId());
        if (existingProduit == null) {
            return new ResponseEntity<>("Product with ID " + produit.getId() + " not found.", HttpStatus.NOT_FOUND);
        }

        // Update the stock quantity
        if (Boolean.TRUE.equals(stockRequest.getAction())) { // Add stock
            existingProduit.setQteStock(existingProduit.getQteStock() + stockRequest.getQte());
        } else if (Boolean.FALSE.equals(stockRequest.getAction())) { // Remove stock
            if (existingProduit.getQteStock() < stockRequest.getQte()) {
                return new ResponseEntity<>(
                    "Insufficient stock for product with ID " + produit.getId(),
                    HttpStatus.BAD_REQUEST
                );
            }
            existingProduit.setQteStock(existingProduit.getQteStock() - stockRequest.getQte());
        }

        produitService.save(existingProduit); // Save the updated product
        managedProduits.add(existingProduit); // Add the managed product to the list
    }

    // Set the managed products in the stock request
    stockRequest.setProduits(managedProduits);

    // Save the stock operation
    stockRequest.setCreatedAt(new Date());
    stockRequest.setUpdateddAt(new Date());
    Stock savedStock = stockService.save(stockRequest);

    return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
}


    // PUT: Update a stock operation's quantity
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody Stock stockRequest) {
        Stock existingStock = stockService.findById(id);
        if (existingStock == null) {
            return new ResponseEntity<>("Stock operation not found.", HttpStatus.NOT_FOUND);
        }

        for (Produit produit : stockRequest.getProduits()) {
            Produit existingProduit = produitService.findById(produit.getId());
            if (existingProduit == null) {
                return new ResponseEntity<>("Product with ID " + produit.getId() + " not found.", HttpStatus.NOT_FOUND);
            }

            int newQte = stockRequest.getQte();
            if (existingProduit.getQteStock() + newQte < 0) {
                return new ResponseEntity<>(
                    "Updating stock would cause negative stock for product with ID " + produit.getId(),
                    HttpStatus.BAD_REQUEST
                );
            }

            existingProduit.setQteStock(newQte);
            produitService.save(existingProduit);
        }

        existingStock.setQte(stockRequest.getQte());
        existingStock.setUpdateddAt(new Date());
        Stock updatedStock = stockService.save(existingStock);

        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    // GET: Retrieve all stock operations
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.findAll();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    // GET: Retrieve a stock operation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.findById(id);
        if (stock != null) {
            return new ResponseEntity<>(stock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Delete a stock operation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStock(@PathVariable Long id) {
        Stock stock = stockService.findById(id);
        
        if (stock != null) {
            for (Produit produit : stock.getProduits()) {
                produit.setQteStock(0);
                produitService.save(produit);
            }

            stockService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
