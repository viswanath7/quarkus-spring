package com.example.entity;

import lombok.NonNull;
import org.bson.Document;

public interface MongoEntityFactory <T extends MongoEntity> {
   T fromDocument(@NonNull final Document document);
}
