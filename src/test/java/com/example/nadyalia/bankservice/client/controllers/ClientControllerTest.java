package com.example.nadyalia.bankservice.client.controllers;

import com.example.nadyalia.bankservice.account.dto.AccountDTO;
import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/client";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("tonystark", "tony");
    }

    @Test
    public void getAccountsByClientIdSuccess() {
        // given
        String url = baseUrl + "/account";

        // when
        ResponseEntity<List<AccountDTO>> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<AccountDTO>>() {}
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void balanceCheckSuccess() {
        // given
        UUID accountId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        String url = baseUrl + "/account/check-balance/" + accountId;

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void creditAccountSuccess() {
        // given
        String url = baseUrl + "/transaction/credit";
        CreditDebitRequestDTO requestDTO = new CreditDebitRequestDTO();
        requestDTO.setAmount(BigDecimal.valueOf(100.00));
        requestDTO.setId(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.postForEntity(
                url,
                new HttpEntity<>(requestDTO, headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void debitAccountSuccess() {
        // given
        String url = baseUrl + "/transaction/debit";
        CreditDebitRequestDTO requestDTO = new CreditDebitRequestDTO();
        requestDTO.setAmount(BigDecimal.valueOf(50.00));
        requestDTO.setId(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.postForEntity(
                url,
                new HttpEntity<>(requestDTO, headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void transferBetweenAccountsSuccess() {
        // given
        String url = baseUrl + "/transaction/transfer";
        TransferRequestDTO request = new TransferRequestDTO();
        request.setDestinationAccountId(UUID.fromString("d47ac10b-58cc-4372-a567-0e02b2c3d481"));
        request.setTransferAmount(BigDecimal.valueOf(10.00));
        request.setSourceAccountId(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

        // when
        ResponseEntity<BankResponseAccountDTO> response = template.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request, headers),
                BankResponseAccountDTO.class
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}