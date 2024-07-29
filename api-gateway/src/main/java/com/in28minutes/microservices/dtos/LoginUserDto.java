package com.in28minutes.microservices.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
    private String[] roles;
}