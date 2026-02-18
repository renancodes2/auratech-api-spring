package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.AuthResponseDTO;
import com.example.auratechApi.dtos.LoginRequestDTO;
import com.example.auratechApi.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    UserEntity toEntity(LoginRequestDTO dto);

    AuthResponseDTO toDto(UserEntity user);
}
