package com.jgo.demo_graphql.infrastructure.configuration;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.UUIDBinaryType;
import org.hibernate.type.UUIDCharType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that dynamically registers the appropriate UUID type handler
 * based on the active profile.
 */
@Configuration
public class UuidTypeConfiguration {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * This method will be called by Hibernate during SessionFactory initialization
     * and will register the appropriate UUID type handler based on the active profile.
     */
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        if ("dev".equals(activeProfile)) {
            // For H2 database (dev profile), use UUIDCharType
            typeContributions.contributeType(UUIDCharType.INSTANCE);
        } else if ("test".equals(activeProfile)) {
            // For PostgreSQL database (test profile), use UUIDBinaryType
            typeContributions.contributeType(UUIDBinaryType.INSTANCE);
        }
    }
}
