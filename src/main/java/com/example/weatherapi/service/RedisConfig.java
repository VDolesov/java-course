package com.example.weatherapi.service;

import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientCustomizer() {
        return builder -> builder
                .commandTimeout(Duration.ofSeconds(5))
                .shutdownTimeout(Duration.ofMillis(100))
                .clientOptions(ClientOptions.builder()
                        .autoReconnect(true)
                        .build());
    }
}
