package com.example.market_data_analyzer.DTO;
import java.time.LocalDate;

public class MarketDataDTO {

    private LocalDate date;
    private double openPrice;
    private double highestPrice;
    private double lowestPrice;
    private double closePrice;
    private long volume;
    private String name;

    public MarketDataDTO(LocalDate date, double openPrice, double highestPrice, double lowestPrice, double closePrice, long volume, String name) {
        this.date = date;
        this.openPrice = openPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
