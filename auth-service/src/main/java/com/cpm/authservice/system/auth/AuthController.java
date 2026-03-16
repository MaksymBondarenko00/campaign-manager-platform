package com.cpm.authservice.system.auth;

import com.cpm.authservice.system.auth.dto.AuthRequest;
import com.cpm.authservice.system.auth.dto.AuthResponse;
import com.cpm.authservice.system.auth.dto.RegisterRequest;
import com.cpm.authservice.system.exceptions.EmailAlreadyUsedException;
import com.cpm.authservice.system.exceptions.InvalidEmailException;
import com.cpm.authservice.system.exceptions.UserNotEnabledException;
import com.cpm.authservice.system.security.token.VerificationTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;
    VerificationTokenService verificationTokenService;

    @PostMapping("/register")
    public void registerAccount(@RequestBody RegisterRequest request) throws EmailAlreadyUsedException, InvalidEmailException {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws UserNotEnabledException {
        return authService.authenticate(request);
    }

    @GetMapping("/verify")
    public void verify(@RequestParam("token") String token) {
        verificationTokenService.validateToken(token);
    }
}
