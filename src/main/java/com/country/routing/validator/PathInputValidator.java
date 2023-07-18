package com.country.routing.validator;


import com.country.routing.exception.NoLandRouteFoundException;
import com.country.routing.exception.NoSuchCountryException;
import com.country.routing.model.Country;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PathInputValidator implements RoutingValidator {

    private final Set<String> EMEA_AND_ASIA = Set.of("Asia", "Europe", "Africa", "ASIA", "EUROPE", "AFRICA");

    @Override
    public void validate(final String origin, final String destination, final Map<String, Country> countries) {
        Country originalCountry = countries.get(origin);
        Country destinationCountry = countries.get(destination);

        Optional.ofNullable(originalCountry).orElseThrow(() -> new NoSuchCountryException("Origin country not found"));
        Optional.ofNullable(destinationCountry).orElseThrow(() -> new NoSuchCountryException("Destination country not found"));
        Optional.ofNullable(originalCountry.getRegion()).orElseThrow(() -> new NoSuchCountryException("Origin country region not found"));
        Optional.ofNullable(destinationCountry.getRegion()).orElseThrow(() -> new NoSuchCountryException("Destination country region not found"));

        if (!isWalkable(originalCountry, destinationCountry)) {
            throw new NoLandRouteFoundException("There is no way to get to the destination");
        }
    }

    private boolean isWalkable(final Country originalCountry, final Country destinationCountry) {
        return originalCountry.getRegion().equals(destinationCountry.getRegion())
                || isConnectedByLand(originalCountry, destinationCountry);
    }

    private boolean isConnectedByLand(final Country originalCountry, final Country destinationCountry) {
        return EMEA_AND_ASIA.contains(originalCountry.getRegion()) && EMEA_AND_ASIA.contains(destinationCountry.getRegion());
    }
}
