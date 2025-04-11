package com.jgo.demo_graphql.application.usecase;

import com.jgo.demo_graphql.application.dto.SaleDetailsDto;
import com.jgo.demo_graphql.domain.model.Order;
import com.jgo.demo_graphql.domain.model.SaleDetails;
import com.jgo.demo_graphql.domain.repository.relational.SaleDetailsRepositoryJpa;
import com.jgo.demo_graphql.infrastructure.inputport.SaleDetailsInputPort;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import com.jgo.demo_graphql.util.MapperUtil;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class SaleDetailsUseCase implements SaleDetailsInputPort {

  private EntityRepository entityRepository;

  private SaleDetailsRepositoryJpa saleDetailsRepositoryJpa;

  public SaleDetailsUseCase(EntityRepository entityRepository,
                            SaleDetailsRepositoryJpa saleDetailsRepositoryJpa) {
    this.entityRepository = entityRepository;
    this.saleDetailsRepositoryJpa = saleDetailsRepositoryJpa;
  }

  @Override
  public ResponseEntity<SaleDetails> createSaleDetails(SaleDetailsDto saleDetailsDto) {
    try {
      MapperUtil mapperUtil = new MapperUtil(new ModelMapper());
      SaleDetails saleDetails = mapperUtil.map(saleDetailsDto, SaleDetails.class);
      SaleDetails foundSaleDetails = getSaleDetailsById(saleDetails.getId()).getBody();
      Order foundOrder = entityRepository.getEntityById(saleDetailsDto.getOrder_id(), Order.class);

      if (isNull(foundSaleDetails)) {
        SaleDetails newSaleDetails = SaleDetails.builder()
            .id(saleDetails.getId())
            .sku(saleDetails.getSku())
            .quantity(saleDetails.getQuantity())
            .unitPrice(saleDetails.getUnitPrice())
            .discountAmount(saleDetails.getDiscountAmount())
            .totalPrice(saleDetails.getTotalPrice())
            .order(foundOrder)
            .build();
        entityRepository.createEntity(newSaleDetails);
        log.info("New saleDetails!.");
        return new ResponseEntity<>(newSaleDetails, HttpStatus.CREATED);
      } else {
        SaleDetails updateSaleDetails = SaleDetails.builder()
            .id(foundSaleDetails.getId())
            .sku(saleDetails.getSku())
            .quantity(saleDetails.getQuantity())
            .unitPrice(saleDetails.getUnitPrice())
            .discountAmount(saleDetails.getDiscountAmount())
            .totalPrice(saleDetails.getTotalPrice())
            .order(foundOrder)
            .build();
        entityRepository.updateEntity(updateSaleDetails);
        log.info("Update saleDetails.");
        return new ResponseEntity<>(updateSaleDetails, HttpStatus.OK);
      }

    } catch (Exception e) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("ErrorMessage", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .headers(headers)
          .body(null);
    }
  }

  @Override
  public ResponseEntity<SaleDetails> getSaleDetailsById(Long id) {
    try {
      SaleDetails foundSaleDetails = entityRepository.getEntityById(id, SaleDetails.class);
      if (isNull(foundSaleDetails)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(foundSaleDetails, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<SaleDetails>> getSaleDetailsByOrderId(Long orderId) {
    try {
      Optional<List<SaleDetails>> foundSaleDetails = saleDetailsRepositoryJpa.findByOrderId(orderId);
      if(!foundSaleDetails.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
        return new ResponseEntity<>(foundSaleDetails.get(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<SaleDetails>> getAllSaleDetails() {
    try {
      List<SaleDetails> foundSaleDetails = entityRepository.getAllEntities(SaleDetails.class);
      if (foundSaleDetails.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(foundSaleDetails, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public Boolean deleteSaleDetailsById(Long id) {
    try {
      entityRepository.deleteEntityById(id, SaleDetails.class);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
