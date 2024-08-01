package com.in28minutes.microservices.controllers;


import com.in28minutes.microservices.dtos.LoginResponse;
import com.in28minutes.microservices.dtos.LoginUserDto;
import com.in28minutes.microservices.dtos.RegisterUserDto;
import com.in28minutes.microservices.entities.User;
import com.in28minutes.microservices.services.AuthenticationService;
import com.in28minutes.microservices.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String[] roles = loginUserDto.getRoles();
        LoginResponse loginResponse;
        List<String> rolesList = Arrays.asList(roles);
        if (!Objects.isNull(authenticatedUser)) {
            String jwtToken = jwtService.generateToken(authenticatedUser, rolesList);
            loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        } else {
            loginResponse = new LoginResponse().setExpiresIn(0).setToken(null);
        }
        return ResponseEntity.ok(loginResponse);
    }
}