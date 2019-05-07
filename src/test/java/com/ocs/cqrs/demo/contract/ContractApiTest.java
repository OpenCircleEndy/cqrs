package com.ocs.cqrs.demo.contract;

import com.jayway.jsonpath.JsonPath;
import com.ocs.cqrs.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = {ContractApiTest.Initializer.class})
class ContractApiTest implements ApplicationContextAware {

    static ApplicationContext applicationContext;

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("contracts")
            .withUsername("test")
            .withPassword("secret");

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
    void whenAllContractsAreRequested() throws IOException {

        final String createLeadCommand = this.getResourceAsString("contract-api-create-lead.json");
        final String createContractCommand = this.getResourceAsString("contract-api-create-contract.json");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Given a list of relations.
        String getAllRelationsResponse = restTemplate.getForObject("/relations", String.class);
        String someRelationId = JsonPath.parse(getAllRelationsResponse).read("$[0].id");

        // Given a lead is created.
        this.restTemplate.postForEntity("/leads",
                new HttpEntity<>(createLeadCommand.replace("${relationId}", someRelationId), headers),
                String.class);

        // Given a create contract is requested.
        ResponseEntity<String> createContractResponse = this.restTemplate.postForEntity("/contracts",
                new HttpEntity<>(createContractCommand, headers),
                String.class);

        // Given the contract is created.
        assertEquals(HttpStatus.OK.value(), createContractResponse.getStatusCodeValue());

        // When all contracts are requested.
        String getAllContractsResponse = restTemplate.getForObject("/contracts", String.class);

        // Then this contains the new contract.
        assertTrue(getAllContractsResponse.contains("CUSTOMER"));
    }

    private String getResourceAsString(String fileName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(this.getResourceName(fileName)), Charset.defaultCharset());
    }

    private String getResourceName(String fileName) {
        return String.format("/%s/%s", this.getClass().getPackage().getName(), fileName);
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
