package as.books.domain.model;

import java.util.UUID;

public record Author(UUID id, String firstName, String lastName) implements DomainObject{

    public static Author of(String firstName, String lastName){
        return new Author(UUID.randomUUID(), firstName, lastName);
    }
}
