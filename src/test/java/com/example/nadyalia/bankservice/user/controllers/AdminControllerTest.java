package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.user.dto.ManagerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/admin";
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void addManagerSuccess() {
        // given
        String url = baseUrl + "/add-manager";
        String managerJson = """
                
                {
                    "firstName": "N",
                    "lastName": "L",
                    "username": "nl2",
                    "email": "nl2@gmail.com",
                    "password": "nl2",
                    "status": 1
                }
                
                """;
        headers.setBasicAuth("sl", "sl");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<ManagerResponseDTO> response = template.postForEntity(
                url,
                new HttpEntity<>(managerJson, headers),
                ManagerResponseDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("L", response.getBody().getManagerName());
    }
}