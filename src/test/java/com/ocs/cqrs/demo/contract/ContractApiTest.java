package com.ocs.cqrs.demo.contract;

import com.ocs.cqrs.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = {ContractApiTest.Initializer.class})
class ContractApiTest implements ApplicationContextAware {

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("contracts")
            .withUsername("test")
            .withPassword("secret");

    static ApplicationContext applicationContext;
    @Autowired
    private TestRestTemplate restTemplate;

    @AfterAll
    static void helpSpring() {
        SpringApplication.exit(applicationContext, () -> 0);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    @Test
    void whenAllContractsAreRequested() {

        // Given a create contract is requested.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"type\": \"CUSTOMER\"}", headers);
        ResponseEntity<String> createContractResponse = this.restTemplate.postForEntity("/contracts", request, String.class);

        // Given the contract is created.
        assertEquals(HttpStatus.CREATED.value(), createContractResponse.getStatusCodeValue());

        // When all contracts are requested.
        String getAllContractsResponse = restTemplate.getForObject("/contracts", String.class);

        // Then this contains the new contract.
        assertTrue(getAllContractsResponse.contains("CUSTOMER"));
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
