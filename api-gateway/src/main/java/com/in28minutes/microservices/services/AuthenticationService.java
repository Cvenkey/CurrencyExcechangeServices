package com.in28minutes.microservices.services;

import com.in28minutes.microservices.config.CustomAuthenticationProvider;
import com.in28minutes.microservices.dtos.LoginUserDto;
import com.in28minutes.microservices.dtos.RegisterUserDto;
import com.in28minutes.microservices.entities.User;
import com.in28minutes.microservices.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationProvider authenticationManager;

    public AuthenticationService(UserRepository userRepository, CustomAuthenticationProvider authenticationManager,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        var user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));
        if (user.getEmail().equals("ces.admin@ces.com"))
            user.setRole("ROLE_ADMIN");
        else
            user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        String password = input.getPassword();
        Optional<User> byEmail = userRepository.findByEmail(input.getEmail());
        User dbUser = byEmail.get();
        boolean matches = passwordEncoder.matches(password, dbUser.getPassword());
        System.out.println("Password match flag {} " + matches);
        return matches ? dbUser : null;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
