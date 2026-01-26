package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.AuthResponseDTO;
import com.example.auratechApi.dtos.LoginRequestDTO;
import com.example.auratechApi.model.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-26T17:44:34-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class LoginMapperImpl implements LoginMapper {

    @Override
    public UserEntity toEntity(LoginRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( dto.email() );
        userEntity.setPassword( dto.password() );

        return userEntity;
    }

    @Override
    public AuthResponseDTO toDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        String name = null;

        name = user.getName();

        String token = null;

        AuthResponseDTO authResponseDTO = new AuthResponseDTO( name, token );

        return authResponseDTO;
    }
}
