package as.books.domain.model;

import java.util.List;

public record Book(String isbn, String title, String description, Author author,
                   List<BookCategory> categories) implements DomainObject {}
