package com.example.weatherapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class YandexGeocoderService {

    @Value("${yandex.geocoder.api.key}")
    private String apiKey;

    @Value("${yandex.geocoder.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Coordinates> getCoordinatesByCity(String cityName) {
        String url = String.format("%s?apikey=%s&geocode=%s&format=json", apiUrl, apiKey, cityName);

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode posNode = response.getBody()
                    .path("response")
                    .path("GeoObjectCollection")
                    .path("featureMember")
                    .get(0)
                    .path("GeoObject")
                    .path("Point")
                    .path("pos");

            if (!posNode.isMissingNode()) {
                String[] coords = posNode.asText().split(" ");
                double lon = Double.parseDouble(coords[0]);
                double lat = Double.parseDouble(coords[1]);
                return Optional.of(new Coordinates(lat, lon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public record Coordinates(double lat, double lon) {}
}