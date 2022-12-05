package com.kakura.museum.repo;

import com.kakura.museum.models.Exhibit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ExhibitRepository extends CrudRepository<Exhibit, Long> {
    @Query(value = "CALL FIND_EXHIBITS_AFTER_DATE(:dateIn);", nativeQuery = true)
    List<Exhibit> findExhibitAfterDate(@Param("dateIn") Date dateIn);

}
