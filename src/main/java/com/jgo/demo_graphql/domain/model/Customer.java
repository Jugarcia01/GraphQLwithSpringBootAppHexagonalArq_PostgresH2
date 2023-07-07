package com.jgo.demo_graphql.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.application.dto.OrderDto;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// //-- Enable when use Postgres / H2 DBs
@Entity
@Table(name = "CUSTOMER")
//-- Enable when use MongoDB
// @Document(collection = "customer")
public class Customer implements Serializable {

    //-- Enable when use Postgres and H2 DB
    @javax.persistence.Id
    @JsonProperty("uuid")
    @GraphQLQuery(name = "uuid")
    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    //--  Only enable when use H2, not compatible with Postgres
    // @GeneratedValue(strategy = GenerationType.AUTO) // No requerida porque generamos el UUID al crear un Customer.
    @Type(type="org.hibernate.type.UUIDCharType") // Necesaria para que no queden los UUID cifrados en la BD H2.
    private UUID uuid;

    //-- Enable when use MongoDB
    // @Id
    // @JsonProperty("uuid")
    // @GraphQLQuery(name = "uuid")
    // private UUID uuid;
    //--

    @JsonProperty("email")
    @GraphQLQuery(name = "email")
    @Column(name = "email")
    private String email;

    @JsonProperty("name")
    @GraphQLQuery(name = "name")
    @Column(name = "name")
    private String name;

    @JsonProperty("lastName")
    @GraphQLQuery(name = "lastName")
    @Column(name = "lastname") // WARNING: Note the column name is in lower case.
    private String lastName;

    @JsonProperty("orders")
    @GraphQLQuery(name = "orders")
    // One customer can have multiple orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    // Maybe we need to include this methods: equals, hashCode, toString

}
