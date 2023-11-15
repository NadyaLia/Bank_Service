package com.example.nadyalia.bankservice.client.services;

import com.example.nadyalia.bankservice.client.entity.ClientWithTransactions;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientCreateDTO;
import com.example.nadyalia.bankservice.client.entity.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> getAll();

    Client getById(UUID id);

    ClientResponseDTO addClient(ClientCreateDTO createClient, Integer id);

    void deleteById(UUID id);

    int getCount();

    List<Client> getAllClientsWhereStatusIsActive();

    List<Client> getAllClientsWhereBalanceMoreThan(BigDecimal balance);

    List<ClientWithTransactions> getAllClientsWhereTransactionMoreThan(int transactionCount);

    Client getClientByUserId(UUID id);
}
