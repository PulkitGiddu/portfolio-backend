package com.portfolio.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ServerPortConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        String portStr = System.getenv("PORT");
        System.out.println("==> Configuring Server Port Programmatically");

        if (StringUtils.hasText(portStr)) {
            try {
                int port = Integer.parseInt(portStr);
                factory.setPort(port);
                System.out.println("✅ PORT environment variable detected: " + port);
                System.out.println("==> Server will bind to port: " + port);
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid PORT environment variable: " + portStr);
                System.out.println("==> Defaulting to 8080");
                factory.setPort(8080);
            }
        } else {
            System.out.println("⚠️ No PORT environment variable found");
            System.out.println("==> Defaulting to 8080");
            factory.setPort(8080);
        }
    }
}
