package com.example.nadyalia.bankservice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateAccountDTO {

    private UUID id;

    private String name;

    private int type;

    private int currencyCode;

    private UUID clientId;

    private BigDecimal balance;
}
