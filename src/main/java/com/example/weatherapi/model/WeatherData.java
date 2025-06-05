package com.example.weatherapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "weather_forecast")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String temperature;
    private String feelsLike;
    private String dewPoint;
    private String pressure;
    private String wind;
    private String windDirection;
    private String cloudCover;
    private String visibility;

    private LocalDateTime updatedAt;
    private LocalDateTime lastAccessedAt;
    private int requestCount;

    private Double latitude;
    private Double longitude;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Double> hourlyTemperatures;

    public WeatherData() {
    }

    public WeatherData(String city, String temperature) {
        this.city = city;
        this.temperature = temperature;
        this.requestCount = 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }

    public String getFeelsLike() { return feelsLike; }
    public void setFeelsLike(String feelsLike) { this.feelsLike = feelsLike; }

    public String getDewPoint() { return dewPoint; }
    public void setDewPoint(String dewPoint) { this.dewPoint = dewPoint; }

    public String getPressure() { return pressure; }
    public void setPressure(String pressure) { this.pressure = pressure; }

    public String getWind() { return wind; }
    public void setWind(String wind) { this.wind = wind; }

    public String getWindDirection() { return windDirection; }
    public void setWindDirection(String windDirection) { this.windDirection = windDirection; }

    public String getCloudCover() { return cloudCover; }
    public void setCloudCover(String cloudCover) { this.cloudCover = cloudCover; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getLastAccessedAt() { return lastAccessedAt; }
    public void setLastAccessedAt(LocalDateTime lastAccessedAt) { this.lastAccessedAt = lastAccessedAt; }

    public int getRequestCount() { return requestCount; }
    public void setRequestCount(int requestCount) { this.requestCount = requestCount; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<Double> getHourlyTemperatures() { return hourlyTemperatures; }
    public void setHourlyTemperatures(List<Double> hourlyTemperatures) { this.hourlyTemperatures = hourlyTemperatures; }
}
