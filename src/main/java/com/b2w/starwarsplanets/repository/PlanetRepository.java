package com.b2w.starwarsplanets.repository;

import com.b2w.starwarsplanets.document.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlanetRepository extends MongoRepository<Planet, String> {

    Page<Planet> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
