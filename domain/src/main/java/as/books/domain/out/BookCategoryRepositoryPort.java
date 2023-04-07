package as.books.domain.out;

import as.books.domain.model.BookCategory;
import as.books.domain.request.BookCategorySearchRequest;

import java.util.UUID;

public interface BookCategoryRepositoryPort extends DomainObjectRepositoryPort<UUID, BookCategory, BookCategorySearchRequest> {
}
