package com.example.nadyalia.bankservice.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private UUID id;

    private UUID debitAccountId;

    private UUID creditAccountId;

    private int type;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createDate;
}
