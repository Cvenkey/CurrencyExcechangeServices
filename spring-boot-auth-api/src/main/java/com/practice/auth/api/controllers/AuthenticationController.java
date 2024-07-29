//package com.practice.auth.api.controllers;
//
//
//import com.in28minutes.microservices.apigateway.authApi.dtos.LoginResponse;
//import com.in28minutes.microservices.apigateway.authApi.dtos.LoginUserDto;
//import com.in28minutes.microservices.apigateway.authApi.dtos.RegisterUserDto;
//import com.in28minutes.microservices.apigateway.authApi.entities.User;
//import com.in28minutes.microservices.apigateway.authApi.services.AuthenticationService;
//import com.in28minutes.microservices.apigateway.authApi.services.JwtService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping("/auth")
//@RestController
//public class AuthenticationController {
//    private final JwtService jwtService;
//
//    private final AuthenticationService authenticationService;
//
//    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
//        this.jwtService = jwtService;
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
//        User registeredUser = authenticationService.signup(registerUserDto);
//
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(jwtToken);
//        loginResponse.setExpiresIn(jwtService.getExpirationTime());
//        return ResponseEntity.ok(loginResponse);
//    }
//}