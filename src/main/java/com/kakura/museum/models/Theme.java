package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, nullable = false)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private String name;

    @ManyToMany(mappedBy = "themes")
    Set<Event> events;

    public Theme() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
