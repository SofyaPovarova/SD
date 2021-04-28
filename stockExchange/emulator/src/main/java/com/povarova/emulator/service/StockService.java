package com.povarova.emulator.service;

import com.povarova.emulator.model.Stock;
import com.povarova.emulator.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock add(Stock stock) {
        return stockRepository.save(stock);
    }

    public List<Stock> list() {
        return stockRepository.findAll();
    }

    public Stock updatePrice(String company, Long price) {
        var stock = findByCompany(company).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such stock"));
        stock.setPrice(price);
        return stockRepository.save(stock);
    }

    public Optional<Stock> findByCompany(String company) {
        return stockRepository.findByCompany(company);
    }

}
