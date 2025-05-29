package com.example.weatherapi.service;

import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherDataRepository weatherRepository;
    private final Random random = new Random();

    public WeatherService(WeatherDataRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public WeatherData getWeatherForCity(String city) {
        String requestId = UUID.randomUUID().toString();
        logger.info(">>> [LOG][{}] Запрос на город: {}", requestId, city);

        if (city == null || city.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название города не может быть пустым");
        }

        if (!city.matches("^[А-Яа-яA-Za-z\\-\\s]{2,50}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название города содержит недопустимые символы или слишком короткое");
        }

        city = city.trim();
        Optional<WeatherData> existingData = weatherRepository.findByCityIgnoreCase(city);

        if (existingData.isPresent()) {
            WeatherData data = existingData.get();
            data.setLastAccessedAt(LocalDateTime.now());
            data.setRequestCount(data.getRequestCount() + 1);
            data.setUpdatedAt(LocalDateTime.now());
            weatherRepository.save(data);
            logger.info(">>> [LOG][{}] Город найден. Обновлён requestCount = {}", requestId, data.getRequestCount());
            return data;
        }

        String temperatures = IntStream.range(0, 3)
                .map(i -> random.nextInt(46) - 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));

        WeatherData newData = new WeatherData(city, temperatures);
        newData.setLastAccessedAt(LocalDateTime.now());
        weatherRepository.save(newData);

        logger.info(">>> [LOG][{}] Новый город. Температуры: {}", requestId, temperatures);
        return newData;
    }

    public void evictCacheForCity(String city) {
        logger.info(">>> [CACHE] Очистка кэша для города: {}", city);
    }

    public void clearAllCache() {
        logger.info(">>> [CACHE] Полная очистка кэша");
    }
}
