package as.books.domain;

import as.books.domain.model.Author;
import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.request.AuthorSearchRequest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class InMemoryAuthorRepository extends AbstractInMemoryRepository<UUID, Author, AuthorSearchRequest> implements
        AuthorRepositoryPort {

    public static final Author WILLIAM = Author.of("William", "Shakespeare");
    public static final Author ROWLING = Author.of("J.K", "Rowling");
    public static final Author MARTIN = Author.of("George", "Marting");

    public static InMemoryAuthorRepository STUB = new InMemoryAuthorRepository(List.of(WILLIAM, ROWLING, MARTIN));

    private static final Comparator<Author> COMPARATOR = Comparator.comparing(Author::lastName)
            .thenComparing(Author::firstName);

    public InMemoryAuthorRepository(final List<Author> objects) {
        super(objects);
    }

    @Override
    protected Function<Author, List<String>> searchPhraseFieldsProvider() {
        return b -> List.of(b.lastName(), b.firstName());
    }

    @Override
    protected Function<Author, UUID> idFieldProvider() {
        return Author::id;
    }

    @Override
    protected Comparator<Author> sortComparator() {
        return COMPARATOR;
    }
}
