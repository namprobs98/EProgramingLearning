package com.eprogramming.learning.service;

import com.eprogramming.learning.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Gửi mail giống {@code EmailService} trong TodoWeb (SimpleMailMessage + Gmail SMTP).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.mock:false}")
    private boolean mockMail;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String verificationLink) {
        if (mockMail) {
            log.warn("MOCK MAIL MODE - Verification link for {} is {}", to, verificationLink);
            return;
        }
        validateSmtpConfig();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Kich hoat tai khoan E-Programming Learning");
        message.setText("""
                Xin chao,

                Bam link sau de xac thuc email va kich hoat tai khoan (het han sau 15 phut):

                %s

                Neu ban khong dang ky, hay bo qua email nay.
                """.formatted(verificationLink));

        try {
            mailSender.send(message);
            log.info("Verification email sent to {}", to);
        } catch (MailException ex) {
            String detail = ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage();
            throw new ApiException("Khong gui duoc email xac thuc. Chi tiet: " + detail, HttpStatus.BAD_GATEWAY);
        }
    }

    private void validateSmtpConfig() {
        if (!StringUtils.hasText(fromEmail)) {
            throw new ApiException(
                    "Chua cau hinh Gmail. Sua backend/src/main/resources/application-local.yml "
                            + "(spring.mail.username + password) HOAC $env:MAIL_USERNAME / MAIL_PASSWORD. "
                            + "Kiem tra: GET /api/v1/dev/mail/status",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
