package com.cpm.authservice.user.dto;

public record UserDto(
        Long id,
        String email,
        boolean enabled
) {
}