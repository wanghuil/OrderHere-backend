package com.backend.OrderHere.config;

public class StaticConfig {
    public final static String[] ignoreUrl = new String[] {
            // -- Swagger UI v2
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // -- API
            "/v1/public/**",
    };

    // JWT
    public final static String JwtSecretKey = "JwtSecretKey";
    public final static String JwtPrefix = "Bearer ";
}
