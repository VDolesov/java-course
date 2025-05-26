package com.example.weatherapi.controller;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/{city}")
    public WeatherData getWeather(@PathVariable String city) {
        return service.getWeatherForCity(city);
    }
}
