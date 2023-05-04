package com.example.helloworld.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Plain object java - POJO
@Entity
public class Product {
    // This is primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO INCREASE
    private Long id;
    private String name;
    private int year;
    private double cost;
    private String imageUrl;

    public Product() {}

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
                '}';
    }
}

