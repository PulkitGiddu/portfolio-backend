package com.portfolio.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SessionDebugFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        log.info("Session Debug - Path: {}, Session exists: {}, Session ID: {}",
                path,
                session != null,
                session != null ? session.getId() : "NONE");

        // Log cookies
        if (httpRequest.getCookies() != null) {
            for (var cookie : httpRequest.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    log.info("SESSION ID Cookie found: {}", cookie.getValue());
                }
            }
        } else {
            log.info("🍪 No cookies in request");
        }

        chain.doFilter(request, response);
    }
}
