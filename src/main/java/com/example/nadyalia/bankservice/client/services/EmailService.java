package com.example.nadyalia.bankservice.client.services;

import com.example.nadyalia.bankservice.client.dto.EmailDTO;

public interface EmailService {
    void sendEmailAlert(EmailDTO emailDTO);
}
