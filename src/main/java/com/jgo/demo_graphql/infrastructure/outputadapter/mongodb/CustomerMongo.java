package com.jgo.demo_graphql.infrastructure.outputadapter.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgo.demo_graphql.domain.model.Order;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class CustomerMongo {

    //-- Enable when use MongoDB
    @Id
    @JsonProperty("uuid")
    @GraphQLQuery(name = "uuid")
    private UUID uuid;
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
    @JsonIgnoreProperties({"orders", "customer"})
    @GraphQLQuery(name = "orders")
    // One customer can have multiple orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
