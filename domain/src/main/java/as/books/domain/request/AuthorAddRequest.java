package as.books.domain.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AuthorAddRequest(@NotNull @Size(min = 1, max = 100) String firstName,
                               @NotNull @Size(min = 1, max = 100) String lastName) {}
