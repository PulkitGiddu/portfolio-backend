package com.portfolio.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class ServerPortConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        String portStr = System.getenv("PORT");

        if (StringUtils.hasText(portStr)) {
            try {
                int port = Integer.parseInt(portStr);
                factory.setPort(port);
                log.info("Server binding to PORT: {}", port);
            } catch (NumberFormatException e) {
                log.warn("Invalid PORT env variable '{}', defaulting to 8080", portStr);
                factory.setPort(8080);
            }
        } else {
            log.info("No PORT env variable found, defaulting to 8081");
            factory.setPort(8080);
        }
    }
}
