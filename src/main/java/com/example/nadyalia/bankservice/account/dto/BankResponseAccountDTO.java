package com.example.nadyalia.bankservice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankResponseAccountDTO {

    private String responseCode;

    private String responseMessage;

    private AccountInfoDTO accountInfo;
}
