package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EmployeeServiceApplication {
    public static void main(String[] args) {
        // Load .env variables before starting the Spring Boot app
        Dotenv dotenv = Dotenv.load();

        // Set the environment variables as system properties, so Spring can resolve ${DB_URL}, etc.
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(EmployeeServiceApplication.class, args);
    }
}
