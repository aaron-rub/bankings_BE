package com.nighthawk.spring_portfolio.mvc.income;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @PostMapping
    public Income logIncome(@RequestBody Income income) {
        // Here you can add code to log the income to your database
        System.out.println("Logged daily income: " + income.getDailyIncome());
        return income;
    }
}

