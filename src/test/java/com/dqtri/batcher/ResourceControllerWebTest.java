package com.dqtri.batcher;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.repository.ResourceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.dqtri.batcher.model.enums.Status.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers // activate automatic startup and stop of containers
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop", // JPA drop and create table, good for testing
})
class ResourceControllerWebTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    ResourceRepository resourceRepository;

    //RestAssured is a completely different framework. This has nothing to do with Spring. This is a library, which provides various ways to test any REST service with fluent BDD style interface.
    // static, all tests share this postgres container
    @SuppressWarnings("resource")
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("batcher-postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("schema/init.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();

    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    Resource resourceA = new Resource("Resource A", ACTIVE);
    Resource resourceB = new Resource("Resource A", ACTIVE);
    Resource resourceC = new Resource("Resource A", ACTIVE);
    Resource resourceD = new Resource("Resource A", ACTIVE);
    Resource resourceE = new Resource("Resource A", ACTIVE);
    List<Resource> resources = List.of(resourceA, resourceB, resourceC, resourceD, resourceE);

    @BeforeEach
    void setUp() {
        resourceRepository.deleteAll();
        resourceRepository.saveAll(resources);
    }
    
    @Test
    void tryHard() {
        List<Resource> all = resourceRepository.findAll();
        assertThat(all).hasSize(resources.size());
        assertThat(port).isPositive();
    }
}