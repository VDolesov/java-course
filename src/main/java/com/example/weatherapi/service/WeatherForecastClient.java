package com.example.weatherapi.service;

import com.example.weatherapi.dto.EolForecastDto;
import com.example.weatherapi.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherForecastClient {

    private final WebClient webClient;

    @Value("${weather.forecast.api-url}")
    private String apiUrl;

    @Value("${weather.forecast.token}")
    private String apiToken;

    public Mono<WeatherData> getForecast(double lat, double lon) {
        String requestUrl = String.format("%s/api/weather/?lat=%.6f&lon=%.6f&date=%s&token=%s",
                apiUrl, lat, lon, LocalDate.now(), apiToken);

        log.info(">>> [HTTP] Запрос к ProjectEOL: {}", requestUrl);

        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EolForecastDto>>() {})
                .map(forecasts -> {
                    if (forecasts == null || forecasts.isEmpty()) {
                        return null;
                    }

                    EolForecastDto dto = forecasts.get(0);
                    WeatherData data = new WeatherData();
                    data.setTemperature(toStr(dto.temperature()));
                    data.setFeelsLike(toStr(dto.feelsLike()));
                    data.setDewPoint(toStr(dto.dewPoint()));
                    data.setPressure(toStr(dto.pressure()));
                    data.setWind(toStr(dto.wind()));
                    data.setWindDirection(dto.windDirection());
                    data.setVisibility(toStr(dto.visibility()));
                    data.setCloudCover(toStr(dto.cloudCover()));
                    data.setUpdatedAt(LocalDateTime.now());
                    data.setLastAccessedAt(LocalDateTime.now());
                    data.setRequestCount(1);
                    return data;
                });
    }

    private String toStr(Object value) {
        return value == null ? null : value.toString();
    }
}
