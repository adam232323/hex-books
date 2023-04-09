package as.books.domain.in;

import as.books.domain.model.Author;
import as.books.domain.request.AuthorAddRequest;
import as.books.domain.request.AuthorSearchRequest;

import java.util.UUID;

public interface AuthorService extends DomainObjectService<UUID, Author, AuthorSearchRequest>{

    Author add(AuthorAddRequest request);
}
