package com.country.routing.search;

import com.country.routing.exception.NoLandRouteFoundException;
import com.country.routing.exception.NoSuchCountryException;
import com.country.routing.model.Country;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class RouteFinder {

    public List<String> findPath(final Country origin, final Country destination, final Map<String, Country> countries) {

        final Queue<Country> countryQueue = new ArrayDeque<>();
        final HashSet<String> visitedCountries = new LinkedHashSet<>();
        final Map<Country, Country> parentMap = new HashMap<>();

        Country country = origin;

        visitedCountries.add(country.getShortName());
        countryQueue.add(country);

        outerLoop:
        while (!countryQueue.isEmpty()) {
            country = countryQueue.remove();
            visitedCountries.add(country.getShortName());
            if (destination.equals(country)) {
                break;
            }
            for (String borderCountryName : country.getNeighbors()) {
                Country borderCountry = countries.get(borderCountryName);

                Optional.ofNullable(borderCountry).orElseThrow(() ->
                        new NoSuchCountryException("There is no such country"));

                if (!isVisited(visitedCountries, borderCountry)) {
                    visitedCountries.add(borderCountry.getShortName());
                    countryQueue.add(borderCountry);
                    parentMap.put(borderCountry, country);

                    if (isRouteFound(destination, borderCountry)) {
                        country = borderCountry;
                        break outerLoop;
                    }

                }
            }
        }

        if (!landRouteFound(destination, country)) {
            throw new NoLandRouteFoundException("There is no way to get to the destination");
        }

        return recalculateRoute(destination, parentMap);
    }

    private List<String> recalculateRoute(final Country destination, Map<Country, Country> parentMap) {
        final List<String> path = new ArrayList<>();
        Country country = destination;

        while (country != null) {
            path.add(country.getShortName());
            country = parentMap.get(country);
        }

        Collections.reverse(path);
        return path;
    }

    private boolean landRouteFound(Country destination, Country country) {
        return country.equals(destination);
    }

    private boolean isRouteFound(Country destination, Country recentCountry) {
        return recentCountry.equals(destination);
    }

    private boolean isVisited(HashSet<String> wasVisited, Country recentCountry) {
        return wasVisited.contains(recentCountry.getShortName());
    }
}