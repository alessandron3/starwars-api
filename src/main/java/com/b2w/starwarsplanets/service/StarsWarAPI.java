package com.b2w.starwarsplanets.service;

import com.b2w.starwarsplanets.swresponse.PlanetResult;
import com.b2w.starwarsplanets.swresponse.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class StarsWarAPI {

    private RestTemplate restTemplate;

    private String url;

    public StarsWarAPI(RestTemplate restTemplate, @Value("${application.swapi.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Long getNumberOfMoviesByPlanetName(String planetName) {

        try {


            final ResponseEntity<PlanetResult> entity = restTemplate.getForEntity(url, PlanetResult.class, planetName);
            if (entity.getStatusCode().equals(HttpStatus.OK)) {

                final Result result = entity.getBody().getResults().stream().findFirst().get();
                return result.getFilms().stream().count();
            }
            return null;
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
