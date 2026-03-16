package com.cpm.authservice.system.security.jwt;

public record JwtUser(
        Long id,
        String email
) {
}
