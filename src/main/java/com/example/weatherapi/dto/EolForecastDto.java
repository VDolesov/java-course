package com.example.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EolForecastDto(
        @JsonProperty("dt_forecast") String dtForecast,
        @JsonProperty("temp_2_cel") Double temperature,
        @JsonProperty("temp_feels_cel") Double feelsLike,
        @JsonProperty("dew_point_cel") Double dewPoint,
        @JsonProperty("pres_surf") Double pressure,
        @JsonProperty("wind_speed_10") Double wind,
        @JsonProperty("wind_dir_10") String windDirection,
        @JsonProperty("vidimost_surf") Double visibility,
        @JsonProperty("oblachnost_atmo") Double cloudCover
) {}
