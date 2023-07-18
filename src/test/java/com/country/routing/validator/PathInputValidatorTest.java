package com.country.routing.validator;

import com.country.routing.exception.NoLandRouteFoundException;
import com.country.routing.exception.NoSuchCountryException;
import com.country.routing.model.Country;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PathInputValidatorTest {

    private static final Map<String, Country> WORLD_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Country>("hun", new Country("hun", "europe", List.of("aut"))),
            new AbstractMap.SimpleEntry<String, Country>("deu", new Country("deu", "europe", List.of("aut", "mon"))),
            new AbstractMap.SimpleEntry<String, Country>("aut", new Country("aut", "europe", List.of("hun", "deu"))),
            new AbstractMap.SimpleEntry<String, Country>("cze", new Country("cze", "europe", List.of("aut", "deu"))),
            new AbstractMap.SimpleEntry<String, Country>("mon", new Country("mon", "asia", List.of("cze", "deu"))),
            new AbstractMap.SimpleEntry<String, Country>("esp", new Country("esp", "europe", List.of("prt", "and", "fra"))),
            new AbstractMap.SimpleEntry<String, Country>("chn", new Country("chn", "asia", List.of("afg", "btn", "mmr", "hkg", "ind", "kaz", "kgz", "laos", "mac", "mng", "npl", "prk", "tjk", "vnm"))),
            new AbstractMap.SimpleEntry<String, Country>("ind", new Country("ind", "asia", List.of("afg", "bgd", "btn", "mmr", "chn", "npl", "lka", "pak"))),
            new AbstractMap.SimpleEntry<String, Country>("usa", new Country("usa", "america", List.of("can", "mex")))
    );
    private PathInputValidator underTest;

    @Test
    void testShouldNotThrowExceptionWhenOriginAndDestinationAreValid() {
        underTest = new PathInputValidator();
        String origin = "hun";
        String destination = "deu";

        underTest.validate(origin, destination, WORLD_MAP);

    }

    @Test
    void testShouldhrowExceptionWhenOriginIsInValid() {
        underTest = new PathInputValidator();
        String origin = "invalid";
        String destination = "deu";

        assertThrows(NoSuchCountryException.class, () -> {
            underTest.validate(origin, destination, WORLD_MAP);
        });
    }

    @Test
    void testShouldhrowExceptionWhenDestinationIsInvalid() {
        underTest = new PathInputValidator();
        String origin = "deu";
        String destination = "invalid";

        assertThrows(NoSuchCountryException.class, () -> {
            underTest.validate(origin, destination, WORLD_MAP);
        });
    }

    @Test
    void testShouldhrowExceptionWhenNoLandRountFound() {
        underTest = new PathInputValidator();
        String origin = "hun";
        String destination = "usa";

        assertThrows(NoLandRouteFoundException.class, () -> {
            underTest.validate(origin, destination, WORLD_MAP);
        });
    }

}