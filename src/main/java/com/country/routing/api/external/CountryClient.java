package com.country.routing.api.external;

import com.country.routing.config.ApiConfig;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@AllArgsConstructor
public class CountryClient {

    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;

    public String getCountryData() {

        final URI uri = URI.create(concatPath());

        return restTemplate.getForEntity(uri, String.class).getBody();
    }

    private String concatPath() {
        return String.format("%s%s", apiConfig.getUrl(), apiConfig.getPath());
    }

}