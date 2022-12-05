package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity(name = "exhibits")
public class Exhibit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, nullable = false)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private String name;

    private Date receiptDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Author> authors;

    public Exhibit(String name, Date receiptDate, Event event) {
        this.name = name;
        this.receiptDate = receiptDate;
        this.event = event;
    }

    public Exhibit(String name, Date receiptDate) {
        this.name = name;
        this.receiptDate = receiptDate;
    }

    public Exhibit(String name, Event event) {
        this.name = name;
        this.event = event;
    }

    public Exhibit(String name) {
        this.name = name;
    }

    public Exhibit() {

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

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
