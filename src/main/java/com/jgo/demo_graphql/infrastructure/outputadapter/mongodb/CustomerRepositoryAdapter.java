package com.jgo.demo_graphql.infrastructure.outputadapter.mongodb;

import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.domain.port.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador para el repositorio de clientes en MongoDB
 */
// Enable / uncomment the annotation to use Mongo repo
// @Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerMongoRepository customerRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomerRepositoryAdapter(CustomerMongoRepository customerRepository
                                     , MongoTemplate mongoTemplate
    ) {
        this.customerRepository = customerRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Customer findById(UUID uuid) {
        return customerRepository.findById(uuid)
                .map(this::convertToCustomer)
                .orElse(null);
    }

    @Override
    public Customer findByEmail(String email) {
        Optional<List<CustomerMongo>> customerOptional = customerRepository.findByEmail(email);
        
        if (customerOptional.isPresent() && !customerOptional.get().isEmpty()) {
            return convertToCustomer(customerOptional.get().get(0));
        }
        return null;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        CustomerMongo customerMongo = convertToCustomerMongo(customer);
        CustomerMongo saved = customerRepository.save(customerMongo);
        return convertToCustomer(saved);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        CustomerMongo customerMongo = convertToCustomerMongo(customer);
        
        // Check if the customer exists
        Optional<CustomerMongo> existingCustomer = customerRepository.findById(customerMongo.getUuid());
        if (existingCustomer.isPresent()) {
            // Preserve existing relationships if needed
            // For MongoDB, we might need different handling compared to JPA
            CustomerMongo updated = customerRepository.save(customerMongo);
            return convertToCustomer(updated);
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<CustomerMongo> customers = customerRepository.findAll();
        
        return customers.stream()
                .map(this::convertToCustomer)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID uuid) {
        if (customerRepository.existsById(uuid)) {
            customerRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteByEmail(String email) {
        Optional<List<CustomerMongo>> customerOptional = customerRepository.findByEmail(email);
        
        if (customerOptional.isPresent() && !customerOptional.get().isEmpty()) {
            CustomerMongo customer = customerOptional.get().get(0);
            customerRepository.delete(customer);
            return true;
        }
        return false;
    }
    
    /**
     * Convert from domain Customer to MongoDB Customer entity
     */
    private CustomerMongo convertToCustomerMongo(Customer customer) {
        return CustomerMongo.builder()
                .uuid(customer.getUuid())
                .email(customer.getEmail())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .orders(customer.getOrders())
                .build();
    }
    
    /**
     * Convert from MongoDB Customer entity to domain Customer
     */
    private Customer convertToCustomer(CustomerMongo mongoCustomer) {
        // return Customer.builder()
        //         .uuid(mongoCustomer.getUuid())
        //         .email(mongoCustomer.getEmail())
        //         .name(mongoCustomer.getName())
        //         .lastName(mongoCustomer.getLastName())
        //         .orders(mongoCustomer.getOrders())
        //         .build();
        return null;

    }
}
