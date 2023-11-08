package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;
    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/auth";
    }

    @Test
    public void registerUserSuccess() {

        userRepository.renameAdmin("admin");
        userRepository.makeUsernameOld("sl");
        // given
        String url = baseUrl + "/signup";
        String signUpJson = "{" +
                "\"username\":\"sl\"," +
                "\"email\":\"sl@gmail.com\"," +
                "\"password\":\"sl\"" +
                "}";
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<String> response = template.postForEntity(
                url,
                new HttpEntity<>(signUpJson, headers),
                String.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("User registered successfully"));
    }

    @Test
    public void registerUserForbidden() {
        // given
        String url = baseUrl + "/signup";
        String signUpJson = "{" +
                "\"username\":\"sl\"," +
                "\"email\":\"sl@gmail.com\"," +
                "\"password\":\"sl\"" +
                "}";
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<String> response = template.postForEntity(
                url,
                new HttpEntity<>(signUpJson, headers),
                String.class
        );

        // then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().contains("Registration is not allowed. An admin user already exists."));
    }

    @Test
    public void authenticateUser() {
        // given
        String url = baseUrl + "/signin";
        String loginJson = "{" +
                "\"username\":\"sl\"," +
                "\"password\":\"sl\"" +
                "}";
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<String> response = template.postForEntity(
                url,
                new HttpEntity<>(loginJson, headers),
                String.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User signed-in successfully!", response.getBody());
    }
}