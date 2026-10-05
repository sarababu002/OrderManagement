package com.example.order_managment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(String name, @NotBlank @Email String email, @NotBlank @Size(min=8) String password) {}
