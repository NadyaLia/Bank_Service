package com.example.nadyalia.bankservice.transaction.services;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    BankResponseAccountDTO creditAccount(CreditDebitRequestDTO request, UUID clientId);

    BankResponseAccountDTO debitAccount(CreditDebitRequestDTO request, UUID clientId);

    BankResponseAccountDTO transferBetweenAccounts(TransferRequestDTO request, UUID clientId);

    List<Transaction> findAllTransactionsWhereClientIdIs(UUID clientId);

    List<Transaction> findAllTransactionsWhereAccountCurrencyIs(int currency);
}
