package com.example.repository;

import org.testcontainers.containers.GenericContainer;
import javax.validation.constraints.NotNull;

public class MongoDatabaseContainer extends GenericContainer<MongoDatabaseContainer> {

    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.2.6";
    public static final String MONGODB_HOST = "localhost";
    public static final int MONGODB_PORT = 27017;

    public MongoDatabaseContainer() {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    public MongoDatabaseContainer(@NotNull String image) {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    @NotNull
    public Integer getPort() {
        return getMappedPort(MONGODB_PORT);
    }
}
