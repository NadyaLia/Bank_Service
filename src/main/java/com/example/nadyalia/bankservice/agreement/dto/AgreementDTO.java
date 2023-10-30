package com.example.nadyalia.bankservice.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementDTO {

    private int id;

    private BigDecimal interestRate;

    private int status;

    private BigDecimal sum;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
