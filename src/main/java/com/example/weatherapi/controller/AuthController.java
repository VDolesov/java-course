package com.example.weatherapi.controller;

import com.example.weatherapi.dto.AuthRequest;
import com.example.weatherapi.dto.AuthResponse;
import com.example.weatherapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth-controller")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.login(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        authService.registerUser(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}
