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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) { // Use virtual threads

            // Thread-safe list to store parsed data
            List<MarketDataDTO> allData = Collections.synchronizedList(new ArrayList<>());

            List<Future<Void>> futures = new ArrayList<>();
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String currentLine = line; // Final variable for use in lambda
                Future<Void> future = executor.submit(() -> {
                    try {
                        String[] fields = currentLine.split(",");
                        LocalDate date = LocalDate.parse(fields[0]);
                        double openPrice = Double.parseDouble(fields[1]);
                        double highestPrice = Double.parseDouble(fields[2]);

                        double lowestPrice = Double.parseDouble(fields[3]);
                        double closePrice = Double.parseDouble(fields[4]);
                        long volume = Long.parseLong(fields[5]);
                        String name = fields[6];
                        allData.add(new MarketDataDTO(date,openPrice,highestPrice,lowestPrice, closePrice,volume,name));
                    } catch (Exception e) {
                        // Log and handle malformed lines
                        System.err.println("Error processing line: " + currentLine);
                    }
                    return null;
                });
                futures.add(future);
            }

            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                future.get();
            }

            // Save all parsed data
            marketDataService.uploadMarketData(allData);

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
