package com.example.weatherapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetWeatherIntegration() throws Exception {
        mockMvc.perform(get("/weather/Саратов"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Саратов"));
    }
}
