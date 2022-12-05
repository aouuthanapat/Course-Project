package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name= "author_info")
public class AuthorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = true)
    @Size(min=2, max = 20, message = "Не меньше 2 знаков")
    private String profession;

    @Column(nullable = true)
    private int exhibitAmount;

    @OneToOne(mappedBy = "authorInfo")
    private Author author;

    public AuthorInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getExhibitAmount() {
        return exhibitAmount;
    }

    public void setExhibitAmount(int exhibitAmount) {
        this.exhibitAmount = exhibitAmount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
