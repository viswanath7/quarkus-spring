package com.example.repository;

import com.example.entity.SuperHero;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.mongodb.annotations.NotThreadSafe;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.Isolated;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.inject.Inject;
import java.util.Set;

import static com.example.repository.MongoDatabaseContainer.MONGODB_HOST;
import static com.example.repository.MongoDatabaseContainer.MONGODB_PORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@Testcontainers
@QuarkusTest
@NotThreadSafe
class SuperHeroRepositoryTest {

    @Inject
    SuperHeroRepository superHeroRepository;

    @Container
    static GenericContainer MONGO_DB_CONTAINER = new MongoDatabaseContainer()
            .withCreateContainerCmdModifier(command -> command.withHostName(MONGODB_HOST)
                    .withPortBindings(new PortBinding(Ports.Binding.bindPort(MONGODB_PORT), new ExposedPort(MONGODB_PORT))));

    final SuperHero batman = new SuperHero("first", "Batman", Set.of("brilliant", "combat-skills"));


    @Test
    @DisplayName("SuperHeroRepository should save, count and retrieve from database correctly")
    @Execution(SAME_THREAD)
    void should_save_and_retrieve_superheroes_correctly() {
        superHeroRepository.persistOrUpdate(batman);
        assertTrue(superHeroRepository.findByName("Batman").allMatch(superHero -> superHero.getName().equalsIgnoreCase("Batman")));
        assertThat(superHeroRepository.findByPowers("brilliant").findFirst().orElseThrow())
                .usingRecursiveComparison()
                .ignoringActualEmptyOptionalFields()
                .isEqualTo(batman);
        superHeroRepository.deleteAll();
    }
}