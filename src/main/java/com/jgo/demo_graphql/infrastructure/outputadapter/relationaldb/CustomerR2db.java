package com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.domain.model.Order;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class CustomerR2db {

    @Id
    @JsonProperty("uuid")
    @GraphQLQuery(name = "uuid")
    @Column(columnDefinition = "VARCHAR(36)", name = "uuid", nullable = false, updatable = false, unique = true)
    // Activar @Type cuando BD es H2 (Para que no queden los UUID cifrados); Desactivar @Type cuando BD es Postgres
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID uuid;

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
    @Column(name = "lastname") // Note the column name is in lower case.
    private String lastName;

    @JsonIgnoreProperties({"orders", "customer"})
    @GraphQLQuery(name = "orders")
    // One customer can have multiple orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Order> orders;

}
