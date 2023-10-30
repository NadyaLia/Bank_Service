package com.example.nadyalia.bankservice.client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.UUID;

public class ClientWithTransactions {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "transaction_count")
    private int transactionCount;
}
