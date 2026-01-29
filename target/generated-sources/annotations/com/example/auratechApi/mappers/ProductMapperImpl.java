package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.model.CategoryEntity;
import com.example.auratechApi.model.ProductEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-29T08:46:58-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Azul Systems, Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        Integer stock = null;
        List<String> imagesUrls = null;
        CategoryEntity category = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        stock = entity.getStock();
        List<String> list = entity.getImagesUrls();
        if ( list != null ) {
            imagesUrls = new ArrayList<String>( list );
        }
        category = entity.getCategory();

        ProductResponseDTO productResponseDTO = new ProductResponseDTO( id, name, description, price, stock, imagesUrls, category );

        return productResponseDTO;
    }
}
