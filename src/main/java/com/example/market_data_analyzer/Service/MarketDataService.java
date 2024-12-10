package com.example.market_data_analyzer.Service;

import com.example.market_data_analyzer.Mapper.MarketDataMapper;
import org.springframework.stereotype.Service;
import com.example.market_data_analyzer.DTO.MarketDataDTO;
import com.example.market_data_analyzer.Repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.*;
import java.util.*;

@Service
public class MarketDataService {

    @Autowired
    private MarketDataRepository marketDataRepository;


    private List<MarketDataDTO> marketDataList = new ArrayList<>();

    public void uploadMarketData(List<MarketDataDTO> data) {
        // Use repository to save the list of MarketData
        marketDataRepository.saveAll(MarketDataMapper.toEntityList(data));
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

}
