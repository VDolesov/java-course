package com.example.weatherapi.repository;

import com.example.weatherapi.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByCityIgnoreCase(String city);
}