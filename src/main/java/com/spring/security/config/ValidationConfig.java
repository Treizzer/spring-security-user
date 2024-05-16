package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.security.services.models.validations.UserValidation;

@Configuration
public class ValidationConfig {

    @Bean
    public UserValidation userValidation() {
        return new UserValidation();
    }

}
