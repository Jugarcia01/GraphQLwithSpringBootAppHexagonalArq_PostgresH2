package com.jgo.demo_graphql.infrastructure.outputadapter.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio MongoDB para la entidad CustomerMongo
 */
@Repository
public interface CustomerMongoRepository extends MongoRepository<CustomerMongo, UUID> {
    
    /**
     * Busca clientes por su email
     * @param email Email del cliente
     * @return Lista opcional de clientes que coinciden con el email
     */
    Optional<List<CustomerMongo>> findByEmail(String email);
}
