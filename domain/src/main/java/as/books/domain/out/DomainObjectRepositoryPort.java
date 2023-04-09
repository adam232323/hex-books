package as.books.domain.out;

import as.books.domain.model.DomainObject;
import as.books.domain.request.SearchRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DomainObjectRepositoryPort<ID, E extends DomainObject, S extends SearchRequest> {

    List<E> getBy(S searchRequest);

    List<E> getAll();

    E save(E object);

    void delete(ID id);

    List<E> getByIds(Set<ID> ids);

    Optional<E> getById(ID id);
}
