package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.CategoryDTO;
import com.example.auratechApi.models.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryDTO dto);

    CategoryDTO toDto(CategoryEntity category);

}
