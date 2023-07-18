package com.country.routing.service;

import com.country.routing.api.external.CountryClient;
import com.country.routing.mapping.CountryMapper;
import com.country.routing.model.Country;
import com.country.routing.model.CountryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CartographyService {

    private final CountryMapper countryMapper;
    private final ObjectMapper objectMapper;
    private final CountryClient countryClient;

    public CartographyService(final CountryMapper countryMapper,
                              final ObjectMapper objectMapper,
                              final CountryClient countryClient) {
        this.countryClient = countryClient;
        this.countryMapper = countryMapper;
        this.objectMapper = objectMapper;
    }

    public HashMap<String, Country> getCountries() {

        final String jsonResponse = countryClient.getCountryData();

        final List<Country> countryList = countryMapper.convertToDomain(parseCountries(jsonResponse));
        return toMap(countryList);
    }

    private CountryDto[] parseCountries(final String jsonResponse) {
        try {
            return objectMapper.readValue(jsonResponse, CountryDto[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<String, Country> toMap(final List<Country> countryList) {
        return countryList.stream()
                .collect(Collectors.toMap(
                        Country::getShortName,
                        Function.identity(),
                        (existingValue, newValue) -> existingValue,
                        HashMap::new
                ));
    }
}
