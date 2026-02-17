package com.portfolio.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collections;

/**
 * Provides a fallback OAuth configuration when credentials are not available.
 * This prevents the application from failing to start when GOOGLE_CLIENT_ID
 * and GOOGLE_CLIENT_SECRET environment variables are not set.
 */
@Configuration
public class ConditionalOAuthConfig {

    /**
     * Creates a minimal ClientRegistrationRepository bean when OAuth is not
     * configured.
     * This allows the application to start successfully even without OAuth
     * credentials.
     * OAuth login will simply not work, but the rest of the application functions
     * normally.
     */
    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    public ClientRegistrationRepository clientRegistrationRepository() {
        // Create a dummy client registration to satisfy Spring Security's requirements
        // This will never actually be used since there's no way to trigger OAuth flow
        // without valid config
        ClientRegistration dummyRegistration = ClientRegistration
                .withRegistrationId("google")
                .clientId("dummy-client-id")
                .clientSecret("dummy-client-secret")
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
