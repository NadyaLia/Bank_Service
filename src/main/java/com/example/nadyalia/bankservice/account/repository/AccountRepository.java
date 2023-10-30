package com.example.nadyalia.bankservice.account.repository;

import com.example.nadyalia.bankservice.account.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT account.* FROM account WHERE client_id = ?1", nativeQuery = true)
    List<Account> getAccountsByClientId(UUID clientId);

    Account findByNameAndClientId(String name, UUID clientId);


    Account findByIdAndClientId(UUID id, UUID clientId);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    void deleteByName(String name);

    List<Account> findByStatus(int status);

    @Query(value = "SELECT account.* FROM account " +
            "LEFT JOIN agreement ON account.id = agreement.account_id " +
            "WHERE agreement.product_id = ?1 and account.status = ?2", nativeQuery = true)
    List<Account> findByProductIdAndStatus(int productId, int status);
}
