package com.miage2026.coffeechoc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coffee Choc API")
                        .description("API REST du site de commande de café en ligne - MIAGE UNC 2026 - Crée par Luca et Damien")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MIAGE UNC")
                                .email("contact@coffeechoc.nc")));
    }
}