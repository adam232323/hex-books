package as.books.domain.in;

import as.books.domain.model.Author;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.AuthorAddRequest;
import as.books.domain.request.AuthorSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
class AuthorServiceImpl extends AbstractDomainObjectService<UUID, Author, AuthorSearchRequest> implements
        AuthorService {

    private final AuthorRepositoryPort authorRepositoryPort;

    @Override
    public Author add(final AuthorAddRequest request) {
        final Author saved = authorRepositoryPort.save(Author.of(request.firstName(), request.lastName()));

        log.info("Author created: {}", saved);

        return saved;
    }

    @Override
    DomainObjectRepositoryPort<UUID, Author, AuthorSearchRequest> repositoryPort() {
        return authorRepositoryPort;
    }
}
