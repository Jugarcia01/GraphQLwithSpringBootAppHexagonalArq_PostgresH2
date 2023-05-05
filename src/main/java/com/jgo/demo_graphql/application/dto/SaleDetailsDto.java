package com.jgo.demo_graphql.application.dto;

import java.math.BigDecimal;
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

}
