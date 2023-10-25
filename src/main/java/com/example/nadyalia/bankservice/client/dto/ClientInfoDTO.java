package com.example.nadyalia.bankservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientInfoDTO {

    private UUID id;

    private String firstName;

    private String lastName;
}
