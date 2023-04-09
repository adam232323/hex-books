package as.books.app.controller;

import as.books.app.AbstractIntegrationTest;
import as.books.app.persistance.entity.AuthorEntity;
import as.books.app.seeder.AuthorSeeder;
import as.books.domain.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureGraphQlTester
class AuthorControllerTest extends AbstractIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private AuthorSeeder authorSeeder;

    @Test
    void shouldGetAuthorBySearchPhrase() {
        final AuthorEntity george = authorSeeder.seed(AuthorSeeder.GEORGE);

        String query = """
                query{
                  authors(searchPhrase:"George"){
                      id,
                      firstName,
                      lastName
                  }
                }
                """;

        final Author author = this.graphQlTester.document(query)
                .execute()
                .path("data.authors[0]")
                .entity(Author.class)
                .get();

        assertEquals(george.getId(), author.id());
        assertEquals(george.getFirstName(), author.firstName());
        assertEquals(george.getLastName(), author.lastName());
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