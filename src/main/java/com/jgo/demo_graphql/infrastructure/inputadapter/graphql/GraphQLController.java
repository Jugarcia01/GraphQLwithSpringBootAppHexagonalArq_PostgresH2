package com.jgo.demo_graphql.infrastructure.inputadapter.graphql;

import com.jgo.demo_graphql.infrastructure.configuration.GraphQLConfiguration;
import com.jgo.demo_graphql.infrastructure.configuration.CustomGraphQLResponseProcessor;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/*
  Controller to generate the GraphQL schema with SPQR witch takes the
  code-first approach, by generating the schema from the existing model in Java.
  GraphQL SPQR (GraphQL Schema Publisher & Query Resolver)
 */

@Slf4j
@RestController
public class GraphQLController {

  private final GraphQL graphQL;
  private CustomerSpqrQuery customerSpqrQuery;
  private OrderSpqrQuery orderSpqrQuery;
  private SaleDetailsSpqrQuery saleDetailsSpqrQuery;
  private final CustomGraphQLResponseProcessor responseProcessor;

  @Autowired
  public GraphQLController(GraphQLConfiguration graphQLConfiguration,
                           CustomerSpqrQuery customerSpqrQuery,
                           OrderSpqrQuery orderSpqrQuery,
                           SaleDetailsSpqrQuery saleDetailsSpqrQuery,
                           CustomGraphQLResponseProcessor responseProcessor
  ) {
    this.customerSpqrQuery = customerSpqrQuery;
    this.orderSpqrQuery = orderSpqrQuery;
    this.saleDetailsSpqrQuery = saleDetailsSpqrQuery;
    this.responseProcessor = responseProcessor;

    graphQL = graphQLConfiguration.graphQLSchemaConfiguration(entitiesQueryArray());
    log.info("Generated GraphQL schema using SPQR");
  }

  Object[] entitiesQueryArray () {
    List<Object> listQuery = new ArrayList<>();
    // Add the necessary entities to include in the schema
    listQuery.add(customerSpqrQuery);
    listQuery.add(orderSpqrQuery);
    listQuery.add(saleDetailsSpqrQuery);

    return listQuery.toArray();
  }

  @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> indexFromAnnotated(@RequestBody Map<String, String> request, HttpServletRequest raw) {

    ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
        .query(request.get("query"))
        .operationName(request.get("operationName"))
        .context(raw)
        .build());

    return responseProcessor.processResponse(executionResult);
  }
}
