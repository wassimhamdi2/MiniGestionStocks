	package com.example.demotp.model;

	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.Date;

	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.ManyToMany;
	import jakarta.persistence.OneToMany;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	import lombok.ToString;
	@Entity
	@Data @NoArgsConstructor @AllArgsConstructor @ToString

	public class Stock {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
		private Collection<Produit> produits = new ArrayList<>();
		private Boolean Action; //Action is true == Add or false == remove stock qte from product
		private Integer qte;
		private Date createdAt;
		private Date updateddAt;
		
	}