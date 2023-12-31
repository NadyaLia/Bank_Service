package com.example.nadyalia.bankservice.account.services;

import com.example.nadyalia.bankservice.account.dto.*;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.repository.AccountRepository;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Builder
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    private EmailService emailService;

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public List<Account> getAccountsByClientId(UUID id) {
        return repository.getAccountsByClientId(id);
    }

    @Transactional
    @Override
    public BankResponseAccountDTO createAccount(CreateOrUpdateAccountDTO accountDTO, Client client) {

        Account found = repository.findByNameAndClientId(accountDTO.getName(), client.getId());
        if (found != null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Account newAccount = Account.builder()
                .id(UUID.randomUUID())
                .name(accountDTO.getName())
                .type(accountDTO.getType())
                .status(1)
                .currencyCode(accountDTO.getCurrencyCode())
                .balance(BigDecimal.ZERO)
                .client(client)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Account savedAccount = repository.save(newAccount);


  /*      EmailDTO emailDTO = EmailDTO.builder()
                .recipient(savedAccount.getClient().getEmail())
                .subject("ACCOUNT CREATION")
                .message("Congratulations! Your account has been successfully created.\nYour account details:\n" +
                        "Account name: " + savedAccount.getName() + "\nAccount number: " + savedAccount.getId())
                .build();


        emailService.sendEmailAlert(emailDTO);*/

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountDTO.builder()
                        .id(savedAccount.getId())
                        .name(savedAccount.getName())
                        .balance(savedAccount.getBalance())
                        .currencyCode(savedAccount.getCurrencyCode())
                        .status(savedAccount.getStatus())
                        .type(savedAccount.getType())
                        .build())
                .build();
    }

    @Override
    public BankResponseAccountDTO checkBalance(UUID accountId, UUID clientId) {

        Account found = repository.findByIdAndClientId(accountId, clientId);
        if (found == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountDTO.builder()
                        .balance(found.getBalance())
                        .id(found.getId())
                        .name(found.getName())
                        .currencyCode(found.getCurrencyCode())
                        .build())
                .build();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Transactional
    @Override
    public BankResponseAccountDTO updateAccount(CreateOrUpdateAccountDTO accountDTO) {
        Account accountForUpdate = repository.findById(accountDTO.getId()).orElse(null);
        if (accountForUpdate == null) {
            return BankResponseAccountDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        accountForUpdate.setId(accountDTO.getId());
        accountForUpdate.setName(accountDTO.getName());
        accountForUpdate.setType(accountDTO.getType());
        accountForUpdate.setCurrencyCode(accountDTO.getCurrencyCode());
        accountForUpdate.setBalance(accountDTO.getBalance());


        repository.save(accountForUpdate);

        return BankResponseAccountDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_UPDATED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_UPDATED_MESSAGE)
                .accountInfo(AccountDTO.builder()
                        .balance(accountForUpdate.getBalance())
                        .id(accountForUpdate.getId())
                        .name(accountForUpdate.getName())
                        .currencyCode(accountForUpdate.getCurrencyCode())
                        .build())
                .build();
    }

    @Override
    public List<Account> getAllAccountsWhereStatusIs(int status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Account> findAccountsWhereProductIdIsAndStatusIs(int productId, int status) {

        return repository.findByProductIdAndStatus(productId, status);
    }
}
