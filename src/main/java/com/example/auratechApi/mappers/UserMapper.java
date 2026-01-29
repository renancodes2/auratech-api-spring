package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.UserResponseDTO;
import com.example.auratechApi.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(UserEntity entity);
}
