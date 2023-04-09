package as.books.app.controller;

import as.books.domain.in.BookCategoryService;
import as.books.domain.model.BookCategory;
import as.books.domain.request.BookCategorySearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @QueryMapping
    public List<BookCategory> bookCategories(@Argument String searchPhrase) {
        return bookCategoryService.getBy(new BookCategorySearchRequest(searchPhrase));
    }
}
