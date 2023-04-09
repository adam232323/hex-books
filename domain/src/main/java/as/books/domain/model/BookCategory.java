package as.books.domain.model;

import java.util.Comparator;
import java.util.UUID;

public record BookCategory(UUID id, String name) implements DomainObject, Comparable<BookCategory> {

    public static final Comparator<BookCategory> COMPARATOR = Comparator.comparing(BookCategory::name);

    public static BookCategory of(String name) {
        return new BookCategory(UUID.randomUUID(), name);
    }

    @Override
    public int compareTo(final BookCategory o) {
        return COMPARATOR.compare(this, o);
    }
}
