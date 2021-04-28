package com.povarova.emulator.controller;

import com.povarova.emulator.model.Stock;
import com.povarova.emulator.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public Stock add(@RequestBody Stock stock) {
        System.out.println(stock.getCompany());
        System.out.println(stock.getCount());
        System.out.println(stock.getPrice());
        return stockService.add(stock);
    }

    @GetMapping
    public List<Stock> list() {
        return stockService.list();
    }

    @PutMapping("/{company}")
    public Stock updatePrice(@PathVariable String company, @RequestParam Long price) {
        return stockService.updatePrice(company, price);
    }

}
