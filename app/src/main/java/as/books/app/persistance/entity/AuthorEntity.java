package as.books.app.persistance.entity;

import jakarta.persistence.Column;
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
@Table(name = "authors")
@Getter @Setter
public class AuthorEntity implements PersitanceEntity {

    @Id
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorEntity.class.getSimpleName() + "[", "]").add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .toString();
    }
}
