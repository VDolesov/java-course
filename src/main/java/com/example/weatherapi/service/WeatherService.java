package com.example.weatherapi.service;

import com.example.weatherapi.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WeatherService {

    private final WeatherRepository repository;
    private final Random random = new Random();

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public List<Integer> getForecast(String city) {
        List<Integer> forecast = repository.getForecast(city);

        if (forecast.isEmpty()) {
            List<Integer> newForecast = generateRandomForecast();
            repository.saveForecast(city, newForecast);
            return newForecast;
        }

        return forecast;
    }

    private List<Integer> generateRandomForecast() {
        return List.of(
                random.nextInt(30),
                random.nextInt(30),
                random.nextInt(30)
        );
    }
}
