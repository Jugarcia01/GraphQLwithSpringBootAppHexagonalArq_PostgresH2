package com.jgo.demo_graphql.infrastructure.outputport;

import com.jgo.demo_graphql.infrastructure.outputadapter.mongodb.MongodbRepositoryImpl;
import com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb.RelationalDbRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityRepositoryFactoryImpl implements EntityRepositoryFactory {

    @Value("${repository.type}")
    private String repositoryType;

    private final RelationalDbRepositoryImpl relationalDbRepository;
    // private final MongodbRepositoryImpl mongodbRepository;

    public EntityRepositoryFactoryImpl(
            RelationalDbRepositoryImpl relationalDbRepository
            //, MongodbRepositoryImpl mongodbRepository
    ) {
        this.relationalDbRepository = relationalDbRepository;
        // this.mongodbRepository = mongodbRepository;
    }

    @Override
    public EntityRepository buildEntityRepository() {
        if ("r2dbRepository".equalsIgnoreCase(repositoryType)) {
            return relationalDbRepository;
        }
        // if ("mongodbRepository".equalsIgnoreCase(repositoryType)) {
        //     log.info("Executing %s".formatted(repositoryType));
        //     return mongodbRepository;
        // }
        else {
            log.error(String.format("Repository Type not found %s", repositoryType));
            throw new RuntimeException(String.format("Repository Type not found %s", repositoryType));
        }
    }

}
