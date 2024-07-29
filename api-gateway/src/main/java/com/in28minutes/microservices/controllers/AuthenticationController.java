package com.in28minutes.microservices.controllers;


import com.in28minutes.microservices.dtos.LoginResponse;
import com.in28minutes.microservices.dtos.LoginUserDto;
import com.in28minutes.microservices.dtos.RegisterUserDto;
import com.in28minutes.microservices.entities.User;
import com.in28minutes.microservices.services.AuthenticationService;
import com.in28minutes.microservices.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> rolesList = Arrays.asList(roles);
        /*rolesList.stream().forEach(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            authorities.add(simpleGrantedAuthority);
        });
        //authenticatedUser.getAuthorities().addAll(authorities);
        List<GrantedAuthority> authorities1 = authenticatedUser.getAuthorities();
        GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authorities.get(0).getAuthority());
        authorities1.add(simpleGrantedAuthority);
        authenticatedUser.getAuthorities().addAll(authorities1);*/
        String jwtToken = jwtService.generateToken(authenticatedUser,rolesList);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}