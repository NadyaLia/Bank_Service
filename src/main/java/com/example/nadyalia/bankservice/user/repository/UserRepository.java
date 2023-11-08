package com.example.nadyalia.bankservice.user.repository;

import com.example.nadyalia.bankservice.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByType(String type);

    @Modifying
    @Transactional
    @Query(value = "update bank.user set username = 'old' where username = ?1", nativeQuery = true)
    void makeUsernameOld(String username);

    @Modifying
    @Transactional
    @Query(value = "update bank.user set type = 'admin-old' where type = ?1", nativeQuery = true)
    void renameAdmin(String type);
}
