package as.books.domain.in;

import as.books.domain.exception.DomainObjectNotFoundException;
import as.books.domain.model.DomainObject;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.SearchRequest;

import java.util.List;

abstract class AbstractDomainObjectService<ID, E extends DomainObject, S extends SearchRequest> implements DomainObjectService<ID
        , E, S> {

    abstract DomainObjectRepositoryPort<ID, E, S> repositoryPort();

    @Override
    public List<E> getBy(S searchRequest) {
        return repositoryPort().getBy(searchRequest);
    }

    @Override
    public E get(final ID id) {
        return repositoryPort().getById(id).orElseThrow(() -> new DomainObjectNotFoundException("ID: " + id));
    }
}
