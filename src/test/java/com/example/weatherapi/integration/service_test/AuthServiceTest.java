
package com.example.weatherapi.integration.service_test;

import com.example.weatherapi.dto.AuthRequest;
import com.example.weatherapi.model.AppUser;
import com.example.weatherapi.repository.AppUserRepository;
import com.example.weatherapi.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterNewUser() {
        AuthRequest request = new AuthRequest("user", "pass");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        authService.registerUser(request.getUsername(), request.getPassword());

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("user") &&
                        user.getPassword().equals("encoded")
        ));
    }
}