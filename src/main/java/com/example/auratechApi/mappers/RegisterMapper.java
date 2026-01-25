package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.RegisterRequestDTO;
import com.example.auratechApi.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

    UserEntity toEntity(RegisterRequestDTO dto);
}
