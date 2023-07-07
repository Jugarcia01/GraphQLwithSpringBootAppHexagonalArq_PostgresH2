package com.jgo.demo_graphql.infrastructure.inputport;

import com.jgo.demo_graphql.application.dto.SaleDetailsDto;
import com.jgo.demo_graphql.domain.model.SaleDetails;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface SaleDetailsInputPort {

  public ResponseEntity<SaleDetails> createSaleDetails(SaleDetailsDto saleDetailsDto);

  public ResponseEntity<SaleDetails> getSaleDetailsById(Long id);

  public ResponseEntity<List<SaleDetails>> getSaleDetailsByOrderId(Long orderId);

  public ResponseEntity<List<SaleDetails>> getAllSaleDetails();

  public Boolean deleteSaleDetailsById(Long id);
}
