package com.example.nadyalia.bankservice.account.services;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.dto.CreateOrUpdateAccountDTO;
import com.example.nadyalia.bankservice.account.dto.EnquiryRequestDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.client.entity.Client;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> getAllAccounts();

    List<Account> getAccountsByClientId(UUID id);

    BankResponseAccountDTO createAccount(CreateOrUpdateAccountDTO accountDTO, Client client);

    BankResponseAccountDTO checkBalance(UUID accountId, UUID clientId);

    void deleteById(UUID id);

    void deleteByName(String name);

    BankResponseAccountDTO updateAccount(CreateOrUpdateAccountDTO accountDTO);

    List<Account> getAllAccountsWhereStatusIs(String status);

    List<Account> findAccountsWhereProductIdIsAndStatusIs(String productId, String status);
}
