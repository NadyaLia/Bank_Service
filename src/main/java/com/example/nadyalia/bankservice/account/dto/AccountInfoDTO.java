package com.example.nadyalia.bankservice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoDTO {

    private UUID id;

    private String name;

    private int type;

    private int status;

    private BigDecimal balance;

    private int currencyCode;
}
