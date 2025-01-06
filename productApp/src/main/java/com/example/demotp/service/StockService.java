package com.example.demotp.service;

import com.example.demotp.model.Stock;
import java.util.List;

public interface StockService {
    List<Stock> findAll();
    Stock findById(Long id);
    Stock save(Stock stock);
    void delete(Long id);
}
