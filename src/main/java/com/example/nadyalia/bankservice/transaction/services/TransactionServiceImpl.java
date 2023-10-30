package com.example.nadyalia.bankservice.transaction.services;

import com.example.nadyalia.bankservice.account.dto.AccountDTO;
import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.repository.AccountRepository;
import com.example.nadyalia.bankservice.account.services.AccountUtils;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;
import com.example.nadyalia.bankservice.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository repository;

    private AccountRepository accountRepository;

    @Transactional
    @Override
    public BankResponseAccountDTO creditAccount(CreditDebitRequestDTO request, UUID clientId) {

        Account accountToCredit = accountRepository.findByIdAndClientId(request.getId(), clientId);
        if (accountToCredit == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }

        accountToCredit.setBalance(accountToCredit.getBalance().add(request.getAmount()));
        accountRepository.save(accountToCredit);

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(1);
        transaction.setCreditAccountId(accountToCredit.getId());
        transaction.setAmount(request.getAmount());
        transaction.setCreateDate(LocalDateTime.now());
        transaction.setDescription("Cash");

        repository.save(transaction);

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountDTO.builder()
                        .name(accountToCredit.getName())
                        .balance(accountToCredit.getBalance())
                        .id(accountToCredit.getId())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public BankResponseAccountDTO debitAccount(CreditDebitRequestDTO request, UUID clientId) {

        Account accountToDebit = accountRepository.findByIdAndClientId(request.getId(), clientId);
        if (accountToDebit == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }

        BigInteger availableBalance = accountToDebit.getBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            accountToDebit.setBalance(accountToDebit.getBalance().subtract(request.getAmount()));
            accountRepository.save(accountToDebit);

            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID());
            transaction.setType(2);
            transaction.setDebitAccountId(accountToDebit.getId());
            transaction.setAmount(request.getAmount());
            transaction.setCreateDate(LocalDateTime.now());
            transaction.setDescription("Cash");

            repository.save(transaction);

            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountDTO.builder()
                            .id(accountToDebit.getId())
                            .name(accountToDebit.getName())
                            .balance(accountToDebit.getBalance())
                            .build())
                    .build();
        }
    }

    @Transactional
    @Override
    public BankResponseAccountDTO transferBetweenAccounts(TransferRequestDTO request, UUID clientId) {
        Account fromAccount = accountRepository.findByIdAndClientId(request.getSourceAccountId(), clientId);
        Account toAccount = accountRepository.findById(request.getDestinationAccountId()).orElse(null);

        if (fromAccount == null || toAccount == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        if (fromAccount.getBalance().compareTo(request.getTransferAmount()) < 0) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getTransferAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getTransferAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(3);
        transaction.setCreditAccountId(toAccount.getId());
        transaction.setDebitAccountId(fromAccount.getId());
        transaction.setAmount(request.getTransferAmount());
        transaction.setCreateDate(LocalDateTime.now());
        transaction.setDescription("Transfer");

        repository.save(transaction);

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESS_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(AccountDTO.builder()
                        .id(fromAccount.getId())
                        .name(fromAccount.getName())
                        .balance(fromAccount.getBalance())
                        .build())
                .build();
    }

    @Override
    public List<Transaction> findAllTransactionsWhereClientIdIs(UUID clientId) {

        return repository.findByClientId(clientId);
    }

    @Override
    public List<Transaction> findAllTransactionsWhereAccountCurrencyIs(int currency) {

        return repository.findByCurrencyCode(currency);
    }
}
