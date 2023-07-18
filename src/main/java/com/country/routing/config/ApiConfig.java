package com.country.routing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "external.country.api")
@Getter
@Setter
public class ApiConfig {
    private String url;
    private String path;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    ;
}
