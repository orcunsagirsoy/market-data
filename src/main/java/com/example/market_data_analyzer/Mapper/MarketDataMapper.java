package com.example.market_data_analyzer.Mapper;
import com.example.market_data_analyzer.DTO.MarketDataDTO;
import com.example.market_data_analyzer.Entity.MarketData;

import java.util.List;
import java.util.stream.Collectors;

public class MarketDataMapper {

    // Convert Model to Entity
    public static MarketData toEntity(MarketDataDTO model) {
        MarketData entity = new MarketData();
        entity.setDate(model.getDate());
        entity.setOpenPrice(model.getOpenPrice());
        entity.setHighestPrice(model.getHighestPrice());
        entity.setLowestPrice(model.getLowestPrice());
        entity.setClosePrice(model.getClosePrice());
        entity.setVolume(model.getVolume());
        entity.setName(model.getName());
        return entity;
    }

    // Convert Entity to Model
    public static MarketDataDTO toModel(MarketData entity) {
        return new MarketDataDTO(
                entity.getDate(),
                entity.getOpenPrice(),
                entity.getHighestPrice(),
                entity.getLowestPrice(),
                entity.getClosePrice(),
                entity.getVolume(),
                entity.getName()
        );
    }

    // Convert a List of Models to a List of Entities
    public static List<MarketData> toEntityList(List<MarketDataDTO> models) {
        return models.stream()
                .map(MarketDataMapper::toEntity)
                .collect(Collectors.toList());
    }

    // Convert a List of Entities to a List of Models
    public static List<MarketDataDTO> toModelList(List<MarketData> entities) {
        return entities.stream()
                .map(MarketDataMapper::toModel)
                .collect(Collectors.toList());
    }
}
