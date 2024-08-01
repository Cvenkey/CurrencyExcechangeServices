package com.in28minutes.microservices.controllers;


import com.in28minutes.microservices.dtos.RegisterUserDto;
import com.in28minutes.microservices.entities.User;
import com.in28minutes.microservices.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<List<User>>> allUsers() {
        List <User> users = userService.allUsers();
        return Mono.just(ResponseEntity.ok(users));
    }

    @PostMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestBody  RegisterUserDto dto) {
        User user = userService.allUsers().stream().filter(u->u.getEmail().equals(dto.getEmail())).findFirst().get();
        return ResponseEntity.ok(user);
    }
}
