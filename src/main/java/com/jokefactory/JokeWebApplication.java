package com.jokefactory;

import com.jokefactory.integration.SQLiteIntegrator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JokeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JokeWebApplication.class, args);
    }
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try (SQLiteIntegrator integrator = new SQLiteIntegrator("jokes.db")) {
                // initTable already runs in constructor
                System.out.println("Database initialized.");
            }
        };
    }
}
