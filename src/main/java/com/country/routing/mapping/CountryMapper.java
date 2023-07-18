package com.country.routing.mapping;

import com.country.routing.model.Country;
import com.country.routing.model.CountryDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "shortName", source = "cca3")
    @Mapping(target = "region", source = "region")
    Country convertToDomain(CountryDto countryDto);

    @IterableMapping(elementTargetType = Country.class)
    List<Country> convertToDomain(CountryDto[] countryDtoList);

}