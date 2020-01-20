package com.jclz.fruit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")//localhost  39.104.157.227
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION")
                        .allowCredentials(true)
                        .maxAge(100);
            }

        };
    }

}