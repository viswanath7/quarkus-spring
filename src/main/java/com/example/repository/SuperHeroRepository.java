package com.example.repository;

import com.example.entity.MongoEntityFactory;
import com.example.entity.SuperHero;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SuperHeroRepository implements MongoRepository<SuperHero, MongoEntityFactory<SuperHero>> {

    private static final String SUPERHERO_COLLECTION_NAME = "superheroes";

    @Inject
    MongoClient mongoClient;

    @Value("${mongo.database.name:test!}")
    String databaseName;

    @Override
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getCollectionName() {
        return SUPERHERO_COLLECTION_NAME;
    }

    @Override
    public MongoEntityFactory<SuperHero> getFactory() {
        return new SuperHero.SuperHeroFactory();
    }
}
