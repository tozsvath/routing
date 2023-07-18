package com.country.routing.search;

import com.country.routing.exception.NoLandRouteFoundException;
import com.country.routing.model.Country;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RouteFinderTest {

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
    private RouteFinder underTest;

    @Test
    void testShouldFindPathForNeighbours() {
        underTest = new RouteFinder();

        var actual = underTest.findPath(WORLD_MAP.get("hun"), WORLD_MAP.get("aut"), WORLD_MAP);

        assertThat(actual).isEqualTo(List.of("hun", "aut"));
    }


    @Test
    void testShouldFindPathShouldReturnListOfPath() {
        underTest = new RouteFinder();

        var actual = underTest.findPath(WORLD_MAP.get("hun"), WORLD_MAP.get("deu"), WORLD_MAP);

        assertThat(actual).isEqualTo(List.of("hun", "aut", "deu"));
    }

    @Test
    void testShouldThrowErrorWhenNoPathFound() {
        underTest = new RouteFinder();

        assertThrows(NoLandRouteFoundException.class, () -> {
            underTest.findPath(WORLD_MAP.get("hun"), WORLD_MAP.get("usa"), WORLD_MAP);
        });
    }

    @Test
    void testShouldFindPathShouldReturnListOfPathOnDifferentRegion() {
        underTest = new RouteFinder();

        var actual = underTest.findPath(WORLD_MAP.get("hun"), WORLD_MAP.get("mon"), WORLD_MAP);

        assertThat(actual).isEqualTo(List.of("hun", "aut", "deu", "mon"));
    }

    @Test
    void testShouldFindPathShouldReturnListOfPathOnMultipleNeighbours() {
        underTest = new RouteFinder();

        var actual = underTest.findPath(WORLD_MAP.get("hun"), WORLD_MAP.get("mon"), WORLD_MAP);

        assertThat(actual).isEqualTo(List.of("hun", "aut", "deu", "mon"));
    }
}