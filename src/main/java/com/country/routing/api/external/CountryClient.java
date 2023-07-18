package com.country.routing.api.external;

import com.country.routing.config.ApiConfig;
import lombok.AllArgsConstructor;

import java.net.URI;


@AllArgsConstructor
public class CountryClient {

    private ApiConfig apiConfig;

    public String getCountryData() {

        final URI uri = URI.create(concatPath());

        return apiConfig.restTemplate().getForEntity(uri, String.class).getBody();
    }

    private String concatPath() {
        return String.format("%s%s", apiConfig.getUrl(), apiConfig.getPath());
    }

}