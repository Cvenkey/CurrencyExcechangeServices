package com.practice.auth.api.controllers;





import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {


    @GetMapping("/")
    public ResponseEntity<String> allUsers() {

        return ResponseEntity.ok("hello-world");
    }
}