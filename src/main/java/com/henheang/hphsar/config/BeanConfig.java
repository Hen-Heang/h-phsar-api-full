package com.henheang.hphsar.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BeanConfig — Global Shared Beans
 *
 * Registers general-purpose beans that are reused across the application.
 * These are created once and injected wherever needed via Spring DI.
 *
 * Beans defined here:
 *   - PasswordEncoder : hashes passwords before saving to DB (used in: BeanConfig → SecurityConfig → DaoAuthenticationProvider)
 *   - ModelMapper     : converts between Entity ↔ DTO objects (used in service layer)
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

    /**
     * ModelMapper for object mapping.
     * Converts entity objects (DB models) to DTO objects (API response models) and vice versa.
     * Example: AppUser (entity) → AppUserDto (response sent to client)
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
