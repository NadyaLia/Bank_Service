package com.example.nadyalia.bankservice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int id;

    private String name;

    private int status;

    private int currencyCode;

    private BigDecimal interestRate;

    private int limit;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
