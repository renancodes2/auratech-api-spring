package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.model.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toDto(ProductEntity entity);
}
