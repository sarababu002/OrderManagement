package com.example.order_managment.controller;

import com.example.order_managment.Role;
import com.example.order_managment.dto.AuthResponse;
import com.example.order_managment.dto.LoginRequest;
import com.example.order_managment.dto.RegisterRequest;
import com.example.order_managment.entity.Customer;
import com.example.order_managment.entity.User;
import com.example.order_managment.exception.CustomerNotFoundException;
import com.example.order_managment.exception.UserAlreadyExistsException;
import com.example.order_managment.jwt.JwtService;
import com.example.order_managment.repository.CustomerRepository;
import com.example.order_managment.repository.UserRepository;
import com.example.order_managment.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(req));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}