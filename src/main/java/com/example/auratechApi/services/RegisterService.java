package com.example.auratechApi.services;

import com.example.auratechApi.dtos.RegisterRequestDTO;
import com.example.auratechApi.exceptions.UserRegistrationException;
import com.example.auratechApi.mappers.RegisterMapper;
import com.example.auratechApi.model.UserEntity;
import com.example.auratechApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterMapper mapper;

    public void register(RegisterRequestDTO register) {

        UserEntity user = mapper.toEntity(register);
        user.setPassword(passwordEncoder.encode(register.password()));
        Optional<UserEntity> result = this.userRepository.findByEmail(user.getEmail());
        
        if(result.isPresent()) {
            throw new UserRegistrationException("User already exists with this email address");
        }

        this.userRepository.save(user);

    }

}
