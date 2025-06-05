package com.example.weatherapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherResponse {
    private String city;
    private String temperature;
    private String feelsLike;
    private String dewPoint;
    private String pressure;
    private String wind;
    private String windDirection;
    private String cloudCover;
    private String visibility;
    private List<Double> hourlyTemperatures;
    private LocalDateTime updatedAt;

    private Long id;
    private Double latitude;
    private Double longitude;
    private Integer requestCounter;
    private LocalDateTime lastAccessedAt;

    public WeatherResponse() {
    }

    public WeatherResponse(
            Long id,
            String city,
            String temperature,
            String feelsLike,
            String dewPoint,
            String pressure,
            String wind,
            String windDirection,
            String cloudCover,
            String visibility,
            List<Double> hourlyTemperatures,
            LocalDateTime updatedAt,
            Double latitude,
            Double longitude,
            Integer requestCounter,
            LocalDateTime lastAccessedAt
    ) {
        this.id = id;
        this.city = city;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.wind = wind;
        this.windDirection = windDirection;
        this.cloudCover = cloudCover;
        this.visibility = visibility;
        this.hourlyTemperatures = hourlyTemperatures;
        this.updatedAt = updatedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestCounter = requestCounter;
        this.lastAccessedAt = lastAccessedAt;
    }

    public String getCity() { return city; }
    public String getTemperature() { return temperature; }
    public String getFeelsLike() { return feelsLike; }
    public String getDewPoint() { return dewPoint; }
    public String getPressure() { return pressure; }
    public String getWind() { return wind; }
    public String getWindDirection() { return windDirection; }
    public String getCloudCover() { return cloudCover; }
    public String getVisibility() { return visibility; }
    public List<Double> getHourlyTemperatures() { return hourlyTemperatures; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Long getId() { return id; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getRequestCounter() { return requestCounter; }
    public LocalDateTime getLastAccessedAt() { return lastAccessedAt; }

    public void setCity(String city) { this.city = city; }
    public void setTemperature(String temperature) { this.temperature = temperature; }
    public void setFeelsLike(String feelsLike) { this.feelsLike = feelsLike; }
    public void setDewPoint(String dewPoint) { this.dewPoint = dewPoint; }
    public void setPressure(String pressure) { this.pressure = pressure; }
    public void setWind(String wind) { this.wind = wind; }
    public void setWindDirection(String windDirection) { this.windDirection = windDirection; }
    public void setCloudCover(String cloudCover) { this.cloudCover = cloudCover; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public void setHourlyTemperatures(List<Double> hourlyTemperatures) { this.hourlyTemperatures = hourlyTemperatures; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setId(Long id) { this.id = id; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setRequestCounter(Integer requestCounter) { this.requestCounter = requestCounter; }
    public void setLastAccessedAt(LocalDateTime lastAccessedAt) { this.lastAccessedAt = lastAccessedAt; }
}
