package com.eprogramming.learning.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private String frontendUrl = "http://localhost:3000";
    private Jwt jwt = new Jwt();
    private Verification verification = new Verification();

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long expirationMs = 86400000L;
    }

    @Getter
    @Setter
    public static class Verification {
        private int expiryMinutes = 15;
    }
}
