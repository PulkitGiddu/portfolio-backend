package com.portfolio.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class EarlyEnvCheck implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        System.out.println("==== EARLY INVOKE ====");
        check(env, "DB_URL");
        check(env, "SPRING_PROFILES_ACTIVE");
        check(env, "DB_USERNAME");
        System.out.println("======================");
    }

    private void check(ConfigurableEnvironment env, String key) {
        String val = env.getProperty(key); // Check resolved property (Env var + System prop)
        String raw = System.getenv(key); // Check raw env var
        System.out.println("EARLY " + key + ": Property=" + (val != null) + ", EnvVar=" + (raw != null));
        if (val != null)
            System.out.println(" -> Val (len): " + val.length());
    }
}
