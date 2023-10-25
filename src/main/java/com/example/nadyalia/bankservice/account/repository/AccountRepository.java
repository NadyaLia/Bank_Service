package com.example.nadyalia.bankservice.account.repository;

import com.example.nadyalia.bankservice.account.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> getAccountsByClientId(UUID clientId);

    @Query(value = "SELECT name FROM account WHERE name = ?1 AND client_id = ?2", nativeQuery = true)
    Account findByNameAndClientId(String name, UUID clientId);

    @Query(value = "SELECT id, name FROM account WHERE id = ?1", nativeQuery = true)
    Account findByAccountId(UUID id);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    void deleteByName(String name);

    List<Account> findByStatus(int status);

    @Query(value = "SELECT account.id, account.name FROM account" +
            "JOIN agreement ON account.id = agreement.account_id" +
            "WHERE agreement.product_id = ?1 and account.status = ?2", nativeQuery = true)
    List<Account> findByProductIdAndStatus(int productId, int status);
}
