package com.povarova.emulator.controller;

import com.povarova.emulator.model.Stock;
import com.povarova.emulator.model.User;
import com.povarova.emulator.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@RequestParam String login) {
        return userService.register(login);
    }

    @GetMapping("/{login}")
    public User authorize(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PutMapping("/deposit/{login}")
    public void deposit(@PathVariable String login, @RequestParam @Range(max = 10000L) Long value) {
        userService.deposit(login, value);
    }

    @GetMapping("/portfolio/{login}")
    public List<Stock> getPortfolio(@PathVariable String login) {
        return userService.getPortfolio(login);
    }

    @GetMapping("/money/{login}")
    public Long getMoney(@PathVariable String login) {
        return userService.getMoney(login);
    }

    @PutMapping("/buy/{login}")
    public void buy(@PathVariable String login, @RequestParam String company, @RequestParam @Range(max = 100L) Long count) {
        userService.buy(login, company, count);
    }

    @PutMapping("/sell/{login}")
    public void sell(@PathVariable String login, @RequestParam String company, @RequestParam @Range(max = 100L) Long count) {
        userService.sell(login, company, count);
    }

}
