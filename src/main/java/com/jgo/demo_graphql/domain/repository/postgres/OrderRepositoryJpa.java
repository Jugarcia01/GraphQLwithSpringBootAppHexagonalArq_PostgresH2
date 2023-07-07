package com.jgo.demo_graphql.domain.repository.postgres;

import com.jgo.demo_graphql.domain.model.Order;
import javax.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryJpa extends JpaRepository<Order, Id> {

}
