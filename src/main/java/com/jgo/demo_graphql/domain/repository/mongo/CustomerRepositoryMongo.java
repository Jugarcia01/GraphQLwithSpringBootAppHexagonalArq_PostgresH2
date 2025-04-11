package com.jgo.demo_graphql.domain.repository.mongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jgo.demo_graphql.domain.model.Customer;
// import com.jgo.demo_graphql.infrastructure.outputadapter.mongodb.CustomerMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepositoryMongo extends MongoRepository<Customer, UUID> {
// public interface CustomerRepositoryMongo extends MongoRepository<CustomerMongo, UUID> {
//public interface CustomerRepository extends MongoRepository<Customer, Long> {


  Optional<List<Customer>> findByEmail(String email);
  // Optional<List<CustomerMongo>> findByEmail(String email);

  // @Autowired
  // private MongoTemplate mongoTemplate;
  // default Optional<List<CustomerMongo>> findAllWithOrders() {
  //     List<CustomerMongo> customers = mongoTemplate.find(
  //         new Query().addCriteria(Criteria.where("orders").exists(true)),
  //         CustomerMongo.class
  //     );
  //     return Optional.ofNullable(customers.isEmpty() ? null : customers);
  // }

  // Optional<List<Customer>> findAllWithOrders();
  // Optional<List<CustomerMongo>> findAllWithOrders();

}
