package com.jgo.demo_graphql.infrastructure.outputadapter.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomerExistsException extends RuntimeException implements GraphQLError {

    private final UUID customerId;
    private List<Object> path;

    public CustomerExistsException(UUID customerId) {
        super(String.format("Customer with the id %s already exists", customerId));
        this.customerId = customerId;
        // Default path will be set to createCustomer
        this.path = Collections.singletonList("createCustomer");
    }

    /**
     * Constructor with path parameter to specify the GraphQL path
     */
    public CustomerExistsException(UUID customerId, List<Object> path) {
        super(String.format("Customer with the id %s already exists", customerId));
        this.customerId = customerId;
        this.path = path;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public List<Object> getPath() {
        return this.path;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.emptyMap();
    }
}