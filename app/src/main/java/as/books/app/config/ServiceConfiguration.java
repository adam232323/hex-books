package as.books.app.config;

import as.books.domain.in.BookService;
import as.books.domain.in.ServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BookService bookService(){
        return ServiceFactory.createBookService(null, null, null);
    }
}
