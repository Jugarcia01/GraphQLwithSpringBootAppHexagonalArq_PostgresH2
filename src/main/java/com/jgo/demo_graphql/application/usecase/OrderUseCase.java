package com.jgo.demo_graphql.application.usecase;

import com.jgo.demo_graphql.application.dto.OrderDto;
import com.jgo.demo_graphql.domain.model.Order;
import com.jgo.demo_graphql.infrastructure.inputport.OrderInputPort;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class OrderUseCase implements OrderInputPort {

  // Before enable, Check in Order.java have the Mongo anotations actived: @Document, @Id
  // private static final String REPOSITORY = "mongodbRepository";

  // WARNING: If happen this Error: No property getOne found for type Order!
  // Check in Order.java have the Postgres anotations actived: @Entity, @javax.persistence.Id
  // Enable for H2 and Postgres
  private static final String REPOSITORY = "postgresRepository";

  private EntityRepository entityRepository;

  @Autowired
  public OrderUseCase(@Qualifier(REPOSITORY) EntityRepository entityRepository) {
    this.entityRepository = entityRepository;
  }

  @Override
  public ResponseEntity<Order> createOrder(OrderDto orderDto) {
    return null;
  }

  @Override
  public ResponseEntity<Order> getOrderById(Long id) {
    try {
      Order foundOrder = entityRepository.getEntityById(id, Order.class);
      if (isNull(foundOrder)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(foundOrder, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public Boolean deleteOrderById(Long id) {
    try {
      entityRepository.deleteEntityById(id, Order.class);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public ResponseEntity<List<Order>> getAllOrders() {
    try {
      List<Order> foundOrders = entityRepository.getAllEntities(Order.class);
      if (foundOrders.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(foundOrders, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<Order>> getOrderByCustomerId(UUID customerUuid) {
    return null;
  }

}
