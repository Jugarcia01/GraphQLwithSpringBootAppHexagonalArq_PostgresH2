package com.jgo.demo_graphql.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer implements Serializable, BaseEntity {
    UUID uuid;
    String email;
    String name;
    String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Order> orders;

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

}
