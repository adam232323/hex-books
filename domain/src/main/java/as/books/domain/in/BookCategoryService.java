package as.books.domain.in;

import as.books.domain.model.BookCategory;
import as.books.domain.request.BookCategorySearchRequest;

import java.util.List;
import java.util.UUID;

public interface BookCategoryService extends DomainObjectService<UUID, BookCategory, BookCategorySearchRequest>{
    List<BookCategory> getBy(BookCategorySearchRequest searchRequest);
}
