package com.example.nadyalia.bankservice.manager.services;

import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private ManagerRepository repository;

    @Override
    public Manager getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Manager getByUserId(UUID id) {
        return repository.findByUserId(id);
    }
}
