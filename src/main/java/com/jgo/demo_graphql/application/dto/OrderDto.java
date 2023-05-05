package com.jgo.demo_graphql.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

  private Long id;
  private String name;
  private String description;
  private CustomerDto customer;
  private List<SaleDetailsDto> saleDetails;
  private BigDecimal totalOrder;

}
