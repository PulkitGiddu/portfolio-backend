package com.portfolio.config;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class AppConfig {

    public String getContactEmail() {
        return getEnv("CONTACT_EMAIL", "pulkitgiddu09@gmail.com");
    }

    public String getAdminEmail() {
        return getEnv("ADMIN_EMAIL", "pulkitgiddu09@gmail.com");
    }

    public String getFrontendUrl() {
        return getEnv("FRONTEND_URL", "https://pulkit-portfolio-lemon.vercel.app");
    }

    public List<String> getAllowedOrigins() {
        String origins = getEnv("ALLOWED_ORIGINS",
                "https://pulkit-portfolio-lemon.vercel.app,http://localhost:5173,http://localhost:5174");
        return Arrays.asList(origins.split(","));
    }

    private String getEnv(String key, String defaultValue) {
        String val = System.getenv(key);
        return (val != null && !val.isBlank()) ? val : defaultValue;
    }
}
