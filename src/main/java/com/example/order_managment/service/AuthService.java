package com.example.order_managment.service;

import com.example.order_managment.dto.AuthResponse;
import com.example.order_managment.dto.LoginRequest;
import com.example.order_managment.dto.RegisterRequest;
import com.example.order_managment.entity.Customer;
import com.example.order_managment.entity.User;
import com.example.order_managment.exception.UserAlreadyExistsException;
import com.example.order_managment.jwt.JwtService;
import com.example.order_managment.repository.CustomerRepository;
import com.example.order_managment.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService){
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService= jwtService;
    }

    @Transactional
    public String registerUser(RegisterRequest req){
        if (customerRepository.existsByEmail(req.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        Customer customer = new Customer();
        customer.setName(req.name());
        customer.setEmail(req.email());
        customerRepository.save(customer);

        User user = new User();
        user.setUsername(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setCustomer(customer);
        userRepository.save(user);

        return "User created with username "+req.email();
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new AuthResponse(jwtService.generateToken(user.getUsername(), user.getRole()));
    }
}
