package com.example.market_data_analyzer.Config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

public class InfluxDBConfig {

    @Bean
    public InfluxDBClient influxDBClient() {
        String url = "http://localhost:8086";
        String token = Arrays.toString(System.getenv("INFLUXDB_TOKEN").toCharArray());
        String org = "test";
        return InfluxDBClientFactory.create(url, token.toCharArray(), org);
    }
}
