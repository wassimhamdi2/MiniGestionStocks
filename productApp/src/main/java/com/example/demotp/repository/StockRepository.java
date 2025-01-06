package com.example.demotp.repository;

import com.example.demotp.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Custom query methods can be added here if needed
}
