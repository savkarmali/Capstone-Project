package com.capstone.survey.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI surveyManagementOpenApi() {
        return new OpenAPI()
                .info(apiInfo())
                .components(securityComponents())
                .security(List.of(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)));
    }

    private Info apiInfo() {
        return new Info()
                .title("Campaign Survey Management System API")
                .description("REST API documentation for Survey Management System backend")
                .version("1.0.0")
                .contact(apiContact())
                .license(apiLicense());
    }

    private Contact apiContact() {
        return new Contact()
                .name("Survey Management Team")
                .email("support@survey-management.com");
    }

    private License apiLicense() {
        return new License()
                .name("Internal Capstone Project License")
                .url("https://survey-management.com/license");
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes(
                        SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }
}