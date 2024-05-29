package com.nahorniak.inventorymanagementservice.configuration;

import com.nahorniak.inventorymanagementservice.service.fetchers.PredictionFetcher;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(PredictionServiceAutoConfiguration.PredictionServiceProperties.class)
public class PredictionServiceAutoConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    PredictionFetcher predictionFetcher(RestTemplate restTemplate, PredictionServiceProperties predictionServiceProperties) {
        return new PredictionFetcher(predictionServiceProperties.getUrl(), restTemplate);
    }

    @Data
    @ConfigurationProperties(value = "remote.prediction-service", ignoreUnknownFields = false)
    public static class PredictionServiceProperties {
        private URI url;
    }
}
