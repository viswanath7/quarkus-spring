package com.example.entity;

import lombok.*;
import org.bson.Document;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuperHero implements MongoEntity {
    @Id
    @ToString.Exclude
    private String id;
    private String name;
    private Set<String> powers;

    @Override
    public Document toDocument() {
        final var document = new Document();
        document.append("id", getId());
        document.append("name", getName());
        document.append("powers", new ArrayList<>(getPowers()));
        return document;
    }

    public static class SuperHeroFactory implements MongoEntityFactory<SuperHero> {
        public SuperHero fromDocument(@NonNull final Document document) {
            final var id = document.getString("id");
            final var name = document.getString("name");
            final var powers = new HashSet<>(document.getList("powers", String.class));
            return new SuperHero(id,name,powers);
        }
    }
}