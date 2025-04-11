package com.jgo.demo_graphql.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SALEDETAILS")
public class SaleDetails implements Serializable {

    @javax.persistence.Id
    @JsonProperty("id")
    @GraphQLQuery(name = "id")
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("sku")
    @GraphQLQuery(name = "sku")
    @Column(name = "sku")
    private String sku;

    @JsonProperty("quantity")
    @GraphQLQuery(name = "quantity")
    @Column(name = "quantity")
    private Long quantity;

    @JsonProperty("unitPrice")
    @GraphQLQuery(name = "unitPrice")
    @Column(name = "unitprice")
    private BigDecimal unitPrice;

    @JsonProperty("totalPrice")
    @GraphQLQuery(name = "totalPrice")
    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @JsonProperty("discountAmount")
    @GraphQLQuery(name = "discountAmount")
    @Column(name = "discountamount")
    private BigDecimal discountAmount;

    // Many sale details can be associated with one order
    @JsonIgnoreProperties({"order", "saleDetails"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @GraphQLQuery(name = "order")
    private Order order;
}
