package com.jgo.demo_graphql.infrastructure.inputport;

import com.jgo.demo_graphql.application.dto.OrderDto;
import com.jgo.demo_graphql.domain.model.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface OrderInputPort {

  public ResponseEntity<Order> createOrder(OrderDto orderDto);

  public ResponseEntity<Order> getOrderById(Long id);

  public ResponseEntity<List<Order>> getOrderByCustomerId(UUID customerUuid);

  public ResponseEntity<List<Order>> getAllOrders();

  public Boolean deleteOrderById(Long id);

}
