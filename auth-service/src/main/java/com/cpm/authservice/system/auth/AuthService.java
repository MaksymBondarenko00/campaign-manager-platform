package com.cpm.authservice.system.auth;


import com.cpm.authservice.system.auth.dto.AuthRequest;
import com.cpm.authservice.system.auth.dto.AuthResponse;
import com.cpm.authservice.system.auth.dto.RegisterRequest;
import com.cpm.authservice.system.exceptions.EmailAlreadyUsedException;
import com.cpm.authservice.system.exceptions.InvalidEmailException;
import com.cpm.authservice.system.exceptions.UserNotEnabledException;
import com.cpm.authservice.system.notifications.email.EmailService;
import com.cpm.authservice.system.notifications.email.EmailValidator;
import com.cpm.authservice.system.security.jwt.JwtService;
import com.cpm.authservice.system.security.token.VerificationTokenService;
import com.cpm.authservice.user.UserMapper;
import com.cpm.authservice.user.UserRepository;
import com.cpm.authservice.user.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    JwtService jwtService;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    VerificationTokenService verificationTokenService;
    EmailService emailService;

    public void register(RegisterRequest registerRequest) throws EmailAlreadyUsedException, InvalidEmailException {

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new EmailAlreadyUsedException("Email already used");
        }

        if (!EmailValidator.isValid(registerRequest.email())) {
            throw new InvalidEmailException("Invalid email");
        }

        var user = userMapper.toEntity(registerRequest);
        user.setSystemRoles(Set.of(Role.GUEST));
        user.setEncodedPassword(passwordEncoder.encode(registerRequest.password()));

        userRepository.save(user);

        String token = String.valueOf(verificationTokenService.generateToken(user).getToken());

        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    public AuthResponse authenticate(AuthRequest authRequest) throws UserNotEnabledException {
        var user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getEnabled()) throw new UserNotEnabledException("User not enabled");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        return new AuthResponse(
                jwtService.generateToken(user)
        );

    }
}
