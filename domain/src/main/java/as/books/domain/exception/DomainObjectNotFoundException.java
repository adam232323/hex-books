package as.books.domain.exception;

public class DomainObjectNotFoundException extends RuntimeException{

    public DomainObjectNotFoundException(final String message) {
        super(message);
    }
}
