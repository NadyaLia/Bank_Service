package com.example.nadyalia.bankservice.account.dto;

import com.example.nadyalia.bankservice.client.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryRequestDTO {

    private UUID id;

    private String name;
}
