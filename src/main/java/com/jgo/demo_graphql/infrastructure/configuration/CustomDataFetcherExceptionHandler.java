package com.jgo.demo_graphql.infrastructure.configuration;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.language.SourceLocation;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Custom exception handler for GraphQL data fetchers
 * This handler ensures that GraphQL errors are properly formatted according to our requirements
 */
@Component
public class CustomDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    public DataFetcherExceptionHandlerResult onException(
            DataFetcherExceptionHandlerParameters dataFetcherExceptionHandlerParameters) {
        Throwable exception = dataFetcherExceptionHandlerParameters.getException();

        // If the exception is already a GraphQLError (like our CustomerExistsException),
        // we'll use it directly without wrapping
        if (exception instanceof GraphQLError graphQLError) {
            // Our custom GraphQL error is already properly formatted
            return DataFetcherExceptionHandlerResult.newResult()
                    .error(graphQLError)
                    .build();
        }

        // Instead of delegating to default handler, create a custom GraphQL error
        GraphQLError customError = new GraphQLError() {
            @Override
            public String getMessage() {
                return exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred";
            }

            @Override
            public List<Object> getPath() {
                return exception instanceof GraphQLError ? ((GraphQLError) exception).getPath() : null;
            }

            @Override
            public List<SourceLocation> getLocations() {
                return null;
            }

            @Override
            public ErrorClassification getErrorType() {
                return ErrorType.DataFetchingException;
            }

            @Override
            public Map<String, Object> getExtensions() {
                return Collections.emptyMap();
            }
        };

        DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
                .error(customError)
                .build();
        return result;
    }

}
