package com.currencyexchange.api.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final List<String> SWAGGER_PATHS = Arrays.asList(
            "/api-docs", "/swagger-ui.html", "/swagger-ui/*", "/v3/api-docs/*"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Bypass logging for Swagger APIs
        if (SWAGGER_PATHS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Log request details
        LOGGER.info("Incoming Request: method={}, uri={} ", request.getMethod(), requestURI);

        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);

        // Log response details
        LOGGER.info("Outgoing Response: status={}", response.getStatus());
    }
}