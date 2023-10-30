package com.example.nadyalia.bankservice.manager.repository;

import com.example.nadyalia.bankservice.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    Manager findByUserId(UUID id);
}
