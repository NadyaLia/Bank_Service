package com.example.nadyalia.bankservice.agreement.services;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;

import java.util.List;

public interface AgreementService {

    List<Agreement> findAgreementsWhereManagerIDIs(String managerId);

    List<Agreement> findAgreementsWhereClientIdIs(String clientId);
}
