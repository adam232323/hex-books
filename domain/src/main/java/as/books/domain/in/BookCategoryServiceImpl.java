package as.books.domain.in;

import as.books.domain.model.BookCategory;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.BookCategorySearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class BookCategoryServiceImpl extends AbstractDomainObjectService<UUID, BookCategory, BookCategorySearchRequest> implements
        BookCategoryService {

    private final BookCategoryRepositoryPort categoryRepositoryPort;

    @Override
    DomainObjectRepositoryPort<UUID, BookCategory, BookCategorySearchRequest> repositoryPort() {
        return categoryRepositoryPort;
    }
}
