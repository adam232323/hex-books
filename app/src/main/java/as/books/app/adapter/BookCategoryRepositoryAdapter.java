package as.books.app.adapter;

import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.persistance.repository.BookCategoryRepository;
import as.books.domain.model.BookCategory;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.request.BookCategorySearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookCategoryRepositoryAdapter extends AbstractRepositoryAdapter<UUID, BookCategoryEntity, BookCategory,
        BookCategorySearchRequest> implements BookCategoryRepositoryPort {

    private final BookCategoryRepository bookRepository;

    @Override
    public List<BookCategory> getBy(final BookCategorySearchRequest searchRequest) {
        return bookRepository.findBySearchPhrase(searchRequest.searchPhrase())
                .stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    BookCategory entityToDomain(final BookCategoryEntity entity) {
        return new BookCategory(entity.getId(), entity.getName());
    }

    @Override
    BookCategoryEntity domainToEntity(final BookCategory domain) {
        final BookCategoryEntity entity = new BookCategoryEntity();

        entity.setId(domain.id());
        entity.setName(domain.name());

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    BookCategoryRepository jpaRepository() {
        return bookRepository;
    }
}
