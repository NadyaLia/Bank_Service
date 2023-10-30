package com.example.nadyalia.bankservice.transaction.repository;

import com.example.nadyalia.bankservice.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT transaction.* FROM transaction " +
            "JOIN account ON transaction.debit_account_id = account.id " +
            "WHERE account.client_id = ?1", nativeQuery = true)
    List<Transaction> findByClientId(UUID clientId);

    @Query(value = "SELECT transaction.* FROM transaction " +
            "JOIN account ON transaction.debit_account_id = account.id " +
            "WHERE account.currency_code = ?1", nativeQuery = true)
    List<Transaction> findByCurrencyCode(int currencyCode);
}
