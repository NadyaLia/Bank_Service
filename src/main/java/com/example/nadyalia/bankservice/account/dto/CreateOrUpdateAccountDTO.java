package com.example.nadyalia.bankservice.account.dto;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.client.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateAccountDTO {

    private UUID id;

    private String name;

    private int type;

    private int currencyCode;

    private List<Agreement> agreements;

    private Client client;
}
