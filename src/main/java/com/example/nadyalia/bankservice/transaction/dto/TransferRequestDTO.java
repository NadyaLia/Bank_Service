package com.example.nadyalia.bankservice.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {

    private UUID sourceAccountId;

    private UUID destinationAccountId;

    private BigDecimal transferAmount;
}
