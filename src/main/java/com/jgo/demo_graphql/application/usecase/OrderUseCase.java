package com.jgo.demo_graphql.application.usecase;

import com.jgo.demo_graphql.application.dto.OrderDto;
import com.jgo.demo_graphql.domain.model.Order;
import com.jgo.demo_graphql.infrastructure.inputport.OrderInputPort;
import java.util.List;

public class OrderUseCase implements OrderInputPort {

  @Override
  public Order createOrder(OrderDto order, Boolean isUpdate) {
    return null;
  }

  @Override
  public Order getOrderById(Long id) {
    return null;
  }

  @Override
  public Order deleteOrderById(Long id) {
    return null;
  }

  @Override
  public List<Order> getByCustomerId(Long customerId) {
    return null;
  }

  @Override
  public List<Order> getAllOrders() {
    return null;
  }
}
