package com.example.nadyalia.bankservice.client.controllers;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.dto.EnquiryRequestDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.services.AccountService;
import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import com.example.nadyalia.bankservice.client.services.ClientService;
import com.example.nadyalia.bankservice.transaction.dto.CreditDebitRequestDTO;
import com.example.nadyalia.bankservice.transaction.dto.TransferRequestDTO;
import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AgreementService agreementService;

    @GetMapping("/account/{clientId}")
    public List<Account> getAccountsByClientId(@PathVariable UUID id) {
        return accountService.getAccountsByClientId(id);
    }

    @GetMapping("/account/checkBalance")
    public BankResponseAccountDTO balance(@PathVariable EnquiryRequestDTO requestDTO) {
        return accountService.checkBalance(requestDTO);
    }

    @PostMapping("/transaction/credit")
    public BankResponseAccountDTO creditAccount(@RequestBody CreditDebitRequestDTO requestDTO) {
        return transactionService.creditAccount(requestDTO);
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
