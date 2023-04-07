package as.books.domain.model;

import java.util.Set;
import java.util.UUID;

public record Book(String isbn, String title, String description, UUID authorId, Set<UUID> categoryIds) implements
        DomainObject {}
