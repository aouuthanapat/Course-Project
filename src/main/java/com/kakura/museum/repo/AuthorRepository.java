package com.kakura.museum.repo;

import com.kakura.museum.models.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query(value = "CALL FIND_AUTHORS_BY_FIRST_NAME(:firstName)", nativeQuery = true)
    List<Author> findAuthorsByFirstName(@Param("firstName") String firstName);

    @Query(value = "CALL FIND_AUTHORS_BY_LAST_NAME(:lastName)", nativeQuery = true)
    List<Author> findAuthorsByLastName(@Param("lastName") String lastName);


}
