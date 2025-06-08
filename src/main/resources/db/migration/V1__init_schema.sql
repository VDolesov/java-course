CREATE TABLE weather_forecast (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(255),

    temperature VARCHAR(255),
    feels_like VARCHAR(255),
    dew_point VARCHAR(255),
    pressure VARCHAR(255),
    wind VARCHAR(255),
    wind_direction VARCHAR(255),
    cloud_cover VARCHAR(255),
    visibility VARCHAR(255),

    updated_at TIMESTAMP,
    last_accessed_at TIMESTAMP,
    request_count INTEGER
);

ALTER TABLE weather_forecast
ADD COLUMN latitude DOUBLE PRECISION,
ADD COLUMN longitude DOUBLE PRECISION;
