package as.books.app.persistance.repository;

import as.books.app.persistance.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query("select t from AuthorEntity t where LOWER(t.firstName) like LOWER(CONCAT('%',:searchPhrase,'%')) OR LOWER"
            + "(t.lastName) like LOWER(CONCAT('%',:searchPhrase,'%')) order by t.lastName, t.firstName")
    List<AuthorEntity> findBySearchPhrase(@Param("searchPhrase") String searchPhrase);
}
