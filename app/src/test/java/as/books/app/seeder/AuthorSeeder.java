package as.books.app.seeder;

import as.books.app.persistance.entity.AuthorEntity;
import as.books.app.persistance.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthorSeeder {

    public final static AuthorEntity GEORGE = author("George", "Marting");
    public final static AuthorEntity KING = author("Stephan", "King");
    public final static AuthorEntity ROWLING = author("J.K", "Rowling");

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity seed(AuthorEntity entity){
        return authorRepository.save(entity);
    }

    public static AuthorEntity author(String firstName, String lastName) {
        AuthorEntity author = new AuthorEntity();
        author.setId(UUID.randomUUID());
        author.setFirstName(firstName);
        author.setLastName(lastName);

        return author;
    }
}
