package com.jgo.demo_graphql.domain.repository.relational;

import com.jgo.demo_graphql.domain.model.SaleDetails;
import java.util.List;
import java.util.Optional;
import javax.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailsRepositoryJpa extends JpaRepository<SaleDetails, Id> {

  Optional<List<SaleDetails>> findByOrderId(Long id);

}
