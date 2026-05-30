package com.eprogramming.learning.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class MailStartupLogger {

    @Value("${app.mail.mock:false}")
    private boolean mockMail;

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.username:}")
    private String username;

    @EventListener(ApplicationReadyEvent.class)
    public void logMailMode() {
        if (mockMail) {
            log.warn("MAIL: MOCK mode (MAIL_MOCK=true) — khong gui SMTP, chi log link");
            return;
        }
        if (StringUtils.hasText(username)) {
            log.info("MAIL: SMTP san sang — host={}, from={} (Gmail gui toi @fpt.edu.vn OK)", host, username);
        } else {
            log.error("MAIL: Thieu MAIL_USERNAME — tao backend/.env tu .env.example (giong todo-backend)");
        }
    }
}
