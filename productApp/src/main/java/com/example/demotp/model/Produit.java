package com.example.demotp.model;

import java.util.ArrayList;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString

public class Produit {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String libelle;
 private Double prix;
 private Integer qteStock=0; 
 @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
 private Stock stock;
 

 // getters and setters


}
