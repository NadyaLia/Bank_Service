package com.example.nadyalia.bankservice.manager.controllers;

import com.example.nadyalia.bankservice.account.dto.BankResponseAccountDTO;
import com.example.nadyalia.bankservice.account.dto.CreateOrUpdateAccountDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.account.services.AccountService;
import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import com.example.nadyalia.bankservice.client.dto.ClientInfoDTO;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.manager.dto.ClientResponseDTO;
import com.example.nadyalia.bankservice.manager.dto.ClientCreateDTO;
import com.example.nadyalia.bankservice.client.services.ClientService;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import com.example.nadyalia.bankservice.manager.services.ManagerService;
import com.example.nadyalia.bankservice.product.entity.Product;
import com.example.nadyalia.bankservice.product.services.ProductService;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;
import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/all-clients")
    public List<ClientInfoDTO> getAll() {
        List<Client> clients = clientService.getAll();

        List<ClientInfoDTO> dtos = new ArrayList<>();
        for (Client client : clients) {
            ClientInfoDTO d = new ClientInfoDTO();
            d.setId(client.getId());
            d.setFirstName(client.getFirstName());
            d.setLastName(client.getLastName());
            dtos.add(d);
        }
        return dtos;
    }

    @GetMapping("/client/{clientId}")
    public Client getById(@PathVariable UUID clientId) {
        return clientService.getById(clientId);
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteById(@PathVariable UUID clientId) {
        clientService.deleteById(clientId);
    }

    @GetMapping("/clientCount")
    public int getCount() {
        return clientService.getCount();
    }

    @GetMapping("/clientActive")
    public List<Client> getAllClientsWhereStatusIsActive() {
        return clientService.getAllClientsWhereStatusIsActive();
    }

    @GetMapping("/client/transaction/{transactionCount}")
    public List<Client> getAllClientsWhereTransactionMoreThan(@PathVariable int transactionCount) {
        return clientService.getAllClientsWhereTransactionMoreThan(transactionCount);
    }

    @GetMapping("/sorted")
    public List<Manager> findAllManagersSortedByProductQuantity() {
        return managerService.findAllManagersSortedByProductQuantity();
    }

    @PostMapping("/add-client")
    public ClientResponseDTO addClient(@RequestBody ClientCreateDTO createClient) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username);
        Manager manager = managerService.getByUserId(user.getId());

        return clientService.addClient(createClient, manager.getId());
    }

    @GetMapping("/allAccounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/createAccount")
    public BankResponseAccountDTO createAccount(@RequestBody CreateOrUpdateAccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @DeleteMapping("/account/delete/{accountId}")
    public void deleteAccountById(@PathVariable UUID accountId) {
        accountService.deleteById(accountId);
    }

    @DeleteMapping("/account/delete/accountName/{name}")
    public void deleteByName(@PathVariable String name) {
        accountService.deleteByName(name);
    }

    @PutMapping("/updateAccount/{id}")
    public BankResponseAccountDTO updateAccount(@PathVariable UUID id, @RequestBody CreateOrUpdateAccountDTO accountDTO) {
        return accountService.updateAccount(accountDTO);
    }

    @GetMapping("/accountStatus")
    public List<Account> getAllAccountsWhereStatusIs(@PathVariable String status) {
        return accountService.getAllAccountsWhereStatusIs(status);
    }

    @GetMapping("/account/{productId}/{status}")
    public List<Account> findAccountsWhereProductIdIsAndStatusIs(@PathVariable String productId,
                                                                 @PathVariable String status) {
        return accountService.findAccountsWhereProductIdIsAndStatusIs(productId, status);
    }

    @GetMapping("/transaction/{clientId}")
    public List<Transaction> findAllTransactionsWhereClientIdIs(@PathVariable String clientId) {
        return transactionService.findAllTransactionsWhereClientIdIs(clientId);
    }

    @GetMapping("/transaction/{currency}")
    public List<Transaction> findAllTransactionsWhereAccountCurrencyIs(@PathVariable String currency) {
        return transactionService.findAllTransactionsWhereAccountCurrencyIs(currency);
    }

    @GetMapping("/product/agreements/{quantity}")
    public List<Product> findAllProductsWhereAgreementsQuantityMoreThan(@PathVariable int quantity) {
        return productService.findAllProductsWhereAgreementsQuantityMoreThan(quantity);
    }

    @GetMapping("/product/changed")
    public List<Product> findAllChangedProducts() {
        return productService.findAllChangedProducts();
    }

    @GetMapping("/agreement/manager/{managerId}")
    public List<Agreement> findAgreementsWhereManagerIDIs(@PathVariable String managerId) {
        return agreementService.findAgreementsWhereManagerIDIs(managerId);
    }
}
