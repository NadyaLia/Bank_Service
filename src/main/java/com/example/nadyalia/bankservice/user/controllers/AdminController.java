package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.user.dto.ManagerCreateDTO;
import com.example.nadyalia.bankservice.user.dto.ManagerResponseDTO;
import com.example.nadyalia.bankservice.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-manager")
    public ManagerResponseDTO addManager(@RequestBody ManagerCreateDTO managerCreateDTO) {
        return adminService.addManager(managerCreateDTO);
    }
}
