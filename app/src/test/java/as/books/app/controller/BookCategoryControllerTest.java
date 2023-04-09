package as.books.app.controller;

import as.books.app.AbstractIntegrationTest;
import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.seeder.BookCategorySeeder;
import as.books.domain.model.Author;
import as.books.domain.model.BookCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureGraphQlTester
class BookCategoryControllerTest extends AbstractIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private BookCategorySeeder bookCategorySeeder;

    @Test
    void shouldGetBookCategoryBySearchPhrase() {
        final BookCategoryEntity fantasy = bookCategorySeeder.seed(BookCategorySeeder.FANTASY);
        bookCategorySeeder.seed(BookCategorySeeder.HORROR);

        String query = """
                query{
                  bookCategories(searchPhrase:"tasy"){
                      id,
                      name
                  }
                }
                """;

        final BookCategory category = this.graphQlTester.document(query)
                .execute()
                .path("data.bookCategories[0]")
                .entity(BookCategory.class)
                .get();

        assertEquals(fantasy.getId(), category.id());
        assertEquals(fantasy.getName(), category.name());
    }

    @Test
    void shouldAddAuthor() {
        String mutation = """
                mutation AddAuthor{
                    addAuthor(
                        firstName: "Jan"
                        lastName: "Testowy"
                    ) {
                        id
                        firstName
                        lastName
                    }
                }
                """;

        final Author author = this.graphQlTester.document(mutation)
                .execute()
                .path("data.addAuthor")
                .entity(Author.class)
                .get();

        assertNotNull(author.id());
        assertEquals("Jan", author.firstName());
        assertEquals("Testowy", author.lastName());
    }
}