package com.example.nadyalia.bankservice.manager.controllers;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.client.dto.ClientDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ManagerControllerTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private String baseUrl;
    @Autowired
    private UserRepository userRepository;
    private String jsonBody = "{" +
            "\"firstName\":\"tony\"," +
            "\"lastName\":\"stark\"," +
            "\"username\":\"tonystark\"," +
            "\"email\":\"tony@gmail.com\"," +
            "\"password\":\"tony\"," +
            "\"type\":\"client\"," +
            "\"address\":\"333 Akl St\"," +
            "\"phoneNumber\":\"333-444-5555\"" +
            "}";

    private String accountJson = "{" +
            "\"name\":\"Business Plus Account\"," +
            "\"type\":1," +
            "\"status\":1," +
            "\"currencyCode\":840," +
            "\"clientId\":\"ecc1bb19-6c17-11ee-a809-9cb6d0ff6637\"" +
            "}";

    private String updateAccountJson = "{" +
            "\"name\":\"Premium Business Account\"," +
            "\"type\":1," +
            "\"status\":1," +
            "\"currencyCode\":840," +
            "\"balance\":200.00," +
            "\"id\":\"d47ac10b-58cc-4372-a567-0e02b2c3d481\"" +
            "}";

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/manager";
    }

    @Test
    public void getClientsWithoutAuthentication() {
        // given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = baseUrl + "/all-clients";

        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatusCode expected = HttpStatus.UNAUTHORIZED;
        HttpStatusCode actual = response.getStatusCode();

        // then
        assertEquals(expected, actual);
        assertNull(response.getBody());
    }

    @Test
    public void getClientsWithAuthentication() {
        // given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = baseUrl + "/all-clients";

        // when
        ResponseEntity<String> response = template.withBasicAuth("nl2", "nl2")
                .exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatusCode expected = HttpStatus.OK;
        HttpStatusCode actual = response.getStatusCode();

        // TODO: proverit na imena tolko kto postojanno


        String expectedBody = "[" +
                "{" +
                "\"id\":\"ecc1bb19-6c17-11ee-a809-9cb6d0ff6637\"," +
                "\"status\":1," +
                "\"taxCode\":\"TX12345\"," +
                "\"firstName\":\"Anna\"," +
                "\"lastName\":\"Smith\"," +
                "\"userId\":\"34092502-2772-4d71-b52e-f03b44bf6046\"," +
                "\"email\":\"anna@email.com\"," +
                "\"address\":\"321 Gold St\"," +
                "\"phoneNumber\":\"123-456-7890\"," +
                "\"createDate\":\"2023-11-06T19:29:04\"," +
                "\"updateDate\":\"2023-11-06T19:29:04\"" +
                "}," +
                "{" +
                "\"id\":\"ecc1c05a-6c17-11ee-a809-9cb6d0ff6637\"," +
                "\"status\":1," +
                "\"taxCode\":\"TX67890\"," +
                "\"firstName\":\"Bobby\"," +
                "\"lastName\":\"Kit\"," +
                "\"userId\":\"1bf109b9-7333-4d19-8ee8-da23abfd588d\"," +
                "\"email\":\"bob@email.com\"," +
                "\"address\":\"456 Elm St\"," +
                "\"phoneNumber\":\"987-654-3210\"," +
                "\"createDate\":\"2023-11-06T19:29:04\"," +
                "\"updateDate\":\"2023-11-06T19:29:04\"" +
                "}" +
                "]";

        String actualBody = response.getBody();

        // then
        assertEquals(expected, actual);
        //assertEquals(expectedBody, actualBody);
    }

    @Test
    public void addClientWithoutRequiredRole() {

        // given
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        String url = baseUrl + "/add-client";

        // when
        ResponseEntity<String> response = template.withBasicAuth("sl", "sl")
                .exchange(url, HttpMethod.POST, entity, String.class);

        HttpStatusCode expected = HttpStatus.FORBIDDEN;
        HttpStatusCode actual = response.getStatusCode();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void addClientWithAuthentication() {

        userRepository.makeUsernameOld("tonystark");

        // given
        String url = baseUrl + "/add-client";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<ClientResponseDTO> response = template.postForEntity(
                url,
                new HttpEntity<>(jsonBody, headers),
                ClientResponseDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("tony", response.getBody().getClientInfo().getFirstName());

        response.getBody().getClientInfo().getId();
    }

    @Test
    public void getClientById() {
        // given
        UUID clientId = UUID.fromString("ecc1bb19-6c17-11ee-a809-9cb6d0ff6637");
        String url = baseUrl + "/client/" + clientId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<ClientDTO> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                ClientDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clientId, response.getBody().getId());
    }

    @Test
    public void deleteClientById() {
        // given
        UUID clientId = UUID.fromString("bf38336f-8809-428b-9af6-1e7fdaed42cb");
        String url = baseUrl + "/client/" + clientId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<Void> response = template.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getClientCount() {
        // given
        String url = baseUrl + "/client-count";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<Integer> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Integer.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() > 0);
    }

    @Test
    public void getAllActiveClients() {
        // given
        String url = baseUrl + "/client-active";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void getAllClientsWhereTransactionMoreThan() {
        // given
        int transactionCount = 1;
        String url = baseUrl + "/client/transaction/" + transactionCount;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getAllAccounts() {
        // given
        String url = baseUrl + "/all-accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void createAccount() {
        // given
        String url = baseUrl + "/create-account";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.postForEntity(
                url,
                new HttpEntity<>(accountJson, headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteAccountById() {
        // given
        UUID accountId = UUID.fromString("ecc1bb19-6c17-11ee-a809-9cb6d0ff6637");
        String url = baseUrl + "/account/delete/" + accountId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<Void> response = template.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateAccount() {
        // given
        String url = baseUrl + "/update-account";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updateAccountJson, headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getAllAccountsWhereStatusIs() {
        // given
        int status = 1;
        String url = baseUrl + "/account-status/" + status;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAccountsWhereProductIdIsAndStatusIs() {
        // given
        int productId = 1;
        int status = 1;
        String url = baseUrl + "/account/" + productId + "/" + status;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAllTransactionsWhereClientIdIs() {
        // given
        UUID clientId = UUID.fromString("ecc1bb19-6c17-11ee-a809-9cb6d0ff6637");
        String url = baseUrl + "/transaction/" + clientId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAllTransactionsWhereAccountCurrencyIs() {
        // given
        int currency = 840;
        String url = baseUrl + "/transaction/currency/" + currency;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAllProductsWhereAgreementsQuantityMoreThan() {
        // given
        int quantity = 1;
        String url = baseUrl + "/product/agreements/" + quantity;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAllChangedProducts() {
        // given
        String url = baseUrl + "/product/changed";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAgreementsWhereManagerIDIs() {
        // given
        int managerId = 1;
        String url = baseUrl + "/agreement/manager/" + managerId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void findAgreementsWhereClientIdIs() {
        // given
        UUID clientId = UUID.fromString("ecc1bb19-6c17-11ee-a809-9cb6d0ff6637");
        String url = baseUrl + "/agreement/client/" + clientId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("nl2", "nl2");

        // when
        ResponseEntity<List> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}