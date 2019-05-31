package com.b2w.starwarsplanets.controller;

import com.b2w.starwarsplanets.document.Planet;
import com.b2w.starwarsplanets.service.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlanetService planetService;
    private Page<Planet> page;
    private Planet planet;

    @Before
    public void setUp() throws Exception {
        final ArrayList<Planet> planets = new ArrayList<>();
        planet = new Planet("5ceff6981baa986e0e36d164", "Naboo",
                "temparate", "grassy hills, swamps, forests, mountains");
        planets.add(planet);

        page = new PageImpl<Planet>(planets);

    }

    @Test
    public void listAllTest() throws Exception {

        when(planetService.getAllPlanets(0, 5)).thenReturn(page);
        this.mockMvc.perform(get("/planets"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content")));
    }

    @Test
    public void listAllByNameTest() throws Exception {
        when(planetService.getByName("Naboo", 0, 5)).thenReturn(page);
        this.mockMvc.perform(get("/planets?name=Naboo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Naboo")));
    }

    @Test
    public void searchByIdOkTest() throws Exception {
        when(planetService.getById("5ceff6981baa986e0e36d164")).thenReturn(Optional.of(planet));

        this.mockMvc.perform(get("/planets/5ceff6981baa986e0e36d164"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("5ceff6981baa986e0e36d164")));

    }

    @Test
    public void searchByIdNotFoundTest() throws Exception {
        when(planetService.getById("111111a")).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/planets/111111a"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createPlanetCreatedTest() throws Exception {
        Planet planetRequest = new Planet(null, "Nabooa", "temparate", "swamps");
        when(planetService.save(any())).thenReturn(planetRequest);
        this.mockMvc.perform(post("/planets")
                    .content(new ObjectMapper().writeValueAsString(planetRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    public void createPlanetBadRequestTest() throws Exception {
        Planet planetRequest = new Planet();
        when(planetService.save(any())).thenReturn(planetRequest);
        this.mockMvc.perform(post("/planets")
                .content(new ObjectMapper().writeValueAsString(planetRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("name must not be empty")))
                .andExpect(content().string(containsString("climate must not be empty")))
                .andExpect(content().string(containsString("terrain must not be empty")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removePlanetTest() throws Exception {
        when(planetService.delete("5ceff6981baa986e0e36d164")).thenReturn(true);

        this.mockMvc.perform(delete("/planets/5ceff6981baa986e0e36d164"))
                .andExpect(status().isOk());
    }

    @Test
    public void removePlanetNoContentTest() throws Exception {
        when(planetService.delete("123456")).thenReturn(false);

        this.mockMvc.perform(delete("/planets/123456"))
                .andExpect(status().isNoContent());
    }
}