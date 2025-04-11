package com.jgo.demo_graphql.infrastructure.outputadapter.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String email) {
        super(String.format("Customer with the email %s not found", email));
    }

}