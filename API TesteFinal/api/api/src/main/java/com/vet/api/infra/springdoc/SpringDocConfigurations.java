package com.vet.api.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API Clínica Veterinária")
                .version("1.0")
                .description("API REST para cadastro de clientes, pets, veterinários e agendamento de consultas.")
                .contact(new Contact().name("Clínica Veterinária")));
    }
}
