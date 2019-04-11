package com.ocs.cqrs.demo.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {ContractRepositoryTest.Initializer.class})
class ContractRepositoryTest {

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("contracts")
            .withUsername("test")
            .withPassword("secret");

    @Autowired
    private ContractRepository subject;

    @Test
    void whenAllContractsAreRequested() throws SQLException {
        assertEquals(0, this.subject.count());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "flyway.baselineOnMigrate=true",
                    "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
