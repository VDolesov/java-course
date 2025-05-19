package org.example;

import java.util.*;

public class WeatherRepository {
    private final Map<String, List<Integer>> forecasts = new HashMap<>();

    public List<Integer> findForecast(String city) {
        return forecasts.get(city);
    }

    public void saveForecast(String city, List<Integer> forecast) {
        forecasts.put(city, forecast);
    }

    public boolean hasForecast(String city) {
        return forecasts.containsKey(city);
    }
}
