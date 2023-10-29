package com.example.nadyalia.bankservice.transaction.services;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    BankResponseAccountDTO creditAccount(CreditDebitRequestDTO request, UUID clientId);

    BankResponseAccountDTO debitAccount(CreditDebitRequestDTO request);

    BankResponseAccountDTO transferBetweenAccounts(UUID sourceAccount, UUID destinationAccount, TransferRequestDTO request);

    List<Transaction> findAllTransactionsWhereClientIdIs(String clientId);

    List<Transaction> findAllTransactionsWhereAccountCurrencyIs(String currency);
}
