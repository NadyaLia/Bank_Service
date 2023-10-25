package com.example.nadyalia.bankservice.transaction.services;

import com.example.nadyalia.bankservice.account.dto.AccountInfoDTO;
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
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository repository;

    private AccountRepository accountRepository;

    @Transactional
    @Override
    public BankResponseAccountDTO creditAccount(CreditDebitRequestDTO request) {

        Account found = accountRepository.findByAccountId(request.getId());
        if (found == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        Account accountToCredit = accountRepository.findByAccountId(request.getId());
        accountToCredit.setBalance(accountToCredit.getBalance().add(request.getAmount()));
        accountRepository.save(accountToCredit);

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .name(accountToCredit.getName())
                        .balance(accountToCredit.getBalance())
                        .id(accountToCredit.getId())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public BankResponseAccountDTO debitAccount(CreditDebitRequestDTO request) {

        Account found = accountRepository.findByAccountId(request.getId());
        if (found == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        Account accountToDebit = accountRepository.findByAccountId(request.getId());
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
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfoDTO.builder()
                            .id(accountToDebit.getId())
                            .name(accountToDebit.getName())
                            .balance(accountToDebit.getBalance())
                            .build())
                    .build();
        }
    }

    @Transactional
    @Override
    public BankResponseAccountDTO transferBetweenAccounts(UUID sourceAccount, UUID destinationAccount,
                                                          TransferRequestDTO request) {
        Account fromAccount = accountRepository.findByAccountId(request.getSourceAccountId());
        Account toAccount = accountRepository.findByAccountId(request.getDestinationAccountId());

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

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESS_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .id(fromAccount.getId())
                        .name(fromAccount.getName())
                        .balance(fromAccount.getBalance())
                        .build())
                .build();
    }

    @Override
    public List<Transaction> findAllTransactionsWhereClientIdIs(String clientId) {
        UUID clientUUID = UUID.fromString(clientId);
        return repository.findByClientId(clientUUID);
    }

    @Override
    public List<Transaction> findAllTransactionsWhereAccountCurrencyIs(String currency) {
        int currencyCodeInt = Integer.parseInt(currency);
        return repository.findByCurrencyCode(currencyCodeInt);
    }
}
