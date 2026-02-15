package com.portfolio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EnvCheck implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("================ ENV CHECK ================");
        logEnv("DB_URL");
        logProp("spring.datasource.url");
        logEnv("DB_USERNAME");
        logEnv("SPRING_PROFILES_ACTIVE");
        System.out.println("Active Profiles: " + java.util.Arrays.toString(env.getActiveProfiles()));
        System.out.println("===========================================");
    }

    private void logEnv(String key) {
        String val = System.getenv(key);
        System.out.println("ENV [" + key + "]: " + (val != null ? "PRESENT (len=" + val.length() + ")" : "MISSING"));
    }

    private void logProp(String key) {
        String val = env.getProperty(key);
        System.out.println("PROP [" + key + "]: " + (val != null ? "PRESENT (len=" + val.length() + ")" : "MISSING"));
    }
}
