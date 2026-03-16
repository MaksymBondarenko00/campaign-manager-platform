package com.cpm.authservice.system.notifications.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendVerificationEmail(String to, String token) {

        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;

        String subject = "Confirm your email address";

        String text = """
                Hello,

                Thank you for registering in Campaign Manager Platform.

                Please confirm your email address by clicking the link below:

                %s

                This verification link will expire in 24 hours.

                If you did not create this account, you can safely ignore this email.

                Best regards,
                Campaign Manager Platform Team
                """.formatted(verificationLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

        log.info("Verification email sent to {}", to);
    }
}