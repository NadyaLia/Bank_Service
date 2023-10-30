package com.example.nadyalia.bankservice.client.services;

import com.example.nadyalia.bankservice.client.entity.ClientWithTransactions;
import com.example.nadyalia.bankservice.converters.ConverterToDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientCreateDTO;
import com.example.nadyalia.bankservice.client.dto.ClientInfoDTO;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.repository.ClientRepository;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.services.ManagerService;
import com.example.nadyalia.bankservice.role.entity.Role;
import com.example.nadyalia.bankservice.role.repository.RoleRepository;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Builder
public class ClientServiceImpl implements ClientService {


    private ClientRepository repository;

    private ManagerService managerService;

    private BCryptPasswordEncoder encoder;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private ConverterToDTO converter;

    @Override
    public List<Client> getAll() {
       List<Client> clients = repository.findAll();

        return new ArrayList<>(clients);
    }

    @Override
    public Client getById(UUID id) {
        Client client = repository.findById(id).orElse(null);
        return client;
    }

    @Transactional
    @Override
    public ClientResponseDTO addClient(ClientCreateDTO createClient, Integer managerId) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(createClient.getUsername());
        user.setEmail(createClient.getEmail());
        user.setPassword(encoder.encode(createClient.getPassword()));
        user.setType("client");

        Role roles = roleRepository.findByName("ROLE_CLIENT").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        Manager manager = managerService.getById(managerId);
        Client newClient = Client.builder()
                .id(UUID.randomUUID())
                .status(1)
                .taxCode(createClient.getTaxCode())
                .firstName(createClient.getFirstName())
                .lastName(createClient.getLastName())
                .email(createClient.getEmail())
                .address(createClient.getAddress())
                .phoneNumber(createClient.getPhoneNumber())
                .manager(manager)
                .userId(user.getId())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Client savedClient = repository.save(newClient);

        return ClientResponseDTO.builder()
                .responseCode(ClientUtils.CLIENT_ADDED_SUCCESS)
                .responseMessage(ClientUtils.CLIENT_ADDED_MESSAGE)
                .clientInfo(ClientInfoDTO.builder()
                        .id(savedClient.getId())
                        .firstName(savedClient.getFirstName())
                        .lastName(savedClient.getLastName())
                        .build())
                .build();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public int getCount() {
        return (int) repository.count();
    }

    @Override
    public List<Client> getAllClientsWhereStatusIsActive() {
        return repository.findByStatus(1);
    }

    @Override
    public List<Client> getAllClientsWhereBalanceMoreThan(BigDecimal balance) {
        return repository.findByBalanceGreaterThan(balance);
    }

    @Override
    public List<ClientWithTransactions> getAllClientsWhereTransactionMoreThan(int transactionCount) {
        return repository.findClientsWithTransactionCountGreaterThan(transactionCount);
    }

    @Override
    public Client getClientByUserId(UUID id) {
        return repository.findByUserId(id);
    }
}
