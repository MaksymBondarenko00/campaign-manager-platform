package com.cpm.authservice.system.notifications.email;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
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

    @Value("${spring.mail.username:}")
    private String from;

    @Value("${SENDGRID_API_KEY:}")
    private String sendgridApiKey;

    @Value("${FROM_EMAIL}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String token) {

        String verificationLink = "https://cmp-front.onrender.com/auth/verify?token=" + token;

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

        // Try to send by SMTP
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("Email sent via SMTP to {}", to);
            return;

        } catch (Exception e) {
            log.warn("SMTP failed, fallback to SendGrid: {}", e.getMessage());
        }

        // Fallback for SendGrid (Render)
        try {
            Email fromEmailObj = new Email(fromEmail);
            Email toEmail = new Email(to);

            Content content = new Content("text/plain", text);
            Mail mail = new Mail(fromEmailObj, subject, toEmail, content);

            SendGrid sg = new SendGrid(sendgridApiKey);
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid error: " + response.getBody());
            }

            log.info("Email sent via SendGrid to {}", to);

        } catch (Exception ex) {
            log.error("SendGrid ALSO failed", ex);

            //  fallback - log
            log.warn("VERIFICATION LINK (fallback): {}", verificationLink);
        }
    }
}