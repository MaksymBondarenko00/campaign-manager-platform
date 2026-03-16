package com.cpm.authservice.system.auth.dto;

public record AuthRequest(
        String email,
        String password
) { }
