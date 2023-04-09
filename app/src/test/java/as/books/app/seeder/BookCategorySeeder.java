package as.books.app.seeder;

import as.books.app.persistance.entity.BookCategoryEntity;
import as.books.app.persistance.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookCategorySeeder {

    public final static BookCategoryEntity FANTASY = category("Fantasy");
    public final static BookCategoryEntity HORROR = category("Horror");

    @Autowired
    private BookCategoryRepository repository;

    public BookCategoryEntity seed(BookCategoryEntity entity){
        return repository.save(entity);
    }

    public static BookCategoryEntity category(String name) {
        BookCategoryEntity entity = new BookCategoryEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(name);

        return entity;
    }
}
