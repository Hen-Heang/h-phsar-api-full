package com.henheang.hphsar.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BeanConfig — Global Shared Beans
 * <p>
 * Registers general-purpose beans that are reused across the application.
 * These are created once and injected wherever needed via Spring DI.
 * <p>
 * Beans defined here:
 *   - PasswordEncoder : hashes passwords before saving to DB (used in: BeanConfig → SecurityConfig → DaoAuthenticationProvider)
 */
@Configuration
public class BeanConfig {

    /**
     * BCrypt password encoder.
     * Automatically salts and hashes passwords — never stores plain text.
     * Used by SecurityConfig's DaoAuthenticationProvider to verify login passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Allows sending a single string instead of an array for List<String> fields.
    // e.g. "additionalPhone": "0123456789" works the same as ["0123456789"]
    @Bean
    @Primary
    ObjectMapper objectMapper() {
        return new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }
}
