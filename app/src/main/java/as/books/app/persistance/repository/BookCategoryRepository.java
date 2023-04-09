package as.books.app.persistance.repository;

import as.books.app.persistance.entity.BookCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookCategoryRepository extends JpaRepository<BookCategoryEntity, UUID> {

    @Query("select t from BookCategoryEntity t where LOWER(t.name) like LOWER(CONCAT('%',:searchPhrase,'%')) order by t.name")
    List<BookCategoryEntity> findBySearchPhrase(@Param("searchPhrase") String searchPhrase);
}
