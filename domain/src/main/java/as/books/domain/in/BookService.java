package as.books.domain.in;

import as.books.domain.model.Book;
import as.books.domain.request.BookAddRequest;
import as.books.domain.request.BookSearchRequest;

public interface BookService  extends DomainObjectService<String, Book, BookSearchRequest>{
    Book add(BookAddRequest request);
    void delete(String id);
}
