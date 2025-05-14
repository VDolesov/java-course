package com.example.weatherapi.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherRepository {

    private final JdbcTemplate jdbc;

    public WeatherRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Integer> getForecast(String cityName) {
        String selectSql = "SELECT temperatures FROM weather_forecasts WHERE city_name = ?";
        String updateSql = "UPDATE weather_forecasts SET last_accessed_at = CURRENT_TIMESTAMP, request_count = request_count + 1 WHERE city_name = ?";

        return jdbc.query(selectSql, new Object[]{cityName}, rs -> {
            if (rs.next()) {
                String tempStr = rs.getString("temperatures");
                jdbc.update(updateSql, cityName);
                return stringToTemperatures(tempStr);
            }
            return List.of(); // пустой список — прогноз не найден
        });
    }

    public void saveForecast(String cityName, List<Integer> temps) {
        String sql = "INSERT INTO weather_forecasts(city_name, temperatures, created_at, updated_at, request_count) VALUES (?, ?, now(), now(), 1)";
        String joinedTemps = String.join(",", temps.stream().map(String::valueOf).toList());
        jdbc.update(sql, cityName, joinedTemps);
    }

    private List<Integer> stringToTemperatures(String tempStr) {
        return List.of(tempStr.split(",")).stream()
                .map(Integer::parseInt)
                .toList();
    }
}
