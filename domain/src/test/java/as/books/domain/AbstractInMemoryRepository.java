package as.books.domain;

import as.books.domain.model.DomainObject;
import as.books.domain.out.DomainObjectRepositoryPort;
import as.books.domain.request.SearchRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class AbstractInMemoryRepository<ID, E extends DomainObject, S extends SearchRequest> implements
        DomainObjectRepositoryPort<ID, E, S> {

    protected final Map<ID, E> objectsMap = new ConcurrentHashMap<>();

    AbstractInMemoryRepository(List<E> objects) {
        objects.forEach(o -> objectsMap.put(idFieldProvider().apply(o), o));
    }

    @Override
    public List<E> getAll() {
        return objectsMap.values().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<E> getBy(final S searchRequest) {
        if (searchRequest == null) {
            return objectsMap.values().stream().toList();
        }

        return objectsMap.values()
                .stream()
                .filter(c -> searchRequest.searchPhrase() == null || searchPhraseFieldsProvider().apply(c)
                        .stream()
                        .anyMatch(s -> s != null && s.toLowerCase().contains(searchRequest.searchPhrase())))
                .sorted(sortComparator())
                .toList();
    }

    @Override
    public List<E> getByIds(final Set<ID> ids) {
        Objects.requireNonNull(ids);
        return ids.stream().map(objectsMap::get).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Optional<E> getById(final ID id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(objectsMap.get(id));
    }

    @Override
    public void save(final E object) {
        Objects.requireNonNull(object);
        objectsMap.put(idFieldProvider().apply(object), object);
    }

    @Override
    public void delete(final ID id) {
        Objects.requireNonNull(id);
        objectsMap.remove(id);
    }

    protected abstract Function<E, List<String>> searchPhraseFieldsProvider();

    protected abstract Function<E, ID> idFieldProvider();

    protected abstract Comparator<E> sortComparator();
}
