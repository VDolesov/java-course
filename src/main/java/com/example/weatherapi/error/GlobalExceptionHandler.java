package com.example.weatherapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        ApiError error = new ApiError(
                status.value(),         // 400, 404 и т.д.
                status.toString(),      // "400 BAD_REQUEST"
                ex.getReason()          // твоё сообщение, например "Город слишком короткий"
        );

        return new ResponseEntity<>(error, status);
    }
}
