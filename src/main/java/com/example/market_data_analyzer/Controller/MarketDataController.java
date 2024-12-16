package com.example.market_data_analyzer.Controller;

import com.example.market_data_analyzer.DTO.MarketDataDTO;
import com.example.market_data_analyzer.Service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    @Autowired
    private MarketDataService marketDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMarketData(@RequestParam("file") MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine(); // Skip the header line

            // Process each line directly in the calling thread
            br.lines().forEach(marketDataService::saveMarketData);

            return ResponseEntity.ok("File processed successfully. Data uploaded to InfluxDB.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
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
