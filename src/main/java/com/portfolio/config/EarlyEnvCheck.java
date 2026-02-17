package com.portfolio.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class EarlyEnvCheck implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        check(env, "DB_URL");
        check(env, "SPRING_PROFILES_ACTIVE");
        check(env, "DB_USERNAME");
    }

    private void check(ConfigurableEnvironment env, String key) {
        String val = env.getProperty(key);
        String raw = System.getenv(key);
        System.out.println("EARLY " + key + ": Property=" + (val != null) + ", EnvVar=" + (raw != null));
        if (val != null)
            System.out.println(" -> Val (len): " + val.length());
    }
}
