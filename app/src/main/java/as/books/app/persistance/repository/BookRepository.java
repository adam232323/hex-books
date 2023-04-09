package as.books.app.persistance.repository;

import as.books.app.persistance.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Query("select t from BookEntity t where LOWER(t.title) like LOWER(CONCAT('%',:searchPhrase,'%')) order by t.title")
    List<BookEntity> findBySearchPhrase(@Param("searchPhrase") String searchPhrase);
}
