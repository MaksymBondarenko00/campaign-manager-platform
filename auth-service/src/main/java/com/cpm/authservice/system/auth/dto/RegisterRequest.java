package com.cpm.authservice.system.auth.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) { }
