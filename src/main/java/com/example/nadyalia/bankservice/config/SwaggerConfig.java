package com.example.nadyalia.bankservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Banking Application",
                description = "Backend microservice for banking operations without a frontend component. " +
                        "The microservice will expose a RESTful API to perform various banking " +
                        "transactions and manage clients accounts.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Nadzeya L.",
                        email = "nadzeya.liashchynskaya@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
