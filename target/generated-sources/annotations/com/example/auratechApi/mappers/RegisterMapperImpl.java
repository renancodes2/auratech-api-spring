package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.RegisterRequestDTO;
import com.example.auratechApi.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T12:37:05-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class RegisterMapperImpl implements RegisterMapper {

    @Override
    public UserEntity toEntity(RegisterRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( dto.email() );
        userEntity.setName( dto.name() );
        userEntity.setPassword( dto.password() );
        List<String> list = dto.roles();
        if ( list != null ) {
            userEntity.setRoles( new ArrayList<String>( list ) );
        }

        return userEntity;
    }
}
