package as.books.app.controller;

import as.books.app.AbstractIntegrationTest;
import as.books.app.persistance.entity.AuthorEntity;
import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.persistance.entity.BookEntity;
import as.books.app.persistance.repository.BookRepository;
import as.books.app.seeder.AuthorSeeder;
import as.books.app.seeder.BookCategorySeeder;
import as.books.app.seeder.BookSeeder;
import as.books.domain.model.Author;
import as.books.domain.model.Book;
import as.books.domain.model.BookCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import static as.books.app.seeder.AuthorSeeder.ROWLING;
import static as.books.app.seeder.BookCategorySeeder.FANTASY;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureGraphQlTester
class BookControllerTest extends AbstractIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private BookSeeder bookSeeder;
    @Autowired
    private AuthorSeeder authorSeeder;
    @Autowired
    private BookCategorySeeder bookCategorySeeder;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldGetBookBySearchPhrase() {
        final BookEntity harryPotter = bookSeeder.seedHarryPotter();
        bookSeeder.seedGameOfThrones();

        String query = """
                query{
                  books(searchPhrase:"potter"){
                      isbn
                      title
                      description
                      author {
                         id
                         firstName
                         lastName                
                      }
                      categories{
                          id
                          name
                      }
                  }
                }
                """;

        final Book book = this.graphQlTester.document(query)
                .execute()
                .path("data.books[0]")
                .entity(Book.class)
                .get();

        assertEquals(harryPotter.getIsbn(), book.isbn());
        assertEquals(harryPotter.getTitle(), book.title());
        assertEquals(harryPotter.getDescription(), book.description());

        final Author author = book.author();
        final BookCategory category = book.categories().get(0);

        assertEquals(ROWLING.getId(), author.id());
        assertEquals(ROWLING.getFirstName(), author.firstName());
        assertEquals(ROWLING.getLastName(), author.lastName());

        assertEquals(FANTASY.getId(), category.id());
        assertEquals(FANTASY.getName(), category.name());

    }

    @Test
    void shouldAddABook() {
        final AuthorEntity king = authorSeeder.seed(AuthorSeeder.GEORGE);
        final BookCategoryEntity horror = bookCategorySeeder.seed(BookCategorySeeder.HORROR);

        String mutation = """
                mutation AddBook{
                    addBook(
                        isbn: "isbn-111"
                        title: "Shining",
                        description: "Some horror"
                        authorId: "%s"
                        categoryIds: ["%s"]
                    ) {
                        isbn                
                    }
                }
                """.formatted(king.getId(), horror.getId());

        final Book book = this.graphQlTester.document(mutation)
                .execute()
                .path("data.addBook")
                .entity(Book.class)
                .get();

        assertNotNull(book.isbn());
    }

    @Test
    void shouldDeleteBook() {
        final BookEntity bookEntity = bookSeeder.seedGameOfThrones();

        String mutation = """
                mutation DeleteBook{
                    deleteBook(isbn: "%s")
                }
                """.formatted(bookEntity.getIsbn());

        this.graphQlTester.document(mutation)
                .executeAndVerify();

        assertFalse(bookRepository.findById(bookEntity.getIsbn()).isPresent());
    }
}