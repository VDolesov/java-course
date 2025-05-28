package com.example.weatherapi.service;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private WeatherDataRepository repository;
    private WeatherService service;

    @BeforeEach
    void setUp() {
        repository = mock(WeatherDataRepository.class);
        service = new WeatherService(repository);
    }

    @Test
    void shouldGenerateNewWeatherDataIfCityNotFound() {
        when(repository.findByCityIgnoreCase("Саратов")).thenReturn(Optional.empty());

        WeatherData result = service.getWeatherForCity("Саратов");

        assertEquals("Саратов", result.getCity());
        assertNotNull(result.getTemperature());
        verify(repository).save(any(WeatherData.class));
    }
}
