package com.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


//Top-level OpenAPI metadata annotation for Swagger documentation
@OpenAPIDefinition(
		
 // Info section → Basic information about your API
 info = @Info(
     title = "Demo",                // Title of the API (appears in Swagger UI header)
     version = "1.0.0",             // Version of your API
     description = "Documentation for Demo application.", // Short description of the API

     // Contact details → Helps users know who to reach out to
     contact = @Contact(
         name = "Ashik B",   // Name of the maintainer/author
         url = "https://aashiqui.github.io", // Website/portfolio/GitHub
         email = "ashikmail2747@gmail.com" // Contact email
     )
 ),

 // Server section → Defines one or more servers where this API can be accessed
 servers = {
     @Server(
         url = "http://localhost:8080",       // Base URL for API requests
         description = "Development Server"   // Label shown in Swagger UI
     )
     // You can add more servers here (e.g., staging, production)
 }
)

@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo {

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}

}
