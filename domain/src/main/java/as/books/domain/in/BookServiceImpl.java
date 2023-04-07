package as.books.domain.in;

import as.books.domain.exception.DomainObjectNotFoundException;
import as.books.domain.model.Book;
import as.books.domain.model.BookCategory;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.out.BookRepositoryPort;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.BookAddRequest;
import as.books.domain.request.BookSearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class BookServiceImpl extends AbstractDomainObjectService<String, Book, BookSearchRequest> implements BookService{

    private final BookRepositoryPort bookRepositoryPort;
    private final AuthorRepositoryPort authorRepositoryPort;
    private final BookCategoryRepositoryPort bookCategoryRepositoryPort;

    @Override
    public Book add(final BookAddRequest request) {
        validateRequest(request);

        final Book book = new Book(request.isbn(),
                                   request.title(),
                                   request.description(),
                                   request.authorId(),
                                   request.categoryIds());
        bookRepositoryPort.save(book);

        return book;
    }

    private void validateRequest(final BookAddRequest request) {
        if(authorRepositoryPort.getById(request.authorId()).isEmpty()){
            throw new DomainObjectNotFoundException("Author: " + request.authorId());
        }
        final Set<UUID> foundIds = bookCategoryRepositoryPort.getByIds(request.categoryIds())
                .stream()
                .map(BookCategory::uuid)
                .collect(Collectors.toSet());

        if(foundIds.size() < request.categoryIds().size()){
            final Set<UUID> notMatched = request.categoryIds()
                    .stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new DomainObjectNotFoundException("BookCategories: " + notMatched);
        }
    }

    @Override
    public void delete(final String id) {
        bookRepositoryPort.delete(id);
    }

    @Override
    DomainObjectRepositoryPort<String, Book, BookSearchRequest> repositoryPort() {
        return bookRepositoryPort;
    }
}
