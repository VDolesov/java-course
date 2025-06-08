package com.example.weatherapi.integration.service_test;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import com.example.weatherapi.service.WeatherForecastClient;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherForecastClient forecastClient;

    @Mock
    private WeatherDataRepository weatherRepository; // если используется сохранение

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void shouldReturnCompleteWeatherDataFromClient() {
        // Arrange
        WeatherData mockData = new WeatherData();
        mockData.setCity("Moscow");
        mockData.setLatitude(55.7);
        mockData.setLongitude(37.6);
        mockData.setTemperature("21.5");

        when(forecastClient.getForecast(anyDouble(), anyDouble()))
                .thenReturn(Mono.just(mockData));

        // Act
        WeatherData result = weatherService.getWeather(55.7, 37.6);

        // Assert
        assertNotNull(result);
        assertEquals("Moscow", result.getCity());
        assertEquals(55.7, result.getLatitude());
        assertEquals(37.6, result.getLongitude());
        assertEquals(21.5, result.getTemperature());

        verify(forecastClient).getForecast(55.7, 37.6);
        verify(weatherRepository).save(any(WeatherData.class)); // если используется сохранение
    }

    @Test
    void shouldHandleEmptyForecastFromClient() {
        when(forecastClient.getForecast(anyDouble(), anyDouble()))
                .thenReturn(Mono.empty());

        WeatherData result = weatherService.getWeather(55.7, 37.6);

        assertNull(result);
        verify(forecastClient).getForecast(55.7, 37.6);
        verifyNoInteractions(weatherRepository); // т.к. ничего не сохраняется
    }
}
