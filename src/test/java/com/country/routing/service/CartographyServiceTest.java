package com.country.routing.service;

import com.country.routing.api.external.CountryClient;
import com.country.routing.mapping.CountryMapper;
import com.country.routing.model.Country;
import com.country.routing.model.CountryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CartographyServiceTest {

    @Mock
    private CountryMapper countryMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CountryClient countryClient;

    @ExtendWith(MockitoExtension.class)
    private CartographyService underTest;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        underTest = new CartographyService(countryMapper, objectMapper, countryClient);
    }

    @Test
    void testShouldReturnCountryData() throws JsonProcessingException {
        CountryDto[] countryDtos = new CountryDto[1];
        countryDtos[0] = new CountryDto("test1", "testRegion", List.of("test1", "test2"));

        when(countryClient.getCountryData()).thenReturn("test");
        when(objectMapper.readValue("test", CountryDto[].class)).thenReturn(countryDtos);
        Country country1 = new Country("test1", "testRegion", List.of("test1", "test2"));
        Country country2 = new Country("test2", "testRegion", List.of("test1", "test2"));

        when(countryMapper.convertToDomain(countryDtos)).thenReturn(List.of(
                country1,
                country2));

        Map<String, Country> actual = underTest.getCountries();

        assertThat(actual).isNotNull();
        assertThat(actual).size().isEqualTo(2);
        assertThat(country1.getShortName()).isNotNull();
        assertThat(actual.get(country2.getShortName())).isNotNull();
        assertThat(actual.get(country1.getShortName())).isEqualTo(country1);
        assertThat(actual.get(country2.getShortName())).isEqualTo(country2);
    }

}