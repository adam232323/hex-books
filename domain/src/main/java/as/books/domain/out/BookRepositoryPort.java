package as.books.domain.out;

import as.books.domain.model.Book;
import as.books.domain.request.BookSearchRequest;

public interface BookRepositoryPort extends DomainObjectRepositoryPort<String, Book, BookSearchRequest> {
}
