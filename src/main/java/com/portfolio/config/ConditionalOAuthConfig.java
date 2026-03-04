package com.portfolio.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collections;

/**
 * Provides a fallback OAuth configuration ONLY when credentials are explicitly
 * disabled.
 * This prevents the application from failing to start in local dev without
 * Google OAuth credentials.
 * 
 * In production, GOOGLE_CLIENT_ID and GOOGLE_CLIENT_SECRET MUST be set as
 * environment variables on Render.
 */
@Configuration
@ConditionalOnProperty(name = "spring.security.oauth2.client.registration.google.client-id", havingValue = "", matchIfMissing = true)
public class ConditionalOAuthConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // Dummy registration - OAuth login won't work without real credentials
        // but the app will still start for dev/testing purposes
        ClientRegistration dummyRegistration = ClientRegistration
                .withRegistrationId("google")
                .clientId("not-configured")
                .clientSecret("not-configured")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();

        return new InMemoryClientRegistrationRepository(Collections.singletonList(dummyRegistration));
    }
}
