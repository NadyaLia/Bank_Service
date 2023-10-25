package com.example.nadyalia.bankservice.client.services;

import com.example.nadyalia.bankservice.client.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final String senderEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String senderEmail) {
        this.javaMailSender = javaMailSender;
        this.senderEmail = senderEmail;
    }

    @Override
    public void sendEmailAlert(EmailDTO emailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(emailDTO.getRecipient());
            message.setText(emailDTO.getMessage());
            message.setSubject(emailDTO.getSubject());

            javaMailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
