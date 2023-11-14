package com.example.nadyalia.bankservice.client.services;

import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.entity.ClientWithTransactions;
import com.example.nadyalia.bankservice.client.exceptions.ClientNotFoundException;
import com.example.nadyalia.bankservice.client.repository.ClientRepository;
import com.example.nadyalia.bankservice.converters.ConverterToDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientCreateDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.services.ManagerService;
import com.example.nadyalia.bankservice.role.entity.Role;
import com.example.nadyalia.bankservice.role.repository.RoleRepository;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ManagerService managerService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConverterToDTO converter;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSuccess() {
        // given
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setFirstName("Nadja");
        client1.setLastName("Kuk");

        Client client2 = new Client();
        client2.setId(UUID.randomUUID());
        client2.setFirstName("Katja");
        client2.setLastName("Kik");

        List<Client> mockClients = Arrays.asList(client1, client2);

        when(clientRepository.findAll()).thenReturn(mockClients);

        // when
        List<Client> returnedClients = clientService.getAll();

        // then
        assertEquals(2, returnedClients.size());
        assertEquals("Nadja", returnedClients.get(0).getFirstName());
        assertEquals("Katja", returnedClients.get(1).getFirstName());
    }

    @Test
    public void testGetByIdSuccess() {
        // given
        UUID clientId = UUID.randomUUID();
        Client mockClient = new Client();
        mockClient.setId(clientId);
        mockClient.setFirstName("Nadja");
        mockClient.setLastName("Kuk");

        // when
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        Client returnedClient = clientService.getById(clientId);

        // then
        assertNotNull(returnedClient);
        assertEquals("Nadja", returnedClient.getFirstName());
    }

    @Test
    public void testGetByIdNotFound() {
        // given
        UUID clientId = UUID.randomUUID();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.getById(clientId);
        });

        // then
        assertTrue(exception.getMessage().contains("Client with id = " + clientId + " not found"));
    }

    @Test
    public void testGetAllClientsWhereStatusIsActiveSuccess() {
        // given
        Client activeClient = new Client();
        activeClient.setStatus(1);
        when(clientRepository.findByStatus(1)).thenReturn(Arrays.asList(activeClient));

        // when
        List<Client> activeClients = clientService.getAllClientsWhereStatusIsActive();

        // then
        assertEquals(1, activeClients.size());
        assertEquals(1, activeClients.get(0).getStatus());
    }

    @Test
    public void testGetAllClientsWhereBalanceMoreThanSuccess() {
        // given
        BigDecimal balance = new BigDecimal("1000");
        Client richClient = new Client();
        when(clientRepository.findByBalanceGreaterThan(balance)).thenReturn(Arrays.asList(richClient));

        // when
        List<Client> richClients = clientService.getAllClientsWhereBalanceMoreThan(balance);

        // then
        assertEquals(1, richClients.size());
    }

    @Test
    public void testGetAllClientsWhereTransactionMoreThanSuccess() {
        // given
        int transactionCount = 5;
        ClientWithTransactions clientWithTransactions = new ClientWithTransactions();
        when(clientRepository.findClientsWithTransactionCountGreaterThan(transactionCount))
                .thenReturn(Arrays.asList(clientWithTransactions));

        // when
        List<ClientWithTransactions> clients = clientService.getAllClientsWhereTransactionMoreThan(transactionCount);

        // then
        assertEquals(1, clients.size());
    }

    @Test
    public void testGetClientByUserIdSuccess() {
        // given
        UUID userId1 = UUID.randomUUID();
        Client mockClient1 = new Client();
        mockClient1.setUserId(userId1);

        UUID userId2 = UUID.randomUUID();
        Client mockClient2 = new Client();
        mockClient2.setUserId(userId2);

        when(clientRepository.findByUserId(userId1)).thenReturn(mockClient1);
        when(clientRepository.findByUserId(userId2)).thenReturn(mockClient2);

        // when
        Client returnedClient1 = clientService.getClientByUserId(userId1);
        Client returnedClient2 = clientService.getClientByUserId(userId2);

        // then
        assertNotNull(returnedClient1);
        assertEquals(userId1, returnedClient1.getUserId());

        assertNotNull(returnedClient2);
        assertEquals(userId2, returnedClient2.getUserId());
    }

    @Test
    public void testAddClientSuccess() {
        // given
        ClientCreateDTO createClient = new ClientCreateDTO();
        createClient.setUsername("testUser");
        createClient.setEmail("test@email.com");
        createClient.setPassword("password");
        createClient.setTaxCode("12345");
        createClient.setFirstName("Nadja");
        createClient.setLastName("Kuk");

        int managerId = 1;
        Manager manager = new Manager();
        manager.setId(managerId);

        Client savedClient = new Client();
        savedClient.setFirstName(createClient.getFirstName());
        savedClient.setLastName(createClient.getLastName());
        savedClient.setEmail(createClient.getEmail());

        when(managerService.getById(managerId)).thenReturn(manager);
        when(roleRepository.findByName("ROLE_CLIENT")).thenReturn(Optional.of(new Role()));
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // when
        ClientResponseDTO response = clientService.addClient(createClient, managerId);

        // then
        assertNotNull(response);
        assertEquals(ClientUtils.CLIENT_ADDED_SUCCESS, response.getResponseCode());
        assertEquals(createClient.getFirstName(), response.getClientInfo().getFirstName());
        assertEquals(createClient.getLastName(), response.getClientInfo().getLastName());
    }

    @Test
    public void testDeleteByIdSuccess() {
        // given
        UUID clientId = UUID.randomUUID();

        doNothing().when(clientRepository).deleteById(clientId);

        // when
        clientService.deleteById(clientId);

        // then
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    public void testGetCountSuccess() {
        // given
        when(clientRepository.count()).thenReturn(5L);

        // when
        int count = clientService.getCount();

        // then
        assertEquals(5, count);
    }
}

