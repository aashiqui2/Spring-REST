package com.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //mention 2 star
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

