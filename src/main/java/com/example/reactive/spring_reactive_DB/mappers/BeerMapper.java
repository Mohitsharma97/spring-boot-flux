package com.example.reactive.spring_reactive_DB.mappers;

import com.example.reactive.spring_reactive_DB.domain.Beer;
import com.example.reactive.spring_reactive_DB.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
