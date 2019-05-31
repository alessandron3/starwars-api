package com.b2w.starwarsplanets.service;


import com.b2w.starwarsplanets.swresponse.PlanetResult;
import com.b2w.starwarsplanets.swresponse.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StarsWarAPITest {

    private StarsWarAPI starsWarAPI;

    @Mock
    private RestTemplate restTemplate;

    private static String URL = "https://swapi.co/api/planets?search={planetName}";

    @Before
    public void setup() {
        starsWarAPI = new StarsWarAPI(this.restTemplate, URL);
    }


    @Test
    public void getNumberOfMoviesByPlanetNameJupterTest() {
        when(restTemplate.getForEntity(URL, PlanetResult.class, "Jupter"))
                .thenReturn(ResponseEntity.notFound().build());

        final Long number = starsWarAPI.getNumberOfMoviesByPlanetName("Jupter");

        Assert.assertNull(number);
    }


    @Test
    public void getNumberOfMoviesByPlanetNameDagobahTest() {

        PlanetResult planetResult = new PlanetResult();
        Result result = new Result();
        List<String> films = Arrays.asList("https://swapi.co/api/films/2/", "https://swapi.co/api/films/6/", "https://swapi.co/api/films/3/");
        result.setFilms(films);
        planetResult.setResults(Arrays.asList(result));

        when(restTemplate.getForEntity(URL, PlanetResult.class, "Dagobah"))
                .thenReturn(ResponseEntity.ok(planetResult));

        final Long number = starsWarAPI.getNumberOfMoviesByPlanetName("Dagobah");


        Assert.assertEquals(films.size(), number.intValue());
    }

}