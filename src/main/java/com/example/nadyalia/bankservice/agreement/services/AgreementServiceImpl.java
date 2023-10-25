package com.example.nadyalia.bankservice.agreement.services;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.repository.AgreementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private AgreementRepository repository;

    @Override
    public List<Agreement> findAgreementsWhereManagerIDIs(String managerId) {
        int managerIdInt = Integer.parseInt(managerId);
        return repository.findAgreementsByManagerId(managerIdInt);
    }

    @Override
    public List<Agreement> findAgreementsWhereClientIdIs(String clientId) {
        UUID clientUUID = UUID.fromString(clientId);
        return repository.findAgreementsWhereClientIdIs(clientUUID);
    }
}
