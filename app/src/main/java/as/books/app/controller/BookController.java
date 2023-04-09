package as.books.app.controller;

import as.books.domain.in.BookService;
import as.books.domain.model.Book;
import as.books.domain.request.BookAddRequest;
import as.books.domain.request.BookSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @QueryMapping
    public List<Book> books(@Argument String searchPhrase) {
        return bookService.getBy(new BookSearchRequest(searchPhrase));
    }

    @MutationMapping
    public Book addBook(@Argument String isbn,
                        @Argument String title,
                        @Argument String description,
                        @Argument UUID authorId,
                        @Argument Set<UUID> categoryIds) {
        return bookService.add(new BookAddRequest(isbn, title, description, authorId, categoryIds));
    }

    @MutationMapping
    public String deleteBook(@Argument String isbn) {
        bookService.delete(isbn);
        return isbn;
    }
}
