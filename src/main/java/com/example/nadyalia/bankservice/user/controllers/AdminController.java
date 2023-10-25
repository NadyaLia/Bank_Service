package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.repository.ManagerRepository;
import com.example.nadyalia.bankservice.manager.services.ManagerService;
import com.example.nadyalia.bankservice.product.entity.Product;
import com.example.nadyalia.bankservice.product.repository.ProductRepository;
import com.example.nadyalia.bankservice.role.entity.Role;
import com.example.nadyalia.bankservice.role.repository.RoleRepository;
import com.example.nadyalia.bankservice.user.dto.ManagerCreateDTO;
import com.example.nadyalia.bankservice.user.dto.ManagerResponseDTO;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/add-manager")
    public ManagerResponseDTO addManager(@RequestBody ManagerCreateDTO managerCreateDTO) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(managerCreateDTO.getUsername());
        user.setEmail(managerCreateDTO.getEmail());
        user.setPassword(encoder.encode(managerCreateDTO.getPassword()));
        user.setType("manager");

        Role roles = roleRepository.findByName("ROLE_MANAGER").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        Manager manager = new Manager();
        manager.setFirstName(managerCreateDTO.getFirstName());
        manager.setLastName(managerCreateDTO.getLastName());
        manager.setStatus(1);
        manager.setCreateDate(LocalDateTime.now());
        manager.setUpdateDate(LocalDateTime.now());
        manager.setUserId(user.getId());

        managerRepository.save(manager);

        Product product = new Product();
        product.setManager(manager);
        product.setStatus(1);
        product.setCreateDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());
        product.setCurrencyCode(840);
        product.setLimit(10);
        product.setName("Credit Card");
        product.setInterestRate(new BigDecimal(6));
        productRepository.save(product);

        return null;
    }
}
