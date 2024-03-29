package com.vietle.billmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${origin}")
    private String origin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/api/v1/**")
            .allowedOrigins(origin)
            .allowedMethods("*")
            .allowCredentials(true)
            .allowedHeaders("*");
    }
}
