package com.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Set;

@Data
@NoArgsConstructor
@MongoEntity(collection="superheroes")
public class SuperHero extends PanacheMongoEntity {
    @JsonProperty
    private String identifier;
    @JsonProperty
    private String name;
    @JsonProperty
    private Set<String> powers;
}
