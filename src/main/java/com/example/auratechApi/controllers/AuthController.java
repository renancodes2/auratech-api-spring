package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.AuthResponseDTO;
import com.example.auratechApi.dtos.LoginRequestDTO;
import com.example.auratechApi.dtos.RegisterRequestDTO;
import com.example.auratechApi.services.LoginService;
import com.example.auratechApi.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO login ) {

        AuthResponseDTO loginResponseDTO = this.loginService.login(login);

        if(loginResponseDTO != null) {
            return ResponseEntity.ok(loginResponseDTO);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO register) {

        this.registerService.register(register);

        AuthResponseDTO loginResponse = this.loginService.login(new LoginRequestDTO(register.email(), register.password()));

        return ResponseEntity.ok(loginResponse);
    }

}
