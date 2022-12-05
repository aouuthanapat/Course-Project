package com.kakura.museum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, nullable = true)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private String city;

    @Column(length = 40, nullable = true)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private String street;

    @Column(length = 40, nullable = true)
    @Size(min=2, max = 40, message = "Не меньше 2 знаков")
    private int houseNumber;

    @OneToOne(mappedBy = "address")
    private User user;

    public Address() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
