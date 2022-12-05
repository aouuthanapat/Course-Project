package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, nullable = false)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private String title;
    @Column(length = 100, nullable = false)
    @Size(min=2, max = 100, message = "Не меньше 2 знаков")
    private String anons;
    @Column(nullable = false)
    private String full_text;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    private Set<Exhibit> exhibits;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Theme> themes;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;

    public Event(String title, String anons, String full_text, Set<Exhibit> exhibits) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.exhibits = exhibits;
    }

    public Event(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }

    public Event() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public Set<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(Set<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
