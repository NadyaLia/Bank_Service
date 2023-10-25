package com.example.nadyalia.bankservice.user.dto;

import com.example.nadyalia.bankservice.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerResponseDTO {

    private int id;

    private String managerName;

    private String email;

    private Role role;
}
