package com.jgo.demo_graphql.infrastructure.configuration;

import com.jgo.demo_graphql.application.dto.CustomerDto;
import com.jgo.demo_graphql.application.dto.OrderDto;
import com.jgo.demo_graphql.application.dto.SaleDetailsDto;
import com.jgo.demo_graphql.domain.model.Customer;
import com.mongodb.DuplicateKeyException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@Slf4j
//-- enable / uncomment the annotation for MongoDB to insert initial data:
// @Component
public class InsertDataMongo {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public InsertDataMongo(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @PostConstruct
  public void insertData() {

    log.info("Executing insertData");
    CustomerDto customer1 = CustomerDto.builder()
        .uuid(UUID.fromString("729c5d13-261a-4b46-bb11-9af04cb2cd3e"))
        .email("bbunny@mail.com")
        .name("Bugs")
        .lastName("Bunny")

        .build();

    OrderDto order1 = OrderDto.builder()
        .id(1L)
        .name("Order01")
        .description("This is the first order.")
        .customer(customer1)
        .totalOrder(BigDecimal.valueOf(100.0))
        .build();

    SaleDetailsDto saleDetails1A = SaleDetailsDto.builder()
        .id(1L)
        .sku("0001")
        .quantity(2L)
        .unitPrice(BigDecimal.valueOf(50.0))
        .totalPrice(BigDecimal.valueOf(100.0))
        .discountAmount(BigDecimal.ZERO)
        .order_id(order1.getId())
        .build();

    SaleDetailsDto saleDetails1B = SaleDetailsDto.builder()
        .id(2L)
        .sku("0002")
        .quantity(1L)
        .unitPrice(BigDecimal.valueOf(20.0))
        .totalPrice(BigDecimal.valueOf(20.0))
        .discountAmount(BigDecimal.ZERO)
        .order_id(order1.getId())
        .build();

    order1.setSaleDetails(List.of(saleDetails1A, saleDetails1B));

    OrderDto order2 = OrderDto.builder()
        .id(1L)
        .name("Order02")
        .description("This is the second order.")
        .customer(customer1)
        .totalOrder(BigDecimal.valueOf(100.0))
        .build();

    SaleDetailsDto saleDetails2A = SaleDetailsDto.builder()
        .id(3L)
        .sku("0003")
        .quantity(2L)
        .unitPrice(BigDecimal.valueOf(30.0))
        .totalPrice(BigDecimal.valueOf(60.0))
        .discountAmount(BigDecimal.ZERO)
        .order_id(order2.getId())
        .build();

    SaleDetailsDto saleDetails2B = SaleDetailsDto.builder()
        .id(4L)
        .sku("0004")
        .quantity(3L)
        .unitPrice(BigDecimal.valueOf(10.0))
        .totalPrice(BigDecimal.valueOf(30.0))
        .discountAmount(BigDecimal.ZERO)
        .order_id(order2.getId())
        .build();

    order2.setSaleDetails(List.of(saleDetails2A, saleDetails2B));

    customer1.setOrders(List.of(order1, order2));

    /*
    log.info("Executing insertCustomers");
    Customer[] customers = {
        new Customer(UUID.fromString("729c5d13-261a-4b46-bb11-9af04cb2cd3e"), "bbunny@mail.com", "Bugs", "Bunny"),
        new Customer(UUID.fromString("729c5d13-261a-4b46-bb11-9af04cb2cd3f"), "lbunny@mail.com", "Lola", "Bunny"),
        new Customer(UUID.fromString("1adfa52b-a57c-3b40-8da4-388526f6595b"), "plucas@mail.com", "Pato", "Lucas"),
        new Customer(UUID.fromString("1adfa52b-a57c-3b40-8da4-388526f6595c"), "sgonzales@mail.com", "Speedy", "Gonzales"),
        new Customer(UUID.fromString("729c5d13-261a-4b46-bb11-9af04cb2cd3a"), "plepew@mail.com", "Pepe", "Le Pew"),
        new Customer(UUID.fromString("1adfa52b-a57c-3b40-8da4-388526f6595d"), "ddtasmania@mail.com", "Demonio", "De Tasmania"),
        new Customer(UUID.fromString("7b2e3405-5b5f-4d72-9a36-96da47e7b273"), "john.doe@example.com", "John", "Doe"),
        new Customer(UUID.fromString("14e46437-1bdd-4a8e-ac81-085d286e7aba"), "jgo@mail.com", "Julian", "Garcia O")
    };

    if (isCollectionEmpty("customer")) {
      // Insert the customers into the MongoDB database
      // insert with insertAll
      // mongoTemplate.insertAll(Arrays.asList(customers));

      // insert with foreach loop, allow to manage each record.
      for (Customer customer : customers) {
        try {
          mongoTemplate.insert(customer, "customer");
        } catch (DuplicateKeyException e) {
          log.error("Failed to insert customer {}: {}", customer.getUuid(), e.getMessage());
        }
      }
    }
    */

  }

  public boolean isCollectionEmpty(String collectionName) {
    long count = mongoTemplate.count(new Query(), collectionName);
    log.info("Documents in {}: {}", collectionName, count);
    return count == 0;
  }

}
