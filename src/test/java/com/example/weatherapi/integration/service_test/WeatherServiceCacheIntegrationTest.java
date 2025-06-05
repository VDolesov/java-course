package com.example.weatherapi.integration.service_test;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class WeatherServiceCacheIntegrationTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CacheManager cacheManager;

    private final double lat = 55.7961;
    private final double lon = 49.1064;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("weatherCache").clear();
    }

    @Test
    void testWeatherCachingByCoordinates() {
        WeatherData firstCall = weatherService.getWeather(lat, lon);
        WeatherData secondCall = weatherService.getWeather(lat, lon);
        assertThat(secondCall).isSameAs(firstCall);
    }
}
