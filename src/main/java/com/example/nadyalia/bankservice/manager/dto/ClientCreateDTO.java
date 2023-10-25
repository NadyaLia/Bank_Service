package com.example.nadyalia.bankservice.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCreateDTO {

    private UUID id;

    private int managerId;

    private int status;

    private String taxCode;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String username;

    private String password;
}
