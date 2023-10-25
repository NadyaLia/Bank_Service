package com.example.nadyalia.bankservice.transaction.controllers;

import com.example.nadyalia.bankservice.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

}
