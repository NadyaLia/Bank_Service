package com.example.nadyalia.bankservice.client.repository;

import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.entity.ClientWithTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    List<Client> findByStatus(int status);

    @Query(value = "SELECT client.id, client.first_name, client.last_name FROM client " +
            "INNER JOIN account ON client.id = account.client_id " +
            "WHERE account.balance > ?1", nativeQuery = true)
    List<Client> findByBalanceGreaterThan(BigDecimal balance);

    @Query(value = "SELECT client.id, client.first_name, client.last_name," +
            "COUNT(transaction.id) as transactionCount FROM client " +
            "INNER JOIN account ON client.id = account.client_id " +
            "INNER JOIN transaction ON account.id = transaction.debit_account_id " +
            "OR account.id = transaction.credit_account_id " +
            "GROUP BY client.id HAVING transactionCount > ?1", nativeQuery = true)
    List<ClientWithTransactions> findClientsWithTransactionCountGreaterThan(int transactionCount);

    Client findByUserId(UUID id);
}
