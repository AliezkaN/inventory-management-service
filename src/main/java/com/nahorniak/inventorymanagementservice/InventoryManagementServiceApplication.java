package com.nahorniak.inventorymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementServiceApplication.class, args);
    }

    @Bean public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/retail-flow-manager/api");
    }

}
