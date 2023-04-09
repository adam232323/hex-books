package as.books.domain;

import as.books.domain.model.BookCategory;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.request.BookCategorySearchRequest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static as.books.domain.model.BookCategory.COMPARATOR;

public class InMemoryBookCategoryRepository extends AbstractInMemoryRepository<UUID, BookCategory,
        BookCategorySearchRequest> implements
        BookCategoryRepositoryPort {

    public static final BookCategory THRILLER = BookCategory.of("Thriller");
    public static final BookCategory HORROR = BookCategory.of("Horror");
    public static final BookCategory FANTASY = BookCategory.of("Fantasy");

    public static final InMemoryBookCategoryRepository STUB = new InMemoryBookCategoryRepository(List.of(THRILLER,
                                                                                                         HORROR,
                                                                                                         FANTASY));

    public InMemoryBookCategoryRepository(final List<BookCategory> objects) {
        super(objects);
    }

    @Override
    protected Function<BookCategory, List<String>> searchPhraseFieldsProvider() {
        return c -> List.of(c.name());
    }

    @Override
    protected Function<BookCategory, UUID> idFieldProvider() {
        return BookCategory::id;
    }

    @Override
    protected Comparator<BookCategory> sortComparator() {
        return COMPARATOR;
    }
}
