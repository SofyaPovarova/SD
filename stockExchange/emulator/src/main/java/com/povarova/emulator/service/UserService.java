package com.povarova.emulator.service;

import com.povarova.emulator.model.Stock;
import com.povarova.emulator.model.User;
import com.povarova.emulator.repository.StockRepository;
import com.povarova.emulator.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final StockService stockService;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    public UserService(StockService stockService, StockRepository stockRepository, UserRepository userRepository) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    public User register(String login) {
        var user = new User();
        user.setLogin(login);
        return userRepository.save(user);
    }

    public void deposit(String login, Long money) {
        var user = findByLogin(login);
        user.setMoney(user.getMoney() + money);
        userRepository.save(user);
    }

    public List<Stock> getPortfolio(String login) {
        return findByLogin(login).getPortfolio();
    }

    public Long getMoney(String login) {
        var user = findByLogin(login);
        return user.getMoney() + user.getPortfolio().stream().map(Stock::getPrice).reduce(0L, Long::sum);
    }

    public void buy(String login, String company, Long count) {
        var user = findByLogin(login);
        var stock = stockService.findByCompany(company).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such stock"));
        var totalPrice = count * stock.getPrice();
        if (user.getMoney() < totalPrice) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds!");
        }
        if (count > stock.getCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many stock to buy!");
        }
        user.setMoney(user.getMoney() - totalPrice);
        stock.setCount(stock.getCount() - count);
        var portfolio = user.getPortfolio();
        var stockExists = false;
        for (var currentStock : portfolio) {
            if (currentStock.getCompany().equals(company)) {
                currentStock.setCount(currentStock.getCount() + count);
                stockExists = true;
                break;
            }
        }
        if (!stockExists) {
            var newStock = new Stock();
            newStock.setCount(count);
            newStock.setCompany(company);
            newStock.setPrice(stock.getPrice());
            portfolio.add(newStock);
        }
        stockRepository.save(stock);
        userRepository.save(user);
    }

    public void sell(String login, String company, Long count) {
        var user = findByLogin(login);
        var stock = stockService.findByCompany(company).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such stock"));
        var totalPrice = count * stock.getPrice();
        var portfolio = user.getPortfolio();
        var stockExists = false;
        for (var currentStock : portfolio) {
            if (currentStock.getCompany().equals(company)) {
                if (currentStock.getCount() < count) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock to sell!");
                }
                currentStock.setCount(currentStock.getCount() - count);
                stock.setCount(stock.getCount() + count);
                user.setMoney(user.getMoney() + totalPrice);
                stockExists = true;
            }
        }
        if (!stockExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have this stock!");
        }
        stockRepository.save(stock);
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user!"));
    }
}
