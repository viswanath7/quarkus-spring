package com.example.controller;

import com.example.entity.SuperHero;
import com.example.repository.SuperHeroRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/superheroes")
public class SuperHeroController {

    @Inject
    SuperHeroRepository superHeroRepository;

    @GET
    public List<SuperHero> list() {
        return superHeroRepository.list();
    }

    @GET
    @Path("count")
    public Long count() {
        return superHeroRepository.count();
    }

    @POST
    public Response add(SuperHero superHero) {
        final var insertionResult = superHeroRepository.insert(superHero);
        return insertionResult ? Response.status(CREATED).build(): Response.notModified().build();
    }

}
