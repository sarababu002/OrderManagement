package com.example.order_managment.dto;


import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank  String username,@NotBlank String password) {}
