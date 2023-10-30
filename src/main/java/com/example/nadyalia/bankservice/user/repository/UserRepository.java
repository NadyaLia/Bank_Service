package com.example.nadyalia.bankservice.user.repository;

import com.example.nadyalia.bankservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
