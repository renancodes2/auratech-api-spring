package com.example.auratechApi.services;

import com.example.auratechApi.dtos.AuthResponseDTO;
import com.example.auratechApi.dtos.LoginRequestDTO;
import com.example.auratechApi.exceptions.UnauthorizedAccessException;
import com.example.auratechApi.exceptions.UserRegistrationException;
import com.example.auratechApi.mappers.LoginMapper;
import com.example.auratechApi.models.UserEntity;
import com.example.auratechApi.repositories.UserRepository;
import com.example.auratechApi.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final LoginMapper mapper;

    public AuthResponseDTO login(LoginRequestDTO login) {
        UserEntity user = this.userRepository.findByEmail(login.email()).orElseThrow(() -> new UserRegistrationException("User not found. Please check your email or create a new account"));

        if(!passwordEncoder.matches(login.password(), user.getPassword())) {
            throw new UnauthorizedAccessException("Invalid email or password");
        }

        String token = tokenService.generateToken(user);
        return new AuthResponseDTO(user.getName(), token);
    }

}
