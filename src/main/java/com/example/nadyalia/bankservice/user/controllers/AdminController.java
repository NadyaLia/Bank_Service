package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.user.dto.ManagerCreateDTO;
import com.example.nadyalia.bankservice.user.dto.ManagerResponseDTO;
import com.example.nadyalia.bankservice.user.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Controller for administrative operations")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-manager")
    @Operation(
            summary = "Add Manager",
            description = "Creates a new manager account. This operation can only be performed by an admin."
    )
    public ManagerResponseDTO addManager(@RequestBody ManagerCreateDTO managerCreateDTO) {
        return adminService.addManager(managerCreateDTO);
    }
}
