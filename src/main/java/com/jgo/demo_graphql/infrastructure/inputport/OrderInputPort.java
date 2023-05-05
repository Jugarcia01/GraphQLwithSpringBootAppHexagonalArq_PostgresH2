package com.jgo.demo_graphql.infrastructure.inputport;

import com.jgo.demo_graphql.application.dto.OrderDto;
import com.jgo.demo_graphql.domain.model.Order;
import java.util.List;

public interface OrderInputPort {

  public Order createOrder(OrderDto order, Boolean isUpdate);

  public Order getOrderById(Long id);

  public Order deleteOrderById(Long id);

  public List<Order> getByCustomerId(Long customerId);

  public List<Order> getAllOrders();

}
