package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.UserResponseDTO;
import com.example.auratechApi.model.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-29T11:46:16-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String name = null;
        String email = null;

        name = entity.getName();
        email = entity.getEmail();

        UserResponseDTO userResponseDTO = new UserResponseDTO( name, email );

        return userResponseDTO;
    }
}
