package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.AuthResponseDTO;
import com.example.auratechApi.dtos.LoginRequestDTO;
import com.example.auratechApi.dtos.RegisterRequestDTO;
import com.example.auratechApi.dtos.UserResponseDTO;
import com.example.auratechApi.mappers.UserMapper;
import com.example.auratechApi.models.UserEntity;
import com.example.auratechApi.services.LoginService;
import com.example.auratechApi.services.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;
    private final UserMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO login ) {

        AuthResponseDTO loginResponseDTO = this.loginService.login(login);

        return ResponseEntity.ok(loginResponseDTO);

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO register) {

        this.registerService.register(register);

        AuthResponseDTO loginResponse = this.loginService.login(new LoginRequestDTO(register.email(), register.password()));

        return ResponseEntity.ok(loginResponse);

    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(mapper.toDto(user));
    }

}
