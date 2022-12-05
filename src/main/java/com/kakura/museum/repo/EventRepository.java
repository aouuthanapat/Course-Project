package com.kakura.museum.repo;

import com.kakura.museum.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
