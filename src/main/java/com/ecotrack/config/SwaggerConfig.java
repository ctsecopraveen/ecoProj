package com.ecotrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecoTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoTrack API - Module 4.4")
                        .description(
                                "Industry Emissions & Compliance Logging APIs"
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("EcoTrack Team")
                                .email("support@ecotrack.com")
                        )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ecotrack-module-4.4")
                .pathsToMatch("/api/**")
                .build();
    }
}