package com.example.market_data_analyzer.Model;

public class MarketData {

    private java.time.LocalDate date;
    private double closePrice;

    // Constructor
    public MarketData(java.time.LocalDate date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    // Getters and Setters
    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }
}
