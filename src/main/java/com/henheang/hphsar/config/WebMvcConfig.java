package com.henheang.hphsar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Web MVC Configuration
 * <p>
 * Maps uploaded files on disk to a public HTTP URL so clients can access them.
 * <p>
 * Example:
 *   - File saved to  : ./uploads/photo.jpg  (configured in application.properties)
 *   - Accessible via : GET /api/v1/files/photo.jpg
 * <p>
 * The upload directory is set via the property: file.upload-dir
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Injected from application.properties: file.upload-dir=uploads/
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Registers a resource handler that serves files from the upload directory.
     * <p>
     * - addResourceHandler : the URL pattern clients use to request a file
     * - addResourceLocations: the absolute path on disk where files are stored
     * <p>
     * Paths.get(...).toAbsolutePath().normalize() ensures the path is
     * resolved correctly regardless of the OS or working directory.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString();
        registry.addResourceHandler("/api/v1/files/**")
                .addResourceLocations(uploadPath);
    }
}