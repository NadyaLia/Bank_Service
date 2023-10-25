package com.example.nadyalia.bankservice.agreement.controllers;

import com.example.nadyalia.bankservice.agreement.services.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;
}
