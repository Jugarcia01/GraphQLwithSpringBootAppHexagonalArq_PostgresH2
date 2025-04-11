package com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb;

import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.domain.port.CustomerRepository;
import com.jgo.demo_graphql.domain.repository.relational.CustomerRepositoryJpa;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.jgo.demo_graphql.infrastructure.mapper.CustomerMapper.CUSTOMER_MAPPER;

/**
 * Adaptador que implementa el puerto CustomerRepository.
 * Esta clase es un adaptador secundario (driven adapter) que conecta
 * el dominio con la infraestructura de persistencia.
 */
@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerRepositoryJpa customerRepositoryJpa;
    
    @PersistenceContext
    private EntityManager entityManager;

    public CustomerRepositoryAdapter(CustomerRepositoryJpa customerRepositoryJpa) {
        this.customerRepositoryJpa = customerRepositoryJpa;
    }

    @Override
    public Customer findById(UUID uuid) {
        return customerRepositoryJpa.findById(uuid)
                .map(CUSTOMER_MAPPER::toCustomer)
                .orElse(null);
    }

    @Override
    public Customer findByEmail(String email) {
        Optional<List<CustomerR2db>> customerOptional = customerRepositoryJpa.findByEmail(email);
        
        if (customerOptional.isPresent() && !customerOptional.get().isEmpty()) {
            return CUSTOMER_MAPPER.toCustomer(customerOptional.get().get(0));
        }
        return null;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        CustomerR2db customerR2db = CUSTOMER_MAPPER.toCustomerPostgres(customer);
        CustomerR2db saved = customerRepositoryJpa.save(customerR2db);
        return CUSTOMER_MAPPER.toCustomer(saved);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        CustomerR2db customerR2db = CUSTOMER_MAPPER.toCustomerPostgres(customer);
        
        // Fetch the existing customer with its orders to preserve the relationship
        CustomerR2db existingCustomer = entityManager.find(CustomerR2db.class, customerR2db.getUuid());
        if (existingCustomer != null) {
            // Preserve the existing orders to prevent orphan removal
            customerR2db.setOrders(existingCustomer.getOrders());
        }
        
        CustomerR2db updated = entityManager.merge(customerR2db);
        return CUSTOMER_MAPPER.toCustomer(updated);
    }

    @Override
    public List<Customer> findAll() {
        Optional<List<CustomerR2db>> customersWithOrders = customerRepositoryJpa.findAllWithOrders();

        return customersWithOrders.map(customerPostgres -> customerPostgres.stream()
                .map(CUSTOMER_MAPPER::toCustomer)
                .toList()).orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID uuid) {
        if (customerRepositoryJpa.existsById(uuid)) {
            customerRepositoryJpa.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteByEmail(String email) {
        Optional<List<CustomerR2db>> customerOptional = customerRepositoryJpa.findByEmail(email);
        
        if (customerOptional.isPresent() && !customerOptional.get().isEmpty()) {
            CustomerR2db customer = customerOptional.get().get(0);
            customerRepositoryJpa.delete(customer);
            return true;
        }
        return false;
    }
}
