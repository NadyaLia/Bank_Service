package com.example.nadyalia.bankservice.transaction.dto;

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
public class CreditDebitRequestDTO {

    private UUID id;

    private BigDecimal amount;
}
