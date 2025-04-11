package com.jgo.demo_graphql.infrastructure.configuration;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.type.UUIDCharType;
import org.springframework.context.annotation.Configuration;

import java.sql.Types;

@Configuration
public class H2CustomDialect extends H2Dialect {
    public H2CustomDialect() {
        super();
        registerHibernateType(
                Types.VARCHAR, UUIDCharType.INSTANCE.getName());
    }
}