package br.com.portaldbv;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Portal DBV - Sistema de Gerenciamento - Desbravadores",
                version = "1.0",
                description = "Aplicação responsável por gerenciar clubes de desbravadores"
        )
)
@SpringBootApplication
public class PortalDbvApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalDbvApplication.class, args);
    }

}