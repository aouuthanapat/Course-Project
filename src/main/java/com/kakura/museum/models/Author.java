package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    @Size(min=2, max = 20, message = "Не меньше 2 знаков")
    private String firstName;

    @Column(length = 20, nullable = false)
    @Size(min=2, max = 20, message = "Не меньше 2 знаков")
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Exhibit> exhibits;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_info_id", referencedColumnName = "id")
    private AuthorInfo authorInfo;

    public Author() {

    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(Set<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }
}
