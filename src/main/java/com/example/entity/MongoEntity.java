package com.example.entity;

import org.bson.Document;

public interface MongoEntity {
    Document toDocument();
}
