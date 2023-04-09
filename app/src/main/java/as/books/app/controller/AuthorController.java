package as.books.app.controller;

import as.books.domain.in.AuthorService;
import as.books.domain.model.Author;
import as.books.domain.request.AuthorAddRequest;
import as.books.domain.request.AuthorSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @QueryMapping
    public List<Author> authors(@Argument String searchPhrase) {
        return authorService.getBy(new AuthorSearchRequest(searchPhrase));
    }


    @MutationMapping
    public Author addAuthor(@Argument String firstName, @Argument String lastName) {
        return authorService.add(new AuthorAddRequest(firstName, lastName));
    }
}
