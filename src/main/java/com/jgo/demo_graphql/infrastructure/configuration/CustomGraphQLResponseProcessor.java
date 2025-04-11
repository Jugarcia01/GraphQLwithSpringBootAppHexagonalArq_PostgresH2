package com.jgo.demo_graphql.infrastructure.configuration;

import graphql.ExecutionResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Custom processor for GraphQL responses
 * This class handles formatting of GraphQL responses to meet specific requirements:
 * - Removes the 'data' field when all its values are null
 */
@Component
public class CustomGraphQLResponseProcessor {

    /**
     * Process the GraphQL execution result to format it according to our requirements
     * @param executionResult The original GraphQL execution result
     * @return A properly formatted response map
     */
    public Map<String, Object> processResponse(ExecutionResult executionResult) {
        // Get the standard GraphQL response
        Map<String, Object> result = executionResult.toSpecification();
        
        // Check if there are errors and if data is null
        if (result.containsKey("errors") && result.containsKey("data")) {
            Object dataValue = result.get("data");

            // If data is null or all values in the data map are null, remove the data field
            if (dataValue == null) {
                result.remove("data");
            } else if (dataValue instanceof Map) {
                Map<?, ?> dataMap = (Map<?, ?>) dataValue;
                boolean allValuesNull = true;

                for (Object value : dataMap.values()) {
                    if (value != null) {
                        allValuesNull = false;
                        break;
                    }
                }

                if (allValuesNull) {
                    result.remove("data");
                }
            }
        }

        return result;
    }
}
