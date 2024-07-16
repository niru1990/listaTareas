package com.repaso.listaTareas;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class AbstractIntegrationTest {
    static PostgreSQLContainer<?> container;
    static {
        try {
            DockerImageName imageName = DockerImageName
                    .parse("public.ecr.aws/docker/library/postgres:14-alpine")
                    .asCompatibleSubstituteFor("postgres:14-alpine");
            container = new PostgreSQLContainer<>(imageName);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}