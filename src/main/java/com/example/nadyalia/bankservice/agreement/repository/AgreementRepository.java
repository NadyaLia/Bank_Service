package com.example.nadyalia.bankservice.agreement.repository;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    @Query(value = "SELECT agreement.* FROM agreement " +
            "JOIN account ON agreement.account_id = account.id " +
            "JOIN client ON account.client_id = client.id " +
            "WHERE client.manager_id = ?1", nativeQuery = true)
    List<Agreement> findAgreementsByManagerId(int managerId);

    @Query(value = "SELECT agreement.* FROM agreement " +
            "JOIN account ON agreement.account_id = account.id " +
            "WHERE account.client_id = ?1", nativeQuery = true)
    List<Agreement> findAgreementsWhereClientIdIs(UUID clientId);
}
