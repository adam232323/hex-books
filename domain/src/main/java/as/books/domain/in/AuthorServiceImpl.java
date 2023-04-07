package as.books.domain.in;

import as.books.domain.model.Author;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.AuthorSearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class AuthorServiceImpl extends AbstractDomainObjectService<UUID, Author, AuthorSearchRequest> implements
        AuthorService {

    private final AuthorRepositoryPort categoryRepositoryPort;

    @Override
    DomainObjectRepositoryPort<UUID, Author, AuthorSearchRequest> repositoryPort() {
        return categoryRepositoryPort;
    }
}
