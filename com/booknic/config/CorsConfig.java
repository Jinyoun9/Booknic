package com.booknic.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins from the client side
        config.addAllowedOrigin("https://localhost:3000");

        // Allow specific methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Allow specific headers (Content-Type, Authorization, etc.)
        config.addAllowedHeader("*");

        config.setExposedHeaders(Collections.singletonList("Authorization"));

        // Allow credentials (e.g., cookies)
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
