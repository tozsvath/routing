package com.country.routing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    @JsonProperty("cca3")
    private String shortName;
    @JsonProperty("region")
    private String region;
    @JsonProperty("borders")
    private List<String> neighbors;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Country(final String shortName, final String region, final List<String> neighbors) {
        this.shortName = shortName;
        this.region = region;
        this.neighbors = neighbors;
    }

}