package com.example.nadyalia.bankservice.manager.repository;

import com.example.nadyalia.bankservice.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @Query(value = "SELECT manager.id, manager.first_name, manager.last_name," +
            "COUNT(product.id) as productQuantity FROM manager" +
            "LEFT JOIN product ON product.manager_id = manager.id" +
            "GROUP BY manager.id" +
            "ORDER BY productQuantity DESC", nativeQuery = true)
    List<Manager> findAllManagersSortedByProductQuantity();

    Manager findByUserId(UUID id);
}
