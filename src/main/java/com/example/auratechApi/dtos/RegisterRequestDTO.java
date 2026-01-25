package com.example.auratechApi.dtos;

import java.util.List;

public record RegisterRequestDTO(String name, String email, String password, List<String> roles) {
}
