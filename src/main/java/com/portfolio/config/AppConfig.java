package com.portfolio.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AppConfig {

    @Autowired
    private Environment environment;

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
        log.error(
                "====================================================================================================");
        log.error(
                "                                 DIAGNOSTIC CONFIG CHECK                                            ");
        log.error(
                "====================================================================================================");

        // 1. Check Active Profiles
        String[] activeProfiles = environment.getActiveProfiles();
        log.error("ACTIVE PROFILES: {}", Arrays.toString(activeProfiles));

        // 2. Check spring.profiles.active property directly
        String profilesProp = environment.getProperty("spring.profiles.active");
        log.error("PROPERTY 'spring.profiles.active': '{}'", profilesProp);

        // 3. Check Mail Configuration (masked)
        String mailUser = environment.getProperty("spring.mail.username");
        String mailHost = environment.getProperty("spring.mail.host");
        log.error("MAIL HOST: '{}'", mailHost);
        log.error("MAIL USER: '{}'", maskValue(mailUser));

        // 4. Check OAuth Configuration (masked)
        String clientId = environment.getProperty("spring.security.oauth2.client.registration.google.client-id");
        log.error("GOOGLE CLIENT ID: '{}'", maskValue(clientId));

        // 5. Check URL Configuration
        log.error("FRONTEND URL: '{}'", frontendUrl);

        log.error(
                "====================================================================================================");
    }

    private String maskValue(String val) {
        if (val == null)
            return "NULL";
        if (val.isEmpty())
            return "EMPTY";
        if (val.length() <= 4)
            return "***";
        return val.substring(0, 4) + "***";
    }
}
