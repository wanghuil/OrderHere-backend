package com.backend.OrderHere.config;

public class StaticConfig {
    public final static String[] ignoreUrl = new String[] {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- API
            "/v1/public/**",
    };

    // JWT
    public final static String JwtSecretKey = "JwtSecretKey";
    public final static String JwtPrefix = "Bearer ";
}