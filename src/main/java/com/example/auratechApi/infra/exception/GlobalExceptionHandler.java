package com.example.auratechApi.infra.exception;

import com.example.auratechApi.exceptions.ImageUploadException;
import com.example.auratechApi.exceptions.ResourceNotFoundException;
import com.example.auratechApi.exceptions.UnauthorizedAccessException;
import com.example.auratechApi.exceptions.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        return new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                fieldErrors.stream().map(
                        err -> new InvalidField(
                                err.getField(),
                                err.getDefaultMessage()))
                        .toList()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleUserRegistrationException(UserRegistrationException e) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleImageUploadException(ImageUploadException e) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleIOException(IOException e) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

}
