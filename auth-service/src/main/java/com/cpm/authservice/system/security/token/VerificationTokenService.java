package com.cpm.authservice.system.security.token;


import com.cpm.authservice.system.clients.AccountClient;
import com.cpm.authservice.user.User;
import com.cpm.authservice.user.UserRepository;
import com.cpm.authservice.user.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationTokenService {

    VerificationTokenRepository verificationTokenRepository;
    UserRepository userRepository;
    AccountClient accountClient;

    public VerificationToken generateToken(User user) {

        var token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusHours(24)); // 24h lifetime

        return verificationTokenRepository.save(token);
    }

    public void validateToken(String tokenValue) {

        var token = verificationTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));
        var user = token.getUser();

        if (token.isUsed()) {
            throw new IllegalStateException("Token already used");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        user.setEnabled(true);
        user.getSystemRoles().add(Role.ACCOUNT_OWNER);
        userRepository.save(user);

        markUsed(token);

        accountClient.createAccount(user.getId());
    }

    public void markUsed(VerificationToken token) {
        token.setUsed(true);
        verificationTokenRepository.save(token);
    }
}
