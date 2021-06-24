package com.example.controller;

import com.example.entity.SuperHero;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongodb.MongoTestResource;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(MongoTestResource.class)
@DisabledOnOs(OS.WINDOWS)
class SuperHeroControllerTest {
    @Test
    @DisplayName("Should verify the endpoints of superhero controller")
    public void testSuperHeroEndpoints() throws Exception {

        final var batman = new SuperHero("1", "batman", Set.of("brilliant", "combat-skills"));

        given()
                .header("Content-Type", "application/json")
                .body(batman)
                .post("/superheroes")
                .andReturn().then()
                .statusCode(201);


        given()
                .when().get("/superheroes/count")
                .then()
                .statusCode(200)
                .body(is("1"));

        final var expectedResponseBody = new ObjectMapper().writeValueAsString(List.of(batman));
        given().accept(ContentType.JSON)
                .get("/superheroes")
                .andReturn().then().body(is(equalTo(expectedResponseBody)));
    }

}