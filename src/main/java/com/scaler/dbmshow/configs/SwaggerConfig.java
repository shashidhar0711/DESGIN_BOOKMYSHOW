package com.scaler.dbmshow.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("AUTH_TOKEN");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("AUTH_TOKEN", securityScheme))
                .addSecurityItem(new SecurityRequirement()
                        .addList("AUTH_TOKEN"));
    }
}
