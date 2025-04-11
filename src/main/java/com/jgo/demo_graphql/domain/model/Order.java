package com.jgo.demo_graphql.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb.CustomerR2db;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

  //-- Enable when use Postgres and H2 DB
  @javax.persistence.Id
  @JsonProperty("id")
  @GraphQLQuery(name = "id")
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonProperty("nombre")
  private String name;

  @JsonProperty("description")
  @GraphQLQuery(name = "description")
  @Column(name = "description")
  private String description;

  @JsonProperty("customer")
  // @JsonBackReference
  // Many orders can be associated with one customer
  @ManyToOne(fetch = FetchType.LAZY)
  @GraphQLQuery(name = "customer")
  @JsonIgnoreProperties({"customer", "customer_uuid"})
  @JoinColumn(name = "customer_uuid", referencedColumnName = "uuid")
  private CustomerR2db customer;

  // @JsonProperty("saleDetails")
  @JsonIgnoreProperties({"saleDetails", "order"})
  @GraphQLQuery(name = "saleDetails")
  @Column(name = "saledetails")
  // One order can have multiple sale details
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //, fetch = FetchType.EAGER)
  private List<SaleDetails> saleDetails;

  @JsonProperty("totalOrder")
  @GraphQLQuery(name = "totalOrder")
  @Column(name = "totalorder")
  private BigDecimal totalOrder;

  public Order addCustomer(CustomerR2db customer) {
    this.customer = customer;
    return this;
  }

  public Order addSaleDetails(SaleDetails saleDetails) {
    this.saleDetails.add(saleDetails);
    return this;
  }

}
