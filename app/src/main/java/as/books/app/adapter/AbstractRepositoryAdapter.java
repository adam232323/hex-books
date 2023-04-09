package as.books.app.adapter;

import as.books.app.persistance.entity.PersitanceEntity;
import as.books.domain.model.DomainObject;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.SearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


abstract class AbstractRepositoryAdapter<ID, E extends PersitanceEntity, D extends DomainObject,
        S extends SearchRequest> implements
        DomainObjectRepositoryPort<ID, D, S> {

    @Override
    public List<D> getAll() {
        return jpaRepository().findAll().stream().map(this::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public D save(final D domain) {
        var saved = jpaRepository().save(domainToEntity(domain));

        return entityToDomain(saved);
    }

    @Override
    public void delete(final ID id) {
        jpaRepository().deleteById(id);
    }

    @Override
    public List<D> getByIds(final Set<ID> ids) {
        return jpaRepository().findAllById(ids).stream().map(this::entityToDomain).toList();
    }


    @Override
    public Optional<D> getById(final ID id) {
        return jpaRepository().findById(id).map(this::entityToDomain);
    }

    abstract D entityToDomain(E entity);

    abstract E domainToEntity(D domain);

    abstract <J extends JpaRepository<E, ID>> J jpaRepository();
}
