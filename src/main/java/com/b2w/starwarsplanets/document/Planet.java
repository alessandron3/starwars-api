package com.b2w.starwarsplanets.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document
public class Planet {

    @Id
    private String id;
    @NotEmpty(message = "name must not be empty")
    @Indexed(unique = true)
    private String name;
    @NotEmpty(message = "climate must not be empty")
    private String climate;
    @NotEmpty(message = "terrain must not be empty")
    private String terrain;
    private Long numberOfMovies;


    public Planet(String id, String name, String climate, String terrain) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Long getNumberOfMovies() {
        return numberOfMovies;
    }

    public void setNumberOfMovies(Long numberOfMovies) {
        this.numberOfMovies = numberOfMovies;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Planet{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", climate='").append(climate).append('\'');
        sb.append(", terrain='").append(terrain).append('\'');
        sb.append(", numberOfMovies='").append(numberOfMovies).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
