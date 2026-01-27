package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.CategoryDTO;
import com.example.auratechApi.model.CategoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T12:37:03-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity toEntity(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName( dto.name() );

        return categoryEntity;
    }

    @Override
    public CategoryDTO toDto(CategoryEntity category) {
        if ( category == null ) {
            return null;
        }

        String name = null;

        name = category.getName();

        CategoryDTO categoryDTO = new CategoryDTO( name );

        return categoryDTO;
    }
}
