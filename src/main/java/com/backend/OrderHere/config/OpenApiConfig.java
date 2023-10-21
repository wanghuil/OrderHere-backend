package com.backend.OrderHere.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI usersMicroserviceOpenAPI() {
    return new OpenAPI()
      .info(new Info().title("OrderHere API")
        .description("OrderHere API reference for developers")
        .version("1.0"));
  }
}
