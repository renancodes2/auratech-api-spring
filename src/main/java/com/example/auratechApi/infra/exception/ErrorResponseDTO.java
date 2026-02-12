package com.example.auratechApi.infra.exception;

import java.util.List;

public record ErrorResponseDTO(Integer status, String msg, List<InvalidField> errors) {
}
