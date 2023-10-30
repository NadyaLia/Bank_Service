package com.example.nadyalia.bankservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private UUID id;

    private int status;

    private String taxCode;

    private String firstName;

    private String lastName;

    private UUID userId;

    private String email;

    private String address;

    private String phoneNumber;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
