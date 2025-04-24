package org.example;

import java.util.*;

public class WeatherService {
    private final WeatherRepository repository;
    private final Random random = new Random();

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public List<Integer> getForecast(String city) {
        if (!isValidCity(city)) {
            throw new InvalidCityNameException();
        }

        if (repository.hasForecast(city)) {
            return repository.findForecast(city);
        } else {
            List<Integer> forecast = generateForecast();
            repository.saveForecast(city, forecast);
            return forecast;
        }
    }

    private boolean isValidCity(String city) {
        return city.matches("[А-Яа-яЁё\\s]+");
    }

    private List<Integer> generateForecast() {
        List<Integer> temps = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            temps.add(random.nextInt(20));
        }
        return temps;
    }
}
