package com.b2w.starwarsplanets.service;


import com.b2w.starwarsplanets.document.Planet;
import com.b2w.starwarsplanets.repository.PlanetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository, StarsWarAPI starsWarAPI){
        this.planetRepository = planetRepository;
    }

    public Page getAllPlanets(int page, int size) {
        return planetRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Planet> getById(String id) {
        return planetRepository.findById(id);
    }

    public Page getByName(String name, int page, int size) {
        return planetRepository.findByNameLikeIgnoreCase(name, PageRequest.of(page, size));
    }

    public Planet save(Planet planet) {
        return planetRepository.save(planet);
    }


    public boolean delete(String id) {
        final Optional<Planet> planetOptional = planetRepository.findById(id);
        if(planetOptional.isPresent()) {
            planetRepository.delete(planetOptional.get());
            return true;
        }
        return false;
    }
}
