package com.example.reactive.spring_reactive_DB.services;

import com.example.reactive.spring_reactive_DB.mappers.BeerMapper;
import com.example.reactive.spring_reactive_DB.model.BeerDTO;
import com.example.reactive.spring_reactive_DB.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return beerRepository.findById(beerId).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
     return    beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                    foundBeer.setBeerName(beerDTO.getBeerName());
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    foundBeer.setPrice(beerDTO.getPrice());
                    foundBeer.setUpc(beerDTO.getUpc());
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId).map(foundBear->{
            if(StringUtils.hasText(beerDTO.getBeerName())){
                foundBear.setBeerName(beerDTO.getBeerName());
            }
            if(StringUtils.hasText(beerDTO.getBeerStyle())){
                foundBear.setBeerStyle(beerDTO.getBeerStyle());
            }
            if(beerDTO.getPrice() != null){
                foundBear.setPrice(beerDTO.getPrice());
            }
            if(StringUtils.hasText(beerDTO.getUpc())){
                foundBear.setUpc(beerDTO.getUpc());
            }
            if(beerDTO.getQuantityOnHand()!=null){
                foundBear.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            return foundBear;
        }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Void> deleteBeerById(Integer beerId) {
        return beerRepository.deleteById(beerId);
    }
}
