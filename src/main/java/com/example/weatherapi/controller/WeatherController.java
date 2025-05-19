package com.example.weatherapi.controller;

import com.example.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/weather/{city}")
    public List<Integer> getForecast(@PathVariable String city) {
        return service.getForecast(city);
    }
}
