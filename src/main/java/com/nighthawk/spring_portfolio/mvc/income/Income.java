package com.nighthawk.spring_portfolio.mvc.income;

public class Income {
    private double dailyIncome;

    public Income() {}

    public Income(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }
}
