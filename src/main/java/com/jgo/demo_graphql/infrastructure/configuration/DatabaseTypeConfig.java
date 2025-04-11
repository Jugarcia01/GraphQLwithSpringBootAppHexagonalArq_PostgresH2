package com.jgo.demo_graphql.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class to handle database-specific type mappings
 * based on active profile (dev for H2, test for PostgreSQL)
 */
@Configuration
public class DatabaseTypeConfig {

    /**
     * Bean that indicates whether to use UUIDCharType (true for H2, false for PostgreSQL)
     */
    @Bean
    @Profile("dev")
    public boolean useUuidCharType() {
        return true;
    }

    @Bean
    @Profile("test")
    public boolean usePostgresUuidType() {
        return false;
    }
}
