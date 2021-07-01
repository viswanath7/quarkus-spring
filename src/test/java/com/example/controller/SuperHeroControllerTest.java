package com.example.controller;

import com.example.entity.SuperHero;
import com.mongodb.annotations.NotThreadSafe;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongodb.MongoTestResource;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.Isolated;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@QuarkusTest
@QuarkusTestResource(MongoTestResource.class)
@DisabledOnOs(OS.WINDOWS)
@NotThreadSafe
class SuperHeroControllerTest {

    @Test
    @DisplayName("Should verify the endpoints of superhero controller")
    @Execution(SAME_THREAD)
    public void testSuperHeroEndpoints() throws Exception {

        final var batman = new SuperHero("second", "flash", Set.of("speed"));

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

        given().accept(ContentType.JSON)
                .get("/superheroes")
                .andReturn()
                .then()
                .body(containsStringIgnoringCase("\"identifier\":\"second\""),
                        containsStringIgnoringCase("\"name\":\"flash\""),
                        containsStringIgnoringCase("\"powers\":[\"speed\"]")
                );
    }

}