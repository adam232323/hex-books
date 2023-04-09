package as.books.app.config;

import as.books.app.adapter.AuthorRepositoryAdapter;
import as.books.app.adapter.BookCategoryRepositoryAdapter;
import as.books.app.adapter.BookRepositoryAdapter;
import as.books.domain.in.AuthorService;
import as.books.domain.in.BookCategoryService;
import as.books.domain.in.BookService;
import as.books.domain.in.ServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {

    @Bean
    BookService bookService(BookRepositoryAdapter bookRepositoryAdapter,
                            AuthorRepositoryAdapter authorRepositoryAdapter,
                            BookCategoryRepositoryAdapter bookCategoryRepositoryAdapter) {
        return ServiceFactory.createBookService(bookRepositoryAdapter,
                                                authorRepositoryAdapter,
                                                bookCategoryRepositoryAdapter);
    }

    @Bean
    AuthorService authorService(AuthorRepositoryAdapter authorRepositoryAdapter) {
        return ServiceFactory.createAuthorService(authorRepositoryAdapter);
    }

    @Bean
    BookCategoryService bookCategoryService(BookCategoryRepositoryAdapter bookCategoryRepositoryAdapter) {
        return ServiceFactory.createBookCategoryService(bookCategoryRepositoryAdapter);
    }


}
