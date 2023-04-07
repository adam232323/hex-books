package as.books.domain.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public record BookAddRequest(@NotNull @Size(min = 1, max = 100) String isbn,
                             @NotNull @Size(min = 1, max = 100) String title,
                             @NotNull @Size(min = 1, max = 1000) String description,
                             @NotNull UUID authorId,
                             @NotNull Set<UUID> categoryIds) {}
