package com.example.nadyalia.bankservice.converters;

import com.example.nadyalia.bankservice.account.dto.AccountDTO;
import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.agreement.dto.AgreementDTO;
import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.client.dto.ClientDTO;
import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.product.dto.ProductDTO;
import com.example.nadyalia.bankservice.product.entity.Product;
import com.example.nadyalia.bankservice.transaction.dto.TransactionDTO;
import com.example.nadyalia.bankservice.transaction.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConverterToDTO {
    private final ModelMapper modelMapper = new ModelMapper();

    public ClientDTO fromClientToClientDto(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    public AccountDTO fromAccountToAccountDto(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }

    public ProductDTO fromProductToProductDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public AgreementDTO fromAgreementToAgreementDto(Agreement agreement) {
        return modelMapper.map(agreement, AgreementDTO.class);
    }

    public TransactionDTO fromTransactionToTransactionDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public List<ClientDTO> fromClientListToClientDto(List<Client> clients) {
        List<ClientDTO> dtos = new ArrayList<>();
        for (Client client : clients) {
            ClientDTO dto = fromClientToClientDto(client);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AccountDTO> fromAccountListToAccountDto(List<Account> accounts) {
        List<AccountDTO> dtos = new ArrayList<>();
        for (Account account : accounts) {
            AccountDTO dto = fromAccountToAccountDto(account);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductDTO> fromProductListToProductDto(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            ProductDTO dto = fromProductToProductDto(product);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AgreementDTO> fromAgreementListToAgreementDto(List<Agreement> agreements) {
        List<AgreementDTO> dtos = new ArrayList<>();
        for (Agreement agreement : agreements) {
            AgreementDTO dto = fromAgreementToAgreementDto(agreement);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<TransactionDTO> fromTransactionListToTransactionDto(List<Transaction> transactions) {
        List<TransactionDTO> dtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDTO dto = fromTransactionToTransactionDto(transaction);
            dtos.add(dto);
        }
        return dtos;
    }
}
