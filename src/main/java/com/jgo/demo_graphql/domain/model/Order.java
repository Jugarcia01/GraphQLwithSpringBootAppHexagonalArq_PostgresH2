package com.jgo.demo_graphql.domain.model;

//import com.jgo.demoGraphQL.util.SelfValidating;
// import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.application.dto.CustomerDto;
import com.jgo.demo_graphql.application.dto.SaleDetailsDto;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {
//public class Order extends SelfValidating<Order> implements Serializable {

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
  @GraphQLQuery(name = "customer")
  // Many orders can be associated with one customer
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_uuid")
  private Customer customer;

  @JsonProperty("saleDetails")
  @GraphQLQuery(name = "saleDetails")
  @Column(name = "saledetails")
  // One order can have multiple sale details
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //, fetch = FetchType.EAGER)
  private List<SaleDetails> saleDetails;

  @JsonProperty("totalOrder")
  @GraphQLQuery(name = "totalOrder")
  @Column(name = "totalorder")
  private BigDecimal totalOrder;

  public Order addCustomer(Customer customer) {
    this.customer = customer;
    return this;
  }

  public Order addSaleDetails(SaleDetails saleDetails) {
    this.saleDetails.add(saleDetails);
    return this;
  }

}
