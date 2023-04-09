package as.books.app.adapter;

import as.books.app.persistance.entity.AuthorEntity;
import as.books.app.persistance.repository.AuthorRepository;
import as.books.domain.model.Author;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.request.AuthorSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorRepositoryAdapter extends AbstractRepositoryAdapter<UUID, AuthorEntity, Author, AuthorSearchRequest> implements
        AuthorRepositoryPort {

    private final AuthorRepository bookRepository;

    @Override
    public List<Author> getBy(final AuthorSearchRequest searchRequest) {
        return bookRepository.findBySearchPhrase(searchRequest.searchPhrase())
                .stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    Author entityToDomain(final AuthorEntity entity) {
        return new Author(entity.getId(), entity.getFirstName(), entity.getLastName());
    }

    @Override
    AuthorEntity domainToEntity(final Author domain) {
        final AuthorEntity entity = new AuthorEntity();

        entity.setId(domain.id());
        entity.setFirstName(domain.firstName());
        entity.setLastName(domain.lastName());

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    AuthorRepository jpaRepository() {
        return bookRepository;
    }
}
