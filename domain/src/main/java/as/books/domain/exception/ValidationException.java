package as.books.domain.exception;

public abstract class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
