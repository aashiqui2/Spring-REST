package com.springrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        // Info section → Basic information about your API
        info = @Info(
                title = "Demo",                // Title of the API (appears in Swagger UI header)
                version = "1.0.0",             // Version of your API
                description = "Documentation for Demo application.", // Short description of the API

                // Contact details → Helps users know who to reach out to
                contact = @Contact(
                        name = "Ashiq ",   // Name of the maintainer/author
                        url = "https://github.com/aashiqui2", // Website/portfolio/GitHub
                        email = "ahikmail2747@gmail.com" // Contact email
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",       // Base URL for API requests
                        description = "Development Server"   // Label shown in Swagger UI
                )
        }
)
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // mention 2 star
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

