package as.books.domain.in;

import as.books.domain.model.DomainObject;
import as.books.domain.request.SearchRequest;

import java.util.List;
import java.util.Set;

interface DomainObjectService<ID, E extends DomainObject, S extends SearchRequest> {

    List<E> getBy(S searchRequest);

    E get(ID id);

    List<E> getByIds(Set<ID> ids);
}
