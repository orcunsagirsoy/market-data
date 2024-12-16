package com.example.market_data_analyzer.Config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {

    @Value("${influxdb.url}")
    private String influxDbUrl;

    @Value("${influxdb.token}")
    private String token;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;


    @Bean
    public InfluxDBClient influxDBClient() {
        InfluxDBClient client = InfluxDBClientFactory.create(influxDbUrl, token.toCharArray(), org);

        // Ensure the bucket exists (optional, can remove in production)
        Bucket existingBucket = client.getBucketsApi().findBucketByName(bucket);
        if (existingBucket == null) {
            client.getBucketsApi().createBucket(bucket, org);
        }

        return client;
    }
}
