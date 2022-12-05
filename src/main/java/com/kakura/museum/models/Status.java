package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private int category;

    @Column(nullable = true)
    private int experience;

    @OneToOne(mappedBy = "status")
    private User user;

    public Status() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
