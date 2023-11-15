package com.example.nadyalia.bankservice.client.controllers;

import com.example.nadyalia.bankservice.account.dto.AccountDTO;
import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.services.AccountService;
import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.services.ClientService;
import com.example.nadyalia.bankservice.converters.ConverterToDTO;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@Tag(name = "Client Controller", description = "Controller for client operations on accounts and transactions.")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private ConverterToDTO converter;

    public Client getClientByAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameClient = auth.getName();

        User userClient = userService.getByUsername(usernameClient);
        Client client = clientService.getClientByUserId(userClient.getId());
        return client;
    }

    @GetMapping("/account")
    @Operation(
            summary = "Get client accounts",
            description = "Retrieves all accounts associated with the authenticated client."
    )
    public List<AccountDTO> getAccountsByClientId() {
        Client client = getClientByAuth();
        List<Account> accounts = accountService.getAccountsByClientId(client.getId());
        return converter.fromAccountListToAccountDto(accounts);
    }

    @GetMapping("/account/check-balance/{accountId}")
    @Operation(
            summary = "Check account balance",
            description = "Checks the balance of a specified account for the authenticated client."
    )
    public BankResponseAccountDTO balance(@PathVariable UUID accountId) {
        Client client = getClientByAuth();
        return accountService.checkBalance(accountId, client.getId());
    }

    @PostMapping("/transaction/credit")
    @Operation(
            summary = "Credit account",
            description = "Credits a specified amount to the authenticated client's account."
    )
    public BankResponseAccountDTO creditAccount(@RequestBody CreditDebitRequestDTO requestDTO) {
        Client client = getClientByAuth();
        return transactionService.creditAccount(requestDTO, client.getId());
    }

    @PostMapping("/transaction/debit")
    @Operation(
            summary = "Debit account",
            description = "Debits a specified amount from the authenticated client's account."
    )
    public BankResponseAccountDTO debitAccount(@RequestBody CreditDebitRequestDTO requestDTO) {
        Client client = getClientByAuth();
        return transactionService.debitAccount(requestDTO, client.getId());
    }

    @PutMapping("/transaction/transfer")
    @Operation(
            summary = "Transfer between accounts",
            description = "Transfers an amount between two accounts of the authenticated client."
    )
    public BankResponseAccountDTO transferBetweenAccounts(@RequestBody TransferRequestDTO request) {
        Client client = getClientByAuth();
        return transactionService.transferBetweenAccounts(request, client.getId());
    }
}
