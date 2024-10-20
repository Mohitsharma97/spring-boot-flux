package com.example.reactive.spring_reactive_DB.repositories;

import com.example.reactive.spring_reactive_DB.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


public interface BeerRepository extends ReactiveCrudRepository<Beer,Integer> {
}
