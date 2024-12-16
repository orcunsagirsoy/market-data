package com.example.market_data_analyzer.Service;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class MarketDataService {

    @Autowired
    private InfluxDBClient influxDBClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Value("${influxdb.org}")
    private String org;

    public void saveMarketData(String line) {
        try {
            // Parse the line
            String[] fields = line.split(",");
            String date = fields[0];
            double openPrice = Double.parseDouble(fields[1]);
            double highestPrice = Double.parseDouble(fields[2]);
            double lowestPrice = Double.parseDouble(fields[3]);
            double closePrice = Double.parseDouble(fields[4]);
            int volume = Integer.parseInt(fields[5]);
            String name = fields[6];

            Instant timestamp = parseDateToInstant(date);

            // Create a Point for time-series data
            Point point = Point
                    .measurement("market_data")
                    .addTag("name", name)
                    .addField("open_price", openPrice)
                    .addField("highest_price", highestPrice)
                    .addField("lowest_price", lowestPrice)
                    .addField("close_price", closePrice)
                    .addField("volume", volume)
                    .time(timestamp, WritePrecision.NS);

            // Write the point to InfluxDB
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            writeApi.writePoint(bucket, org, point);
            System.out.println("Writing Point: " + point.toLineProtocol());

        } catch (Exception e) {
            System.err.println("Error processing line: " + line + " | Error: " + e.getMessage());
        }
    }

    private Instant parseDateToInstant(String date) {
        try {
            // If the date is in 'yyyy-MM-dd' format, append 'T00:00:00Z'
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                date += "T00:00:00Z";
            }
            return Instant.parse(date);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format: " + date, e);
        }
    }

    public double calculateAverageReturn() {

        return 0;
    }

    public double calculateVolatility() {

        return 0;
    }

    public List<Double> calculateMovingAverage(int period) {

        return List.of();
    }

    @Measurement(name = "market_data")
    private static class Temperature {

        @Column(tag = true)
        String date;

        @Column
        Double openPrice;

        @Column
        Double highestPrice;

        @Column
        Double lowestPrice;

        @Column
        Double closePrice;

        @Column
        int volume;

        @Column
        String name;
    }

}
