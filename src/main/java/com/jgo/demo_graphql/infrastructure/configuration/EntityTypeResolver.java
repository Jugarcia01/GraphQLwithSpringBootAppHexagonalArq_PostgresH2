package com.jgo.demo_graphql.infrastructure.configuration;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Configuration class that determines which entity implementation to use
 * based on the active profile.
 */
@Configuration
public class EntityTypeResolver {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final Environment environment;

    public EntityTypeResolver(Environment environment) {
        this.environment = environment;
    }

    /**
     * Determines if the application is running with H2 database
     */
    @Bean
    public boolean isH2Database() {
        return Arrays.asList(environment.getActiveProfiles()).contains("dev");
    }

    /**
     * Determines if the application is running with PostgreSQL database
     */
    @Bean
    public boolean isPostgresDatabase() {
        return Arrays.asList(environment.getActiveProfiles()).contains("test");
    }
}
