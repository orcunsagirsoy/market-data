package com.example.market_data_analyzer.Service;

import org.springframework.stereotype.Service;
import com.example.market_data_analyzer.Model.MarketData;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketDataService {

    private List<com.example.market_data_analyzer.Model.MarketData> marketDataList = new ArrayList<>();

    public void uploadMarketData(List<MarketData> data) {
        marketDataList = data;
    }

    public double calculateAverageReturn() {
        if (marketDataList.isEmpty()) return 0.0;

        double totalReturn = 0.0;
        for (int i = 1; i < marketDataList.size(); i++) {
            double dailyReturn = (marketDataList.get(i).getClosePrice() - marketDataList.get(i - 1).getClosePrice())
                    / marketDataList.get(i - 1).getClosePrice();
            totalReturn += dailyReturn;
        }
        int years = marketDataList.get(0).getDate().getYear() - marketDataList.get(marketDataList.size() - 1).getDate().getYear();
        return (totalReturn / years) * 100;
    }

    public double calculateVolatility() {
        if (marketDataList.isEmpty()) return 0.0;

        List<Double> dailyReturns = new ArrayList<>();
        for (int i = 1; i < marketDataList.size(); i++) {
            dailyReturns.add((marketDataList.get(i).getClosePrice() - marketDataList.get(i - 1).getClosePrice())
                    / marketDataList.get(i - 1).getClosePrice());
        }
        double mean = dailyReturns.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = dailyReturns.stream().mapToDouble(r -> Math.pow(r - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance) * 100;
    }

    public List<Double> calculateMovingAverage(int period) {
        if (marketDataList.isEmpty() || marketDataList.size() < period) return Collections.emptyList();

        List<Double> movingAverages = new ArrayList<>();
        for (int i = period - 1; i < marketDataList.size(); i++) {
            double sum = 0.0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += marketDataList.get(j).getClosePrice();
            }
            movingAverages.add(sum / period);
        }
        return movingAverages;
    }

}
