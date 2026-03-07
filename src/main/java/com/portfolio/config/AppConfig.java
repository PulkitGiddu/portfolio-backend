package com.portfolio.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AppConfig {

    @Getter
    @Value("${app.contact.email:pulkitgiddu09@gmail.com}")
    private String contactEmail;

    @Getter
    @Value("${app.admin.email:pulkitgiddu09@gmail.com}")
    private String adminEmail;

    @Getter
    @Value("${app.frontend.url:https://pulkit-portfolio-lemon.vercel.app}")
    private String frontendUrl;

    @Value("${app.cors.allowed-origins:https://pulkit-portfolio-lemon.vercel.app,http://localhost:5173,http://localhost:5174}")
    private String allowedOrigins;

    public List<String> getAllowedOrigins() {
        return Arrays.asList(allowedOrigins.split(","));
    }

    @PostConstruct
    public void logConfiguration() {
        log.info("Frontend URL: {}", frontendUrl);
        log.info("Admin email: {}", adminEmail);
        log.info("CORS origins: {}", allowedOrigins);
    }
}
