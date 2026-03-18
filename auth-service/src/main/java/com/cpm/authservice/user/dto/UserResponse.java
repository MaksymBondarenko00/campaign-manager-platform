package com.cpm.authservice.user.dto;

public record UserResponse (
        String firstName,
        String lastName,
        String email
) {
}