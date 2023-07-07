package com.jgo.demo_graphql.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.domain.model.Order;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailsDto {

  private Long id;
  private String sku;
  private Long quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private BigDecimal discountAmount;
  private Long order_id;
  //private OrderDto order;


}
