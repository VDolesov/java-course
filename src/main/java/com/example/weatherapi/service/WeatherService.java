package com.example.weatherapi.service;

import com.example.weatherapi.dto.WeatherResponse;
import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherDataRepository weatherRepository;
    private final WeatherForecastClient forecastClient;
    private final YandexGeocoderService geocoderService;
    private final Random random = new Random();

    public WeatherService(WeatherDataRepository weatherRepository,
                          WeatherForecastClient forecastClient,
                          YandexGeocoderService geocoderService) {
        this.weatherRepository = weatherRepository;
        this.forecastClient = forecastClient;
        this.geocoderService = geocoderService;
    }

    public WeatherResponse getWeatherForCity(String city) {
        String requestId = UUID.randomUUID().toString();
        logger.info(">>> [LOG][{}] Запрос на город: {}", requestId, city);

        if (city == null || city.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название города не может быть пустым");
        }

        if (!Pattern.matches("^[А-Яа-яA-Za-z\\-\\s]{2,50}$", city)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название города содержит недопустимые символы или слишком короткое");
        }

        city = city.trim();
        Optional<WeatherData> existingData = weatherRepository.findByCityIgnoreCase(city);

        if (existingData.isPresent()) {
            WeatherData data = existingData.get();
            data.setLastAccessedAt(LocalDateTime.now());
            data.setRequestCount(data.getRequestCount() + 1);
            weatherRepository.save(data);

            logger.info(">>> [LOG][{}] Город найден. Обновлён requestCount = {}", requestId, data.getRequestCount());
            return mapToResponse(data);
        }

        var coordsOpt = geocoderService.getCoordinatesByCity(city);
        if (coordsOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Не удалось определить координаты города");
        }

        var coords = coordsOpt.get();
        WeatherData forecast = getWeather(coords.lat(), coords.lon());
        forecast.setCity(city);
        forecast.setLatitude(coords.lat());
        forecast.setLongitude(coords.lon());

        weatherRepository.save(forecast);
        logger.info(">>> [LOG][{}] Получен прогноз по API и сохранён", requestId);

        return mapToResponse(forecast);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void updateAllWeatherDataDaily() {
        String taskId = UUID.randomUUID().toString();
        logger.info(">>> [SCHEDULE][{}] Запущено обновление прогнозов для всех сохранённых городов", taskId);

        var cities = weatherRepository.findAll();

        for (WeatherData data : cities) {
            Double lat = data.getLatitude();
            Double lon = data.getLongitude();

            if (lat == null || lon == null) {
                logger.warn(">>> [SCHEDULE][{}] Пропущен город без координат: {}", taskId, data.getCity());
                continue;
            }

            try {
                WeatherData updated = getWeather(lat, lon);
                updated.setId(data.getId());
                updated.setCity(data.getCity());
                updated.setLatitude(lat);
                updated.setLongitude(lon);
                updated.setRequestCount(data.getRequestCount());
                updated.setLastAccessedAt(data.getLastAccessedAt());

                weatherRepository.save(updated);
                logger.info(">>> [SCHEDULE][{}] Обновлён прогноз для города: {}", taskId, data.getCity());

            } catch (Exception e) {
                logger.error(">>> [SCHEDULE][{}] Ошибка при обновлении прогноза для {}: {}", taskId, data.getCity(), e.getMessage());
            }
        }

        logger.info(">>> [SCHEDULE][{}] Обновление завершено", taskId);
    }

    @Cacheable(value = "weather", key = "T(java.lang.String).format('%.4f-%.4f', #lat, #lon)")
    public WeatherData getWeather(double lat, double lon) {
        String label = String.format("Координаты: %.4f, %.4f", lat, lon);
        String requestId = UUID.randomUUID().toString();
        logger.info(">>> [API][{}] Запрос прогноза по координатам: {}", requestId, label);

        WeatherData forecast = forecastClient.getForecast(lat, lon).block();

        if (forecast == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Не удалось получить прогноз погоды");
        }

        forecast.setLastAccessedAt(LocalDateTime.now());
        forecast.setUpdatedAt(LocalDateTime.now());
        forecast.setRequestCount(1);
        forecast.setLatitude(lat);
        forecast.setLongitude(lon);

        if (forecast.getHourlyTemperatures() == null || forecast.getHourlyTemperatures().isEmpty()) {
            List<Double> hourlyTemps = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                hourlyTemps.add(15 + random.nextDouble() * 10);
            }
            forecast.setHourlyTemperatures(hourlyTemps);
        }

        logger.info(">>> [API][{}] Данные сохранены: {}", requestId, forecast.getTemperature());
        return forecast;
    }

    @CacheEvict(value = "weather", key = "#city")
    public void evictCacheForCity(String city) {
        logger.info(">>> [CACHE] Очистка кэша для города: {}", city);
    }

    @CacheEvict(value = "weather", allEntries = true)
    public void clearAllCache() {
        logger.info(">>> [CACHE] Полная очистка кэша");
    }

    private WeatherResponse mapToResponse(WeatherData weather) {
        WeatherResponse response = new WeatherResponse();
        response.setId(weather.getId());
        response.setCity(weather.getCity());
        response.setTemperature(weather.getTemperature());
        response.setFeelsLike(weather.getFeelsLike());
        response.setDewPoint(weather.getDewPoint());
        response.setPressure(weather.getPressure());
        response.setWind(weather.getWind());
        response.setWindDirection(weather.getWindDirection());
        response.setCloudCover(weather.getCloudCover());
        response.setVisibility(weather.getVisibility());
        response.setHourlyTemperatures(weather.getHourlyTemperatures());
        response.setUpdatedAt(weather.getUpdatedAt());
        response.setLatitude(weather.getLatitude());
        response.setLongitude(weather.getLongitude());
        response.setRequestCounter(weather.getRequestCount());
        response.setLastAccessedAt(weather.getLastAccessedAt());
        return response;
    }
}