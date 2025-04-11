package com.jgo.demo_graphql.infrastructure.configuration;

import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepositoryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Value("${repository.type}")
    private final String repositoryType = "r2dbRepository";
    // private final String repositoryType = "mongodbRepository";

    private final EntityRepositoryFactory entityRepositoryFactory;

    public RepositoryConfig(EntityRepositoryFactory entityRepositoryFactory) {
        this.entityRepositoryFactory = entityRepositoryFactory;
    }

    @Bean
    @Qualifier(repositoryType)
    public EntityRepository entityRepository() {
        return entityRepositoryFactory.buildEntityRepository();
    }
}
