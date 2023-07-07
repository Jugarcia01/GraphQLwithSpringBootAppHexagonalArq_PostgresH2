package com.jgo.demo_graphql.infrastructure.inputadapter.graphql;

import com.jgo.demo_graphql.application.dto.SaleDetailsDto;
import com.jgo.demo_graphql.domain.model.SaleDetails;
import com.jgo.demo_graphql.infrastructure.inputport.SaleDetailsInputPort;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaleDetailsSpqrQuery {

  /*
    - Example getAllSaleDetails request:
    query {
      getAllSaleDetails{
        id
        sku
        quantity
        discountAmount
        unitPrice
        totalPrice
       order {
          id
          name
          description
          customer {
            uuid
            name
            email
          }
        }
      }
    }

  - Example getSaleDetailsByOrderId request:
    query {
        getSaleDetailsByOrderId(orderId: 1) {
          id
          sku
          quantity
          unitPrice
          totalPrice
      }
    }

    - Example getSaleDetailsById request:
    query {
        getSaleDetailsById(id:1) {
          id
          sku
          quantity
          unitPrice
          totalPrice
      }
    }

   */

  private SaleDetailsInputPort saleDetailsInputPort;

  public SaleDetailsSpqrQuery(SaleDetailsInputPort saleDetailsInputPort) {
    this.saleDetailsInputPort = saleDetailsInputPort;
  }

  /**
   * Get all sale details
   * @return list of sale details.
   */
  @GraphQLQuery(name = "getAllSaleDetails", description = "Get All SaleDetails")
  public List<SaleDetails> getAllSaleDetails() {
    log.info("getAllSaleDetails in process...");
    return saleDetailsInputPort.getAllSaleDetails().getBody();
  }

  /**
   * Get SaleDetails based on OrderId given.
   * @param orderId
   * @return sale details data.
   */
  @GraphQLQuery(name = "getSaleDetailsByOrderId", description = "Get a SaleDetails based on OrderId given")
  public List<SaleDetails> getSaleDetailsByOrderId(@GraphQLArgument(name = "orderId", description = "orderId of saleDetails to search") Long orderId) {
    return saleDetailsInputPort.getSaleDetailsByOrderId(orderId).getBody();
  }

  /**
   * Deletes the saleDetails given its id
   * @param id
   * @return String message of result
   */
  @GraphQLMutation(name = "deleteSaleDetailsById", description = "Deletes the sale details given its id")
  public String deleteSaleDetailsById(@GraphQLNonNull Long id) {
    log.info("deleteSaleDetailsById in process...");
    Boolean result = saleDetailsInputPort.deleteSaleDetailsById(id);
    if (Boolean.TRUE.equals(result)) {
      return "SaleDetails with id: " + id + " was successfully deleted!";
    } else {
      return "Error occurred when trying to deleted SaleDetails with id:" + id + " or record don't exist.";
    }
  }

  /**
   * Get a SaleDetails based on id given.
   * @param id
   * @return the sale details data.
   */
  @GraphQLQuery(name = "getSaleDetailsById", description = "Get a SaleDetails based on id given")
  public SaleDetails getSaleDetailsById(@GraphQLArgument(name = "id", description = "id of saleDetails to find") final Long id) {
    return saleDetailsInputPort.getSaleDetailsById(id).getBody();
  }

  /**
   * Create a SaleDetails based on data given.
   * @param sku
   * @param quantity
   * @param unitPrice
   * @param totalPrice
   * @param discountAmount
   * @param order
   * @return new sale details.
   */
  @GraphQLMutation(name = "createSaleDetails", description = "Create a SaleDetails based on data given")
  public SaleDetails createSaleDetails(@GraphQLNonNull String sku, Long quantity, BigDecimal unitPrice, BigDecimal discountAmount, BigDecimal totalPrice,
  Long order_id) {
    SaleDetailsDto saleDetailsDto = SaleDetailsDto.builder()
        .sku(sku)
        .quantity(quantity)
        .unitPrice(unitPrice)
        .discountAmount(discountAmount)
        .totalPrice(totalPrice)
        .order_id(order_id)
        .build();
    return saleDetailsInputPort.createSaleDetails(saleDetailsDto).getBody();
  }

}
