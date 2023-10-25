package com.example.nadyalia.bankservice.account.controllers;

import com.example.nadyalia.bankservice.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

}
