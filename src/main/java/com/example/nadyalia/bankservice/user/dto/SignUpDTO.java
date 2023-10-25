package com.example.nadyalia.bankservice.user.dto;

import lombok.Data;

@Data
public class SignUpDTO {

    private String username;

    private String email;

    private String password;

    private String type;
}
