package com.country.routing.config;

import com.country.routing.api.external.CountryClient;
import com.country.routing.mapping.CountryMapper;
import com.country.routing.search.RouteFinder;
import com.country.routing.service.CartographyService;
import com.country.routing.service.RoutingService;
import com.country.routing.validator.PathInputValidator;
import com.country.routing.validator.RoutingValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingServiceConfig {

    @Bean
    public RoutingValidator routingValidator() {
        return new PathInputValidator();
    }

    @Bean
    public CountryMapper countryMapper() {
        return CountryMapper.INSTANCE;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RouteFinder routeFinder() {
        return new RouteFinder();
    }

    @Bean
    public CountryClient countryClient(final ApiConfig apiConfig) {
        return new CountryClient(apiConfig);
    }

    @Bean
    public CartographyService cartographyService(final CountryMapper countryMapper,
                                                 final ObjectMapper objectMapper,
                                                 final CountryClient countryClient) {

        return new CartographyService(countryMapper, objectMapper, countryClient);
    }

    @Bean
    public RoutingService routingService(final RoutingValidator routingValidator,
                                         final CartographyService cartographyService,
                                         final RouteFinder routeFinder) {

        return new RoutingService(routingValidator, cartographyService, routeFinder);
    }
}
