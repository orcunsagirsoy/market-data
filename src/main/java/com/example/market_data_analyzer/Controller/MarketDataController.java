package com.example.market_data_analyzer.Controller;

import com.example.market_data_analyzer.Model.MarketData;
import com.example.market_data_analyzer.Service.MarketDataService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    @Autowired
    private MarketDataService marketDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMarketData(@RequestParam("file") MultipartFile file) {
        try {
            List<MarketData> marketDataList = new ArrayList<>();
            try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
                String[] line;
                reader.readNext(); // Skip header
                while ((line = reader.readNext()) != null) {
                    LocalDate date = LocalDate.parse(line[0]);
                    double closePrice = Double.parseDouble(line[4]); // Assuming close price is in 5th column
                    marketDataList.add(new MarketData(date, closePrice));
                }
            }
            marketDataService.uploadMarketData(marketDataList);
            return ResponseEntity.ok("Market data uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading market data: " + e.getMessage());
        }
    }

    @GetMapping("/analysis/average-return")
    public ResponseEntity<Double> getAverageReturn() {
        return ResponseEntity.ok(marketDataService.calculateAverageReturn());
    }

    @GetMapping("/analysis/volatility")
    public ResponseEntity<Double> getVolatility() {
        return ResponseEntity.ok(marketDataService.calculateVolatility());
    }

    @GetMapping("/analysis/moving-average")
    public ResponseEntity<List<Double>> getMovingAverage(@RequestParam(defaultValue = "200") int period) {
        return ResponseEntity.ok(marketDataService.calculateMovingAverage(period));
    }
}
