package com.example.nadyalia.bankservice.agreement.services;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.exceptions.AgreementNotFoundException;
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
    public List<Agreement> findAgreementsWhereManagerIDIs(int managerId) {
        List<Agreement> agreements = repository.findAgreementsByManagerId(managerId);
        if (agreements.isEmpty()) {
            throw new AgreementNotFoundException("No agreements found for manager with ID: " + managerId);
        }
        return agreements;
    }

    @Override
    public List<Agreement> findAgreementsWhereClientIdIs(UUID clientId) {
        List<Agreement> agreements = repository.findAgreementsWhereClientIdIs(clientId);
        if (agreements.isEmpty()) {
            throw new AgreementNotFoundException("No agreements found for client with ID: " + clientId);
        }
        return agreements;
    }
}
