package com.country.routing.service;

import com.country.routing.model.Country;
import com.country.routing.search.RouteFinder;
import com.country.routing.validator.RoutingValidator;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RoutingService {

    private final RoutingValidator routingValidator;
    private final CartographyService cartographyService;
    private final RouteFinder routeFinder;


    public List<String> findRoute(final String origin, final String destination) {
        Map<String, Country> countries = cartographyService.getCountries();
        routingValidator.validate(origin, destination, countries);

        return routeFinder.findPath(countries.get(origin), countries.get(destination), countries);
    }


}
