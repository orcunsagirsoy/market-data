package com.example.market_data_analyzer;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MarketDataAnalyzerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MarketDataAnalyzerApplication.class, args);
	}

}
