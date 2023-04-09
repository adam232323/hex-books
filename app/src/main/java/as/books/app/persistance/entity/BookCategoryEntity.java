package as.books.app.persistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
@Table(name = "book_categories")
@Getter @Setter
public class BookCategoryEntity implements PersitanceEntity{

    @Id
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Override
    public String toString() {
        return new StringJoiner(", ", BookCategoryEntity.class.getSimpleName() + "[", "]").add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
