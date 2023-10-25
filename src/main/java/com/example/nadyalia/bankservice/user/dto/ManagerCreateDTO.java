package com.example.nadyalia.bankservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerCreateDTO {

    private int id;

    private String firstName;

    private String lastName;

    private String username;

    private UUID user_id;

    private String email;

    private String password;

    private int status;
}
