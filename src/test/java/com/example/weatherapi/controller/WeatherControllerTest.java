package com.example.weatherapi.controller;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
@Import(WeatherControllerTest.TestConfig.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherService weatherService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public WeatherService weatherService() {
            return Mockito.mock(WeatherService.class);
        }
    }

    @Test
    void shouldReturnWeatherData() throws Exception {
        WeatherData mockData = new WeatherData("Саратов", "10,12,13");
        mockData.setLastAccessedAt(LocalDateTime.now());

        Mockito.when(weatherService.getWeatherForCity("Саратов")).thenReturn(mockData);

        mockMvc.perform(get("/weather/Саратов")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Саратов"))
                .andExpect(jsonPath("$.temperature").value("10,12,13"));
    }

}
