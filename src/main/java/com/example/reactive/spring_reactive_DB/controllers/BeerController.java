package com.example.reactive.spring_reactive_DB.controllers;

import com.example.reactive.spring_reactive_DB.model.BeerDTO;
import com.example.reactive.spring_reactive_DB.services.BeerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH+"/{beerId}";
    private final BeerService beerService;

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getbeerById(@PathVariable("beerId") Integer beerId){
        return beerService.getBeerById(beerId);
    }

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers(){
     return beerService.listBeers();
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO){
      return   beerService.saveNewBeer(beerDTO)
               .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
              .fromHttpUrl("http://localhost:8080/"+BEER_PATH
                      +"/"+savedDto.getId())
                       .build().toUri())
                       .build());

    }

    @PutMapping(BEER_PATH_ID)
    ResponseEntity<Void> updateExistingBeer(@PathVariable("beerId") Integer beerId,@Validated @RequestBody BeerDTO beerDTO){

        beerService.updateBeer(beerId,beerDTO).subscribe();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(@PathVariable("beerId") Integer beerId,@Validated @RequestBody BeerDTO beerDTO){
        return beerService.patchBeer(beerId,beerDTO)
                .map(updatedDto->ResponseEntity.ok().build());
    }

    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("beerId") Integer beerId){
        return beerService.deleteBeerById(beerId).map(response->ResponseEntity.noContent().build());
    }
}
