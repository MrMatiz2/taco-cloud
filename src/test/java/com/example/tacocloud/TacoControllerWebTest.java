package com.example.tacocloud;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TacoControllerWebTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void shouldReturnRecentTacos() throws IOException {
        testClient.get().uri("/api/tacos?recent")
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.id == 'TACO1')].name")
                .isEqualTo("Carnivore")
                .jsonPath("$[?(@.id == 'TACO2')].name")
                .isEqualTo("Bovine Bounty")
                .jsonPath("$[?(@.id == 'TACO3')].name")
                .isEqualTo("Veg-Out");
    }

}
