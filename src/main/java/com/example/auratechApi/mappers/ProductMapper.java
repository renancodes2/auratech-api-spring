package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.models.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
    ProductResponseDTO toDto(ProductEntity product);
}
