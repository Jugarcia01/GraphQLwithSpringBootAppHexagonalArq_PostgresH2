package com.jgo.demo_graphql.domain.repository.relational;

import com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb.CustomerR2db;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryJpa extends JpaRepository<CustomerR2db, UUID> {
// public interface CustomerRepositoryJpa extends JpaRepository<Customer, UUID> {

  Optional<List<CustomerR2db>> findByEmail(String email);

  @Query("SELECT DISTINCT c FROM CustomerR2db c LEFT JOIN FETCH c.orders")
  Optional<List<CustomerR2db>> findAllWithOrders();

}
