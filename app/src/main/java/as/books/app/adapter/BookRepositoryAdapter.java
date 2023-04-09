package as.books.app.adapter;

import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.persistance.entity.BookEntity;
import as.books.app.persistance.repository.AuthorRepository;
import as.books.app.persistance.repository.BookCategoryRepository;
import as.books.app.persistance.repository.BookRepository;
import as.books.domain.model.Book;
import as.books.domain.model.BookCategory;
import as.books.domain.out.BookRepositoryPort;
import as.books.domain.request.BookSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookRepositoryAdapter extends AbstractRepositoryAdapter<String, BookEntity, Book, BookSearchRequest> implements
        BookRepositoryPort {

    private final BookRepository bookRepository;
    private final AuthorRepositoryAdapter authorRepositoryAdapter;
    private final AuthorRepository authorRepository;
    private final BookCategoryRepositoryAdapter bookCategoryRepositoryAdapter;
    private final BookCategoryRepository bookCategoryRepository;

    @Override
    public List<Book> getBy(final BookSearchRequest searchRequest) {
        final List<BookEntity> bookEntities = bookRepository.findBySearchPhrase(searchRequest.searchPhrase());

        final Set<UUID> allCategoryIds = bookEntities.stream()
                .flatMap(e -> e.getCategoryIds().stream())
                .collect(Collectors.toSet());

        final Map<UUID, BookCategory> categoryByIds = bookCategoryRepository.findAllById(allCategoryIds)
                .stream()
                .collect(Collectors.toMap(BookCategoryEntity::getId, bookCategoryRepositoryAdapter::entityToDomain));

        return bookEntities.stream()
                .map(e -> entityToDomain(e,
                                         e.getCategoryIds()
                                                 .stream()
                                                 .map(categoryByIds::get)
                                                 .sorted()
                                                 .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    Book entityToDomain(final BookEntity entity) {
        final List<BookCategory> categories = bookCategoryRepository.findAllById(entity.getCategoryIds())
                .stream()
                .map(bookCategoryRepositoryAdapter::entityToDomain)
                .sorted()
                .collect(Collectors.toList());

        return entityToDomain(entity, categories);
    }

    private Book entityToDomain(final BookEntity entity, List<BookCategory> categories) {
        return new Book(entity.getIsbn(),
                        entity.getTitle(),
                        entity.getDescription(),
                        authorRepositoryAdapter.entityToDomain(entity.getAuthor()),
                        categories);
    }

    @Override
    BookEntity domainToEntity(final Book domain) {
        final BookEntity entity = new BookEntity();

        entity.setIsbn(domain.isbn());
        entity.setTitle(domain.title());
        entity.setDescription(domain.description());
        entity.setAuthor(authorRepository.getReferenceById(domain.author().id()));
        entity.setCategoryIds(domain.categories().stream().map(BookCategory::id).collect(Collectors.toSet()));

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    BookRepository jpaRepository() {
        return bookRepository;
    }
}
