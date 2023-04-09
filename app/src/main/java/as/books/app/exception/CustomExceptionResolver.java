package as.books.app.exception;

import as.books.domain.exception.DomainObjectNotFoundException;
import as.books.domain.exception.ValidationException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof DomainObjectNotFoundException) {
            return toGraphQLError(ex, env, ErrorType.NOT_FOUND, "Domain object not found. ");
        }if (ex instanceof ValidationException) {
            return toGraphQLError(ex, env, ErrorType.BAD_REQUEST, "Validation exception: ");
        } else {
            return null;
        }
    }

    private static GraphQLError toGraphQLError(final Throwable ex,
                                               final DataFetchingEnvironment env,
                                               final ErrorType errorType,
                                               final String messagePrefix) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(messagePrefix + ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}