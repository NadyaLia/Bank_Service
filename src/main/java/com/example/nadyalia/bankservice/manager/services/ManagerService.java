package com.example.nadyalia.bankservice.manager.services;

import com.example.nadyalia.bankservice.manager.entity.Manager;

import java.util.UUID;

public interface ManagerService {

    Manager getById(int id);

    Manager getByUserId(UUID id);
}
