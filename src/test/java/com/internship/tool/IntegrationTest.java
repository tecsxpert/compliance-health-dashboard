package com.internship.tool;

import com.internship.tool.entity.Compliance;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.GenericContainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.TimeZone;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@ActiveProfiles("test")
public class IntegrationTest {

    // ✅ Force JVM timezone
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    // ✅ PostgreSQL container (CLEAN)
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("postgres")
                    .withPassword("postgres");

    // ✅ Redis container
    @Container
    static GenericContainer<?> redis =
            new GenericContainer<>("redis:7")
                    .withExposedPorts(6379);

    // ✅ Dynamic properties
    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name",
                () -> "org.postgresql.Driver");

        registry.add("spring.jpa.properties.hibernate.jdbc.time_zone",
                () -> "UTC");

        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port",
                () -> redis.getMappedPort(6379));
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFullCrudFlow() throws Exception {

        Compliance newComp = new Compliance();
        newComp.setTitle("Integration Test");
        newComp.setStatus("PENDING");
        newComp.setDescription("Test Desc");

        // CREATE
        MvcResult createResult = mockMvc.perform(post("/api/compliance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newComp)))
                .andExpect(status().isOk())
                .andReturn();

        Compliance created = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                Compliance.class
        );

        Long id = created.getId();
        assertNotNull(id);

        // READ
        mockMvc.perform(get("/api/compliance?page=0&size=5"))
                .andExpect(status().isOk());

        // UPDATE
        newComp.setTitle("Updated Integration");

        mockMvc.perform(put("/api/compliance/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newComp)))
                .andExpect(status().isOk());

        // DELETE
        mockMvc.perform(delete("/api/compliance/" + id))
                .andExpect(status().isNoContent());
    }
}