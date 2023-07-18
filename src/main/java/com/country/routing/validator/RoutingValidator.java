package com.country.routing.validator;

import com.country.routing.model.Country;

import java.util.Map;

public interface RoutingValidator {

    void validate(final String origin, final String destination, final Map<String, Country> countries);

}
