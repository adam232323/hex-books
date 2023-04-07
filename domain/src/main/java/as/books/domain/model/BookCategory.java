package as.books.domain.model;

import java.util.UUID;

public record BookCategory(UUID uuid, String name) implements DomainObject {

    public static BookCategory of(String name) {
        return new BookCategory(UUID.randomUUID(), name);
    }
}
