package as.books.domain.in;

import as.books.domain.out.AuthorRepositoryPort;
import as.books.domain.out.BookCategoryRepositoryPort;
import as.books.domain.out.BookRepositoryPort;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceFactory {

    public static BookService createBookService(BookRepositoryPort bookRepositoryPort,
                                                AuthorRepositoryPort authorRepositoryPort,
                                                BookCategoryRepositoryPort bookCategoryRepositoryPort) {
        return new BookServiceImpl(bookRepositoryPort, authorRepositoryPort, bookCategoryRepositoryPort);
    }

    public static AuthorService createAuthorService(AuthorRepositoryPort authorRepositoryPort) {
        return new AuthorServiceImpl(authorRepositoryPort);
    }

    public static BookCategoryService createBookCategoryService(BookCategoryRepositoryPort categoryRepositoryPort) {
        return new BookCategoryServiceImpl(categoryRepositoryPort);
    }
}
