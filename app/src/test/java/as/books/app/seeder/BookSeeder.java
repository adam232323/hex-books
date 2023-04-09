package as.books.app.seeder;

import as.books.app.persistance.entity.AuthorEntity;
import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.persistance.entity.BookEntity;
import as.books.app.persistance.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookSeeder {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorSeeder authorSeeder;
    @Autowired
    private BookCategorySeeder bookCategorySeeder;

    public BookEntity seed(BookEntity entity) {
        return repository.save(entity);
    }

    public BookEntity seedHarryPotter() {
        return seed(book("ISBN-HP",
                         "Harry Potter",
                         "About magic",
                         authorSeeder.seed(AuthorSeeder.ROWLING),
                         Set.of(bookCategorySeeder.seed(BookCategorySeeder.FANTASY))));
    }

    public BookEntity seedGameOfThrones() {
        return seed(book("ISBN-GT",
                         "Game of thrones",
                         "About dragons",
                         authorSeeder.seed(AuthorSeeder.GEORGE),
                         Set.of(bookCategorySeeder.seed(BookCategorySeeder.FANTASY))));
    }

    public static BookEntity book(String isbn,
                                  String title,
                                  String description,
                                  AuthorEntity author,
                                  Set<BookCategoryEntity> categories) {
        BookEntity entity = new BookEntity();
        entity.setIsbn(isbn);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setAuthor(author);
        entity.setCategoryIds(categories.stream().map(BookCategoryEntity::getId).collect(Collectors.toSet()));

        return entity;
    }
}
