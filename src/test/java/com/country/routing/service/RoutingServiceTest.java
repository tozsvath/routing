package com.country.routing.service;

import com.country.routing.model.Country;
import com.country.routing.search.RouteFinder;
import com.country.routing.validator.RoutingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RoutingServiceTest {

    private RoutingService underTest;

    @Mock
    private RoutingValidator routingValidator;
    @Mock
    private CartographyService cartographyService;
    @Mock
    private RouteFinder routeFinder;

    @Captor
    private ArgumentCaptor<Country> originCaptor;

    @Captor
    private ArgumentCaptor<Country> destinationCaptor;

    @Captor
    private ArgumentCaptor<Map<String, Country>> countriesCaptor;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoutingService(routingValidator, cartographyService, routeFinder);
    }

    @Test
    void findRouteShouldReturnProperRoute() {

        String origin = "CZE";
        String destination = "ITA";

        Map<String, Country> countryMap = populateMap();
        when(cartographyService.getCountries()).thenReturn((HashMap<String, Country>) countryMap);
        doNothing().when(routingValidator).validate(any(), any(), anyMap());
        when(routeFinder.findPath((countryMap.get(origin)), countryMap.get(destination), countryMap)).thenReturn(List.of("CZE", "AUT", "ITA"));


        List<String> actual = underTest.findRoute(origin, destination);

        verify(cartographyService).getCountries();
        verify(routingValidator).validate(any(), any(), anyMap());
        verify(routeFinder).findPath(originCaptor.capture(), destinationCaptor.capture(), countriesCaptor.capture());

        assertThat(originCaptor.getValue()).isEqualTo(new Country("CZE", "Europe", List.of("AUT", "DEU", "POL", "SVK")));
        assertThat(destinationCaptor.getValue()).isEqualTo(new Country("ITA", "Europe", List.of("AUT", "FRA", "SMR", "SVN", "CHE")));

    }

    private Map<String, Country> populateMap() {
        return new HashMap<String, Country>(Map.of(
                "CZE", new Country("CZE", "Europe", List.of("AUT", "DEU", "POL", "SVK")),
                "AUT", new Country("AUT", "Europe", List.of("CZE", "DEU", "HUN", "ITA", "LIE", "SVK", "SVN", "CHE")),
                "DEU", new Country("DEU", "Europe", List.of("AUT", "BEL", "CZE", "DNK", "FRA", "LUX", "NLD", "POL", "CHE")),
                "HUN", new Country("HUN", "Europe", List.of("AUT", "HRV", "ROU", "SRB", "SVK", "SVN", "UKR")),
                "ITA", new Country("ITA", "Europe", List.of("AUT", "FRA", "SMR", "SVN", "CHE")),
                "LIE", new Country("LIE", "Europe", List.of("AUT", "CHE")),
                "POL", new Country("POL", "Europe", List.of("BLR", "CZE", "DEU", "LTU", "RUS", "SVK", "UKR")),
                "SVK", new Country("SVK", "Europe", List.of("AUT", "CZE", "HUN", "POL", "UKR")),
                "SVN", new Country("SVN", "Europe", List.of("AUT", "HRV", "HUN", "ITA")),
                "CHE", new Country("CHE", "Europe", List.of("AUT", "DEU", "FRA", "ITA", "LIE"))
        ));
    }

}