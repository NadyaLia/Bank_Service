package com.example.nadyalia.bankservice.agreement.services;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;

import java.util.List;
import java.util.UUID;

public interface AgreementService {

    List<Agreement> findAgreementsWhereManagerIDIs(int managerId);

    List<Agreement> findAgreementsWhereClientIdIs(UUID clientId);
}
