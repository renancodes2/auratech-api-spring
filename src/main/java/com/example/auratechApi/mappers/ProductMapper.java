package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.ProductRequestDTO;
import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toDto(ProductEntity entity);
}
