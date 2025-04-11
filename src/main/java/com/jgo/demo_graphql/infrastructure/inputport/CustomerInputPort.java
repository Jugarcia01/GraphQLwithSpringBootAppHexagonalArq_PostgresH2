package com.jgo.demo_graphql.infrastructure.inputport;

import com.jgo.demo_graphql.application.dto.CustomerDto;
import com.jgo.demo_graphql.domain.model.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface CustomerInputPort {

  ResponseEntity<Customer> createCustomer(CustomerDto customerDto);

  ResponseEntity<Customer> updateCustomer(CustomerDto customerDto);

  ResponseEntity<Customer> getCustomerById(UUID id);

  ResponseEntity<Customer> getCustomerByEmail(String email);

  ResponseEntity<List<Customer>> getAllCustomers();

  Boolean deleteCustomerById(UUID id);

  Boolean deleteCustomerByEmail(String email);

}
