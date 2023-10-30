package com.example.nadyalia.bankservice.manager.controllers;

import com.example.nadyalia.bankservice.account.dto.AccountDTO;
import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.dto.CreateOrUpdateAccountDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.services.AccountService;
import com.example.nadyalia.bankservice.agreement.dto.AgreementDTO;
import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import com.example.nadyalia.bankservice.client.dto.ClientDTO;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.client.entity.ClientWithTransactions;
import com.example.nadyalia.bankservice.converters.ConverterToDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientCreateDTO;
import com.example.nadyalia.bankservice.client.services.ClientService;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.services.ManagerService;
import com.example.nadyalia.bankservice.product.dto.ProductDTO;
import com.example.nadyalia.bankservice.product.entity.Product;
import com.example.nadyalia.bankservice.product.services.ProductService;
import com.example.nadyalia.bankservice.transaction.dto.TransactionDTO;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;
import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private ConverterToDTO converter;

    @GetMapping("/all-clients")
    public List<ClientDTO> getAll() {
        List<Client> clients = clientService.getAll();
        return converter.fromClientListToClientDto(clients);
    }

    @GetMapping("/client/{clientId}")
    public ClientDTO getById(@PathVariable UUID clientId) {
        Client client =  clientService.getById(clientId);
        return converter.fromClientToClientDto(client);
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteById(@PathVariable UUID clientId) {
        clientService.deleteById(clientId);
    }

    @GetMapping("/client-count")
    public int getCount() {
        return clientService.getCount();
    }

    @GetMapping("/client-active")
    public List<ClientDTO> getAllClientsWhereStatusIsActive() {
        List<Client> clients = clientService.getAllClientsWhereStatusIsActive();
        return converter.fromClientListToClientDto(clients);
    }

    @GetMapping("/client/transaction/{transactionCount}")
    public List<ClientWithTransactions> getAllClientsWhereTransactionMoreThan(@PathVariable int transactionCount) {
        return clientService.getAllClientsWhereTransactionMoreThan(transactionCount);
    }

    @PostMapping("/add-client")
    public ClientResponseDTO addClient(@RequestBody ClientCreateDTO createClient) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameManager = auth.getName();

        User userManager = userRepository.findByUsername(usernameManager);
        Manager manager = managerService.getByUserId(userManager.getId());

        return clientService.addClient(createClient, manager.getId());
    }

    @GetMapping("/all-accounts")
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return converter.fromAccountListToAccountDto(accounts);
    }

    @PostMapping("/create-account")
    public BankResponseAccountDTO createAccount(@RequestBody CreateOrUpdateAccountDTO accountDTO) {
        Client client = clientService.getById(accountDTO.getClientId());
        return accountService.createAccount(accountDTO, client);
    }

    @DeleteMapping("/account/delete/{accountId}")
    public void deleteAccountById(@PathVariable UUID accountId) {
        accountService.deleteById(accountId);
    }

    @DeleteMapping("/account/delete/account-name/{name}")
    public void deleteByName(@PathVariable String name) {
        accountService.deleteByName(name);
    }

    @PutMapping("/update-account")
    public BankResponseAccountDTO updateAccount(@RequestBody CreateOrUpdateAccountDTO accountDTO) {
        return accountService.updateAccount(accountDTO);
    }

    @GetMapping("/account-status/{status}")
    public List<AccountDTO> getAllAccountsWhereStatusIs(@PathVariable int status) {
        List<Account> accounts = accountService.getAllAccountsWhereStatusIs(status);
        return converter.fromAccountListToAccountDto(accounts);
    }

    @GetMapping("/account/{productId}/{status}")
    public List<AccountDTO> findAccountsWhereProductIdIsAndStatusIs(@PathVariable int productId,
                                                                    @PathVariable int status) {
        List<Account> accounts = accountService.findAccountsWhereProductIdIsAndStatusIs(productId, status);
        return converter.fromAccountListToAccountDto(accounts);
    }

    @GetMapping("/transaction/{clientId}")
    public List<Transaction> findAllTransactionsWhereClientIdIs(@PathVariable UUID clientId) {
        return transactionService.findAllTransactionsWhereClientIdIs(clientId);
    }

    @GetMapping("/transaction/currency/{currency}")
    public List<TransactionDTO> findAllTransactionsWhereAccountCurrencyIs(@PathVariable int currency) {
        List<Transaction> transactions = transactionService.findAllTransactionsWhereAccountCurrencyIs(currency);
        return converter.fromTransactionListToTransactionDto(transactions);
    }

    @GetMapping("/product/agreements/{quantity}")
    public List<ProductDTO> findAllProductsWhereAgreementsQuantityMoreThan(@PathVariable int quantity) {
        List<Product> products = productService.findAllProductsWhereAgreementsQuantityMoreThan(quantity);
        return converter.fromProductListToProductDto(products);
    }

    @GetMapping("/product/changed")
    public List<ProductDTO> findAllChangedProducts() {
        List<Product> products = productService.findAllChangedProducts();
        return converter.fromProductListToProductDto(products);
    }

    @GetMapping("/agreement/manager/{managerId}")
    public List<AgreementDTO> findAgreementsWhereManagerIDIs(@PathVariable int managerId) {
        List<Agreement> agreements = agreementService.findAgreementsWhereManagerIDIs(managerId);
        return converter.fromAgreementListToAgreementDto(agreements);
    }

    @GetMapping("/agreement/client/{clientId}")
    public List<AgreementDTO> findAgreementsWhereClientIdIs(@PathVariable UUID clientId) {
        List<Agreement> agreements = agreementService.findAgreementsWhereClientIdIs(clientId);
        return converter.fromAgreementListToAgreementDto(agreements);
    }
}
