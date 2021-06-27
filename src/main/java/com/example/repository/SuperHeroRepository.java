package com.example.repository;

import com.example.entity.SuperHero;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

@ApplicationScoped
public class SuperHeroRepository implements PanacheMongoRepository<SuperHero> {

    public Stream<SuperHero> findByName(@NonNull final String name){
        return find("name", name).stream().distinct();
    }

    public Stream<SuperHero> findByPowers(@NonNull final String... power){
        return find("powers", power).stream().distinct();
    }

    public void deleteLoics(){
        delete("name", "Loïc");
    }

}
