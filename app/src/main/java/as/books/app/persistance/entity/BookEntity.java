package as.books.app.persistance.entity;

import as.books.app.persistance.converter.UUIDSetConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter @Setter
public class BookEntity implements PersitanceEntity{

    @Id
    private String isbn;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Size(min = 1, max = 1000)
    private String description;

    @NotNull
    @ManyToOne
    private AuthorEntity author;


    /*
    This approach allows to store ids in NoSQL style. No extra table is needed.
     */
    @NotNull
    @Column(name = "category_ids", columnDefinition = "JSON")
    @Convert(converter = UUIDSetConverter.class)
    @ColumnTransformer(write = "?::json")
    private Set<UUID> categoryIds;

    @Override
    public String toString() {
        return new StringJoiner(", ", BookEntity.class.getSimpleName() + "[", "]").add("isbn=" + isbn)
                .add("title='" + title + "'")
                .toString();
    }
}
