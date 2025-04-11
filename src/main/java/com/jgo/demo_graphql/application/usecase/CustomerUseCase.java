package com.jgo.demo_graphql.application.usecase;

import com.jgo.demo_graphql.application.dto.CustomerDto;
import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.domain.port.CustomerRepository;
import com.jgo.demo_graphql.infrastructure.inputport.CustomerInputPort;
import com.jgo.demo_graphql.infrastructure.outputadapter.exception.CustomerExistsException;
import com.jgo.demo_graphql.infrastructure.outputadapter.exception.CustomerNotFoundException;
import com.jgo.demo_graphql.util.EmailToUUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jgo.demo_graphql.infrastructure.mapper.CustomerMapper.CUSTOMER_MAPPER;

@Slf4j
@Component
public class CustomerUseCase implements CustomerInputPort {

  private final CustomerRepository customerRepository;

  public CustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public ResponseEntity<Customer> getCustomerById(UUID uuid) {
    Customer foundCustomer = customerRepository.findById(uuid);

    if (foundCustomer == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }
  }

  @Override
  public ResponseEntity<Customer> createCustomer(CustomerDto customerDto) {
    try {
      Customer customer = CUSTOMER_MAPPER.toEntity(customerDto);
      UUID uuid = EmailToUUID.generateUUID(customer.getEmail());

      if (doesCustomerWithEmailExist(customer.getEmail(), uuid) || doesCustomerWithIDExist(uuid)) {
        log.error("Customer with email: {} already exists", customer.getEmail());
        throw new CustomerExistsException(uuid);
      }
      return createNewCustomer(customer, uuid);

    } catch (CustomerExistsException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error creating customer", e);
      throw new RuntimeException("Error creating customer: " + e.getMessage());
    }
  }

  private ResponseEntity<Customer> createNewCustomer(Customer customer, UUID uuid) {
    Customer newCustomer = new Customer(uuid,
            customer.getEmail(),
            customer.getName(),
            customer.getLastName(),
            customer.getOrders());
    
    Customer savedCustomer = customerRepository.save(newCustomer);
    log.info("New customer created with id: {}.", uuid);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
  }

  @Override
  public ResponseEntity<Customer> updateCustomer(CustomerDto customerDto) {
    Customer customer = CUSTOMER_MAPPER.toEntity(customerDto);
    UUID uuid = customer.getUuid();
    
    if (uuid == null) {
      uuid = EmailToUUID.generateUUID(customer.getEmail());
    }

    if (!doesCustomerWithIDExist(uuid) && !doesCustomerWithEmailExist(customer.getEmail(), uuid)) {
      throw new CustomerNotFoundException(customerDto.getEmail());
    } else {
      return updateExistingCustomer(customer, uuid);
    }
  }

  private ResponseEntity<Customer> updateExistingCustomer(Customer customer, UUID uuid) {
    Customer existingCustomer = customerRepository.findById(uuid);
    
    Customer updatedCustomer = new Customer(
            uuid,
            customer.getEmail(),
            customer.getName(),
            customer.getLastName(),
            existingCustomer != null ? existingCustomer.getOrders() : null);
    
    Customer result = customerRepository.update(updatedCustomer);
    log.info("Existing customer with id: {} updated.", uuid);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  private boolean doesCustomerWithEmailExist(String email, UUID uuid) {
    Customer foundCustomer = getCustomerByEmail(email).getBody();
    return foundCustomer != null && foundCustomer.getUuid().equals(uuid);
  }

  private boolean doesCustomerWithIDExist(UUID uuid) {
    return getCustomerById(uuid).hasBody();
  }

  @Override
  public ResponseEntity<Customer> getCustomerByEmail(String email) {
    Customer foundCustomer = customerRepository.findByEmail(email);

    if (foundCustomer == null) {
      log.info("Don't exist a customer with email: " + email);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return ResponseEntity.status(HttpStatus.FOUND).body(foundCustomer);
    }
  }

  @Override
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> foundCustomers = Optional.ofNullable(customerRepository.findAll())
            .orElse(Collections.emptyList());

    if (foundCustomers.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(foundCustomers, HttpStatus.OK);
  }

  @Override
  public Boolean deleteCustomerById(UUID uuid) {
    try {
      return customerRepository.deleteById(uuid);
    } catch (Exception e) {
      log.error("Error deleting customer", e);
      throw new RuntimeException("Error deleting customer: " + e.getMessage());
    }
  }

  @Override
  public Boolean deleteCustomerByEmail(String email) {
    try {
      return customerRepository.deleteByEmail(email);
    } catch (Exception e) {
      log.error("Error deleting customer by email", e);
      throw new RuntimeException("Error deleting customer by email: " + e.getMessage());
    }
  }
}
