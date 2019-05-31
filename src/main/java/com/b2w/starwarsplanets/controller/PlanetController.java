package com.b2w.starwarsplanets.controller;


import com.b2w.starwarsplanets.document.Planet;
import com.b2w.starwarsplanets.service.PlanetService;
import com.b2w.starwarsplanets.service.StarsWarAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/planets")
@Api(value = "Planets")
public class PlanetController {

    private PlanetService planetService;
    private StarsWarAPI starsWarAPI;

    public PlanetController(PlanetService planetService, StarsWarAPI starsWarAPI) {
        this.planetService = planetService;
        this.starsWarAPI = starsWarAPI;
    }

    @ApiOperation(value = "List all Planets")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Planet> listAll(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {

        if(name != null) {
            return planetService.getByName(name, page, size);
        }

        return planetService.getAllPlanets(page, size);
    }

    @ApiOperation(value = "Search a Planet by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Planet> searchById(@PathVariable(value = "id") String id) {

        final Optional<Planet> optionalPlanet = planetService.getById(id);

        if(optionalPlanet.isPresent()){
            return ResponseEntity.ok(optionalPlanet.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create a new Planet")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Planet> createPlanet(@Valid @RequestBody Planet planetRequest, UriComponentsBuilder builder) throws URISyntaxException {

        planetRequest.setNumberOfMovies(starsWarAPI.getNumberOfMoviesByPlanetName(planetRequest.getName()));

        planetRequest = planetService.save(planetRequest);
        UriComponents uriComponents = builder.path("/planet/{id}").buildAndExpand(planetRequest.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ApiOperation(value = "Remove a planet")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removePlanet(@PathVariable(value = "id") String id) {

        if (planetService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }


}
