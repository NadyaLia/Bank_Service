package com.example.nadyalia.bankservice.client.controllers;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.dto.EnquiryRequestDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.services.AccountService;
import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.services.ClientService;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
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

    public Client getClientByAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameClient = auth.getName();

        User userClient = userService.getByUsername(usernameClient);
        Client client = clientService.getClientByUserId(userClient.getId());
        return client;
    }

    @GetMapping("/account")
    public List<Account> getAccountsByClientId() {
        Client client = getClientByAuth();
        return accountService.getAccountsByClientId(client.getId());

    }

    @GetMapping("/account/check-balance")
    public BankResponseAccountDTO balance(@PathVariable EnquiryRequestDTO requestDTO) {
        Client client = getClientByAuth();

        // get
        return accountService.checkBalance(requestDTO.getId(), client.getId());
    }

    @PostMapping("/transaction/credit")
    public BankResponseAccountDTO creditAccount(@RequestBody CreditDebitRequestDTO requestDTO) {
        Client client = getClientByAuth();
        return transactionService.creditAccount(requestDTO, client.getId());
    }

    @PostMapping("/transaction/debit")
    public BankResponseAccountDTO debitAccount(@RequestBody CreditDebitRequestDTO requestDTO) {
        return transactionService.debitAccount(requestDTO);
    }

    @PutMapping("/transaction/transfer/{sourceAccountId}/{destinationAccountId}")
    public BankResponseAccountDTO transferBetweenAccounts(@PathVariable UUID  sourceAccountId,
                                                          @PathVariable UUID destinationAccountId,
                                                          @RequestBody TransferRequestDTO request) {
        return transactionService.transferBetweenAccounts(sourceAccountId, destinationAccountId, request);
    }

    @GetMapping("/agreement/client/{clientId}")
    public List<Agreement> findAgreementsWhereClientIdIs(@PathVariable String clientId) {
        return agreementService.findAgreementsWhereClientIdIs(clientId);
    }
}
