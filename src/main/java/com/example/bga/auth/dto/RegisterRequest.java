package com.example.bga.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    public String email;
    public String password;
}
