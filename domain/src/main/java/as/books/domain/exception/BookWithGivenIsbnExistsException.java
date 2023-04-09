package as.books.domain.exception;

public class BookWithGivenIsbnExistsException extends ValidationException{

    public BookWithGivenIsbnExistsException(final String message) {
        super(message);
    }
}
