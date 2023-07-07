package com.jgo.demo_graphql.infrastructure.inputadapter.graphql;

import com.jgo.demo_graphql.domain.model.Order;
import com.jgo.demo_graphql.infrastructure.inputport.OrderInputPort;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderSpqrQuery {
  /* endpoint of project:
   http://localhost:8080/graphiql

    - Example getAllOrders request:
    query {
        orders {
              id
              name
              customer {
                email
                name
                lastName
                uuid
              }
              saleDetails {
                id
                sku
                quantity
                discountAmount
                unitPrice
                totalPrice
              }
              description
              totalOrder
           }
    }

    - Example getOrderById request:
    query {
        order (id:1) {
            id
            name
            description
            saleDetails {
              id
              sku
              quantity
              totalPrice
            }
        }
    }

    - Example delete request; before to use MODIFY AND CHECK THAT ID EXIST ON YOUR DATABASE!:
    mutation {
      deleteOrder(id: 1)
    }


   */

  private OrderInputPort orderInputPort;

  public OrderSpqrQuery(OrderInputPort orderInputPort) {
    this.orderInputPort = orderInputPort;
  }

  /**
   * Get a Order based on id given.
   * @param id
   * @return the order data.
   */
  @GraphQLQuery(name = "order", description = "Get a Order based on id given")
  public Order getOrder(@GraphQLArgument(name = "id", description = "id of order to find") final Long id) {
    return orderInputPort.getOrderById(id).getBody();
  }

  /**
   * Get all orders
   * @return list of orders.
   */
  @GraphQLQuery(name = "orders", description = "Get All Orders")
  public List<Order> getAllOrders() {
    log.info("getAllOrders in process...");
    return orderInputPort.getAllOrders().getBody();
  }

  /**
   * Deletes the order given its id
   * @param id
   * @return String message of result
   */
  @GraphQLMutation(name = "deleteOrder", description = "Deletes the order given its id")
  public String deleteOrder(@GraphQLNonNull Long id) {
    log.info("deleteOrderById in process...");
    Boolean result = orderInputPort.deleteOrderById(id);
    if (Boolean.TRUE.equals(result)) {
      return "Order with id: " + id + " was successfully deleted!";
    } else {
      return "Error occurred when trying to deleted Order with id:" + id + " or record don't exist.";
    }
  }

}
