package edu.eci.arsw.blueprints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot main application class for the Blueprints REST API.
 * This class configures and starts the Spring Boot application.
 * 
 * @author Diego Cardenas
 */
@SpringBootApplication
public class BlueprintsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueprintsApplication.class, args);
    }
}