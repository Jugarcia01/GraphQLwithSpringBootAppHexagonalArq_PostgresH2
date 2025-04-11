package com.jgo.demo_graphql.infrastructure.mapper;

import com.jgo.demo_graphql.application.dto.CustomerDto;
import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.infrastructure.outputadapter.mongodb.CustomerMongo;
import com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb.CustomerR2db;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "orders", target = "orders")
    Customer toCustomer(CustomerR2db customerR2db);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "orders", target = "orders")
    CustomerR2db toCustomerPostgres(Customer customer);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "orders", target = "orders")
    CustomerMongo toCustomerMongo(Customer customer);


    Customer toEntity(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);
}
