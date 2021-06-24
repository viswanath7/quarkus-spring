package com.example;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public final class DatabaseUtility {

    public static Jsonb jsonBinding() {
        Jsonb jsonb = JsonbBuilder.create();
        final var mapper = new ObjectMapper() {
            @Override
            public Object deserialize(ObjectMapperDeserializationContext context) {
                return jsonb.fromJson(context.getDataToDeserialize().asString(), context.getType());
            }

            @Override
            public Object serialize(ObjectMapperSerializationContext context) {
                return jsonb.toJson(context.getObjectToSerialize());
            }
        };
        RestAssured.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapper(mapper));

        return jsonb;
    }

}
