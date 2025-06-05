package com.example.accessapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("AccessAPP API")
                .version("1.0")
                .description(
                    "AccessAPP: General-Purpose Authentication & Authorization API System\n\n" +
                    "**How to Test:**\n" +
                    "- Use `/auth/register` to create a user.\n" +
                    "- Use `/auth/login` to get a JWT token.\n" +
                    "- Click the 'Authorize' button and paste your JWT token to access protected endpoints.\n" +
                    "- Use `/roles`, `/permissions`, `/users`, etc. for role/permission management.\n" +
                    "- Use `/auth/refresh` to refresh tokens.\n" +
                    "- Use `/auth/logout` to invalidate tokens.\n\n" +
                    "All endpoints are documented below. Try them directly from this UI!"
                )
            );
    }
} 