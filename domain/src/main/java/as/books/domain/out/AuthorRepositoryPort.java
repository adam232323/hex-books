package as.books.domain.out;

import as.books.domain.model.Author;
import as.books.domain.request.AuthorSearchRequest;

import java.util.UUID;

public interface AuthorRepositoryPort extends DomainObjectRepositoryPort<UUID, Author, AuthorSearchRequest> {
}
