package com.example.helloworld.models;

import jakarta.persistence.*;

import java.util.Calendar;

// Plain object java - POJO
@Entity
@Table(name = "tblProduct")
public class Product {
    // This is primary key
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence") // AUTO INCREASE
    private Long id;
    // validate constraint
    @Column(nullable = false, unique = true, length = 300)
    private String name;
    private int year;
    private double cost;
    private String imageUrl;

    // calculated field = transient
    @Transient
    private int age;

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    public Product() {
    }

    public Product(String name, int year, double cost, String imageUrl) {
        this.name = name;
        this.year = year;
        this.cost = cost;
        this.imageUrl = imageUrl;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", cost=" + cost +
                ", imageUrl='" + imageUrl + '\'' +
                ", age=" + age +
                '}';
    }
}

