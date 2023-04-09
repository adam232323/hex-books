package as.books.domain.in;

import as.books.domain.exception.BookWithGivenIsbnExistsException;
import as.books.domain.exception.DomainObjectNotFoundException;
import as.books.domain.model.Author;
import as.books.domain.model.Book;
import as.books.domain.model.BookCategory;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.out.BookRepositoryPort;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.BookAddRequest;
import as.books.domain.request.BookSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
class BookServiceImpl extends AbstractDomainObjectService<String, Book, BookSearchRequest> implements BookService {

    private final BookRepositoryPort bookRepositoryPort;
    private final AuthorRepositoryPort authorRepositoryPort;
    private final BookCategoryRepositoryPort bookCategoryRepositoryPort;

    @Override
    public Book add(final BookAddRequest request) {
        validateIsbn(request.isbn());

        Author author = fetchAndValidateAuthor(request.authorId());
        List<BookCategory> categories = fetchAndValidateAuthorCategories(request.categoryIds());

        Book book = new Book(request.isbn(), request.title(), request.description(), author, categories);
        bookRepositoryPort.save(book);

        log.info("Book created: {}", book);
        return book;
    }

    private void validateIsbn(String isbn) {
        if(bookRepositoryPort.getById(isbn).isPresent()){
            throw new BookWithGivenIsbnExistsException("Book with given isbn exists: " + isbn);
        }
    }

    private Author fetchAndValidateAuthor(final UUID authorId) {
        return authorRepositoryPort.getById(authorId)
                .orElseThrow(() -> new DomainObjectNotFoundException("Author: " + authorId));
    }

    private List<BookCategory> fetchAndValidateAuthorCategories(final @NotNull Set<UUID> categoryIds) {

        final List<BookCategory> categories = bookCategoryRepositoryPort.getByIds(categoryIds);

        final Set<UUID> foundIds = categories.stream().map(BookCategory::id).collect(Collectors.toSet());

        if (foundIds.size() < categoryIds.size()) {
            final Set<UUID> notMatched = categoryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new DomainObjectNotFoundException("BookCategories: " + notMatched);
        }

        return categories;
    }

    @Override
    public void delete(final String id) {
        bookRepositoryPort.delete(id);

        log.info("Book removed: {}", id);
    }

    @Override
    DomainObjectRepositoryPort<String, Book, BookSearchRequest> repositoryPort() {
        return bookRepositoryPort;
    }
}
